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
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCount
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.ActivityMyDealerBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.ui.income.IncomeListActivity
import com.technoecorp.gorilladealer.utils.NetworkChecker
import kotlinx.coroutines.flow.collectLatest
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
        viewModel.getLastDashboardData(::setLatestDashboard)
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
        binding.toolbar.linearBack.setOnClickListener {
            finish()
        }
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

    private fun onStateCountResponse(stateWiseCountResponse: StateWiseCountResponse) {
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
        stateWiseCountResponse.status.let { check ->
            if (!check) {
                (this@MyDealerActivity).showShortToast(stateWiseCountResponse.message)
                return@let
            }
            list.addAll(stateWiseCountResponse.data)

        }
        val coloredList = list.map { state ->
            val random = randomObj.nextInt(androidColors.size)
            state.color = androidColors[random]
            state
        }
        stateAdapter.submitList(coloredList)
    }

    private fun onCityWiseCountResponse(cityWiseCountResponse: CityWiseCountResponse) {
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
        cityWiseCountResponse.status.let { check ->
            if (!check) {
                (this@MyDealerActivity).showShortToast(cityWiseCountResponse.message)
                return@let
            }
            list.addAll(cityWiseCountResponse.data)
        }
        cityWiseCountAdapter.submitList(list)

    }

    private fun <T> checkResponse(result: ResultWrapper<T>, callback: (T) -> Unit) {
        when (result) {
            is ResultWrapper.Loading -> {
                showDialog()
            }
            is ResultWrapper.Error -> {
                dismissDialog()
                this.showShortToast(result.message)
            }
            is ResultWrapper.Success -> {
                dismissDialog()
                result.data?.let {
                    callback(it)
                }
            }
            is ResultWrapper.Started -> {
                //Nothing to done
            }
        }
    }

    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            viewModel.stateList.collectLatest {
                checkResponse(it, ::onStateCountResponse)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.cityList.collectLatest {
                checkResponse(it, ::onCityWiseCountResponse)
            }
        }
    }

    private fun checkId(_id: Int): Int? {
        return if (_id > 0) _id else null
    }

    private fun selectCity(cityWiseCount: CityWiseCount, stateId: Int, isActive: Boolean) {
        val state: Int? = checkId(stateId)
        val city: Int? = checkId(cityWiseCount.cityId)
        this.cityWiseCount = cityWiseCount
        lifecycleScope.launchWhenCreated {
            if (!NetworkChecker.isInternetAvailable(this@MyDealerActivity)) {
                this@MyDealerActivity.showShortToast(getString(R.string.require_internet))
                return@launchWhenCreated
            }
            showDialog()
            viewModel.getDealerList(
                DealerFilterRequest(
                    dealer.dealerId,
                    dealer.referCode!!,
                    isActive,
                    state,
                    city
                ), ::renderDealerList
            )

        }

    }

    private fun renderDealerList(
        data: ResultWrapper<DealerFilterResponse>,
        state: Int?,
        city: Int?,
        isActive: Boolean?
    ) {
        when (data) {
            is ResultWrapper.Success -> {
                dismissDialog()
                data.data?.status?.let { check ->
                    if (!check) {
                        (this@MyDealerActivity).showShortToast(data.data?.message)
                        return@let
                    }
                    val intent =
                        Intent(this@MyDealerActivity, IncomeListActivity::class.java)
                    intent.putExtra("stateId", state)
                    intent.putExtra("cityId", city)
                    intent.putExtra("isActive", isActive)
                    val title: String = when (isActive) {
                        null -> "All User"
                        true -> "Active User"
                        false -> "Deactive User"
                    }
                    intent.putExtra(
                        "title",
                        "$title, ${cityWiseCount?.cityName}"
                    )
                    intent.putExtra("dealers", Gson().toJson(data.data?.data))
                    startActivity(intent)

                }
            }
            is ResultWrapper.Error -> {
                dismissDialog()
                (this@MyDealerActivity).showShortToast(data.message)
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

    private fun setLatestDashboard(data: DashboardAnayliticsResponse?) {
        data?.let {
            this.latestDashboard = data
        }
    }
}