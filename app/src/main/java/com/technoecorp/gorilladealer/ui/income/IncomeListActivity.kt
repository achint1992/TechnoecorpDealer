package com.technoecorp.gorilladealer.ui.income

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.gorilladealer.databinding.ActivityIncomeListBinding
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import timber.log.Timber

class IncomeListActivity : AppCompatActivity() {
    private var _binding: ActivityIncomeListBinding? = null
    private val binding: ActivityIncomeListBinding get() = _binding!!
    var isExport = true
    var isActive = true
    var stateId: String? = null
    var cityId: String? = null
    var dealer: Dealer? = null
    var dealersList = ArrayList<Dealer>()
    var title: String? = null
    lateinit var adapter: IncomeListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIncomeListBinding.inflate(layoutInflater)
        initView()
        dealer = (application as TechnoecorpApplication).getUpdateDealer()
        if (dealer == null) {
            finish()
        }

        intent?.let {
            val gson = Gson()
            title = intent.getStringExtra("title")
            Timber.e("title data is" + title)
            val dealers = intent.getStringExtra("dealers")
            Timber.e("dealers data is =  " + dealers)
            isExport = intent.getBooleanExtra("isExport", true)
            isActive = intent.getBooleanExtra("isActive", true)
            stateId = intent.getStringExtra("stateId")
            cityId = intent.getStringExtra("cityId")
            val myType = object : TypeToken<ArrayList<Dealer>>() {}.type
            val tempList = gson.fromJson<ArrayList<Dealer>>(dealers, myType)
            if (tempList.isNotEmpty()) {
                dealersList.addAll(tempList)
            }
        }
        initView()
        setContentView(binding.root)
    }

    private fun initView() {
        binding.toolbar.linearBack.setOnClickListener {
            finish()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = IncomeListAdapter(this);
        binding.recyclerView.adapter = adapter
        adapter.submitList(dealersList)
        binding.toolbar.btnExport.visibility = if (isExport) View.VISIBLE else View.INVISIBLE
        title?.let {
            binding.toolbar.headingText.text = it
        }
        binding.toolbar.btnExport.setOnClickListener(View.OnClickListener {
            val url =
                "http://65.2.21.88/exportToExcel?isActive=" + isActive + "&stateId=" + stateId + "&cityId=" + cityId + "&referCode=" + dealer?.referCode + "&dealerId=" + dealer?.dealerId
            val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent1)
        })
    }
}