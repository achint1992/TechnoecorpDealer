package com.technoecorp.gorilladealer.ui.mydealer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.request.CityWiseDealerCountRequest
import com.technoecorp.domain.domainmodel.request.DealerFilterRequest
import com.technoecorp.domain.domainmodel.request.StateWiseDealerCountRequest
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCount
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCount
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.ActivityMyDealerBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.ui.income.IncomeListActivity
import com.technoecorp.gorilladealer.utils.NetworkChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MyDealerActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: MyDealerViewModelFactory
    lateinit var viewModel: MyDealerViewModel

    private var _binding: ActivityMyDealerBinding? = null
    private val binding: ActivityMyDealerBinding get() = _binding!!
    private lateinit var stateAdapter: StateWiseCountAdapter
    private lateinit var cityWiseCountAdapter: CityWiseCountAdapter
    private lateinit var latestDashboard: DashboardAnayliticsResponse
    private lateinit var customDialogClass: CustomDialogClass
    private lateinit var androidColors: IntArray
    private var randomObj = Random()
    private var _dealer: Dealer? = null
    private val dealer: Dealer get() = _dealer!!
    private var stateWiseCount: StateWiseCount? = null
    private var cityWiseCount: CityWiseCount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyDealerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as TechnoecorpApplication).getMyDealerSubComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MyDealerViewModel::class.java]
        this.androidColors = resources.getIntArray(R.array.androidcolors)
        _dealer = (application as TechnoecorpApplication).getUpdateDealer()
        if (_dealer == null) {
            finish()
        }
        setLatestDashboard()
        initView()
        initCollector()
        if (NetworkChecker.isInternetAvailable(this)) {
            viewModel.getStateWiseCount(StateWiseDealerCountRequest(dealer.referCode!!))
        } else {
            this.showShortToast(getString(R.string.require_internet))
        }
    }

    private fun initView() {
        customDialogClass = CustomDialogClass(this@MyDealerActivity)

        stateAdapter = StateWiseCountAdapter(::selectState)
        binding.stateList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.stateList.adapter = stateAdapter

        cityWiseCountAdapter = CityWiseCountAdapter(this, ::selectCity)
        binding.cityList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.cityList.adapter = cityWiseCountAdapter

    }

    private fun showDialog() {
        if (!customDialogClass.isShowing) {
            customDialogClass.show()
        }
    }

    private fun dismissDialog() {
        if (customDialogClass.isShowing) {
            customDialogClass.dismiss()
        }
    }


    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            viewModel.stateList.collectLatest {
                when (it) {
                    is ResultWrapper.Loading -> {
                        showDialog()
                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        (this@MyDealerActivity).showShortToast(it.message)
                    }
                    is ResultWrapper.Success -> {
                        dismissDialog()
                        val list: ArrayList<StateWiseCount> = ArrayList()
                        val activeUsers: Int = latestDashboard.data.activeDealer
                        val deactiveUsers: Int = latestDashboard.data.deactiveDealer
                        val stateWiseCount = StateWiseCount(
                            activeUsers,
                            (activeUsers + deactiveUsers),
                            deactiveUsers,
                            -1,
                            "All States"
                        )
                        selectState(stateWiseCount)
                        list.add(stateWiseCount)
                        it.data?.status?.let { check ->
                            if (check) {
                                list.addAll(it.data?.data!!)
                            } else {
                                (this@MyDealerActivity).showShortToast(it.data?.message)
                            }
                        }
                        val coloredList = list.map { state ->
                            val random = randomObj.nextInt(androidColors.size)
                            state.color = androidColors[random]
                            state
                        }
                        stateAdapter.submitList(coloredList)
                    }
                }
            }

        }

        lifecycleScope.launchWhenCreated {
            viewModel.cityList.collectLatest {
                when (it) {
                    is ResultWrapper.Loading -> {
                        showDialog()
                    }
                    is ResultWrapper.Success -> {
                        dismissDialog()
                        val list: ArrayList<CityWiseCount> = ArrayList()
                        stateWiseCount?.let { data ->
                            list.add(
                                CityWiseCount(
                                    data.activeCount,
                                    -1,
                                    "All Cities",
                                    data.count,
                                    data.deactiveCount,
                                    data.stateName
                                )
                            )
                        }
                        it.data?.status?.let { check ->
                            if (check) {
                                list.addAll(it.data?.data!!)
                            } else {
                                (this@MyDealerActivity).showShortToast(it.data?.message)
                            }
                        }
                        cityWiseCountAdapter.submitList(list)

                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        (this@MyDealerActivity).showShortToast(it.message)
                    }

                }
            }
        }
    }

    private fun selectCity(cityWiseCount: CityWiseCount, stateId: Int, isActive: Boolean) {
        val state: Int? = if (stateId > 0) stateId else null
        val city: Int? = if (cityWiseCount.cityId > 0) cityWiseCount.cityId else null
        this.cityWiseCount = cityWiseCount
        lifecycleScope.launchWhenCreated {
            showDialog()
            if (NetworkChecker.isInternetAvailable(this@MyDealerActivity)) {
                val data = viewModel.getDealerList(
                    DealerFilterRequest(
                        dealer.dealerId,
                        dealer.referCode!!,
                        isActive,
                        state,
                        city
                    )
                )
                when (data) {
                    is ResultWrapper.Success -> {
                        dismissDialog()
                        data.data?.status?.let { check ->
                            if (check) {
                                val intent =
                                    Intent(this@MyDealerActivity, IncomeListActivity::class.java)
                                intent.putExtra("stateId", state)
                                intent.putExtra("cityId", city)
                                intent.putExtra("isActive", isActive)
                                if (isActive) {
                                    intent.putExtra(
                                        "title",
                                        "Active User, ${cityWiseCount.cityName}"
                                    )
                                } else {
                                    intent.putExtra(
                                        "title",
                                        "Deactive User, ${cityWiseCount.cityName}"
                                    )
                                }
                                intent.putExtra("dealers", Gson().toJson(data.data?.data))
                                startActivity(intent)
                            } else {
                                (this@MyDealerActivity).showShortToast(data.data?.message)
                            }
                        }
                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        (this@MyDealerActivity).showShortToast(data.message)
                    }
                }
            } else {
                this@MyDealerActivity.showShortToast(getString(R.string.require_internet))
            }
        }

    }

    private fun selectState(stateWiseCount: StateWiseCount) {
        cityWiseCountAdapter.submitStateId(stateWiseCount.stateId)
        this.stateWiseCount = stateWiseCount

        if (stateWiseCount.stateId != -1) {
            if (NetworkChecker.isInternetAvailable(this)) {

                viewModel.getCityWiseCount(
                    CityWiseDealerCountRequest(
                        dealer.referCode!!,
                        stateWiseCount.stateId
                    )
                )
            } else {
                this.showShortToast(getString(R.string.require_internet))
            }
        } else {
            val tempList: ArrayList<CityWiseCount> = ArrayList()
            tempList.add(
                CityWiseCount(
                    stateWiseCount.activeCount,
                    -1,
                    "All Cities",
                    stateWiseCount.count,
                    stateWiseCount.deactiveCount,
                    stateWiseCount.stateName
                )
            )
            cityWiseCountAdapter.submitList(tempList)
        }


    }

    private fun setLatestDashboard() {
        CoroutineScope(Dispatchers.IO).launch {
            latestDashboard = viewModel.getLastDashboardData()!!
        }
    }


}