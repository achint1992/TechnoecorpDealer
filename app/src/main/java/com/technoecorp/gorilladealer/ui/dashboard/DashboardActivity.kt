package com.technoecorp.gorilladealer.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.ActivityDashboardBinding
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.main.MainActivity
import com.technoecorp.gorilladealer.ui.main.SharedViewModel
import com.technoecorp.gorilladealer.ui.main.SharedViewModelFactory
import com.technoecorp.gorilladealer.utils.Constants
import com.technoecorp.gorilladealer.utils.Constants.Companion.certificate
import com.technoecorp.gorilladealer.utils.Constants.Companion.dealerCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var _binding: ActivityDashboardBinding? = null
    private val binding: ActivityDashboardBinding get() = _binding!!
    private val navController: NavController by lazy { initNavController() }
    private var _dealer: Dealer? = null
    private val dealer: Dealer get() = _dealer!!

    @Inject
    lateinit var mainViewModelFactory: SharedViewModelFactory
    private lateinit var mainViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as TechnoecorpApplication).getMainSubComponent().injectToDashboard(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[SharedViewModel::class.java]
        dealerData()
        setSupportActionBar(binding.dashboardContent.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            binding.dashboardContent.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.drawerArrowDrawable.color = ResourcesCompat.getColor(
            resources,
            R.color.secondaryColor,
            null
        )
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)
        binding.navigationView.itemIconTintList = null
    }

    private fun initNavController(): NavController {
        return Navigation.findNavController(binding.dashboardContent.fragmentContainerView2)
    }


    private fun createActionIntent(baseUrl: String) {
        if (dealer.payment.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(baseUrl + dealer.dealerId)
            startActivity(intent)
        }
    }

    private fun dealerData() {
        CoroutineScope(Dispatchers.Main).launch {
            _dealer = mainViewModel.dealerData()
            (application as TechnoecorpApplication).updateDealer(dealer)
        }
    }

    private fun goBack() {
        if (navController.currentDestination?.id != R.id.dashboardFragment) {
            navController.navigateUp()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                goBack()
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_Kyc -> {
                goBack()
                navController.navigate(R.id.action_dashboardFragment_to_kycFragment)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_idCard -> {
                createActionIntent(dealerCard)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_certificate -> {
                createActionIntent(certificate)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_edit -> {
                goBack()
                navController.navigate(R.id.action_dashboardFragment_to_editProfileFragment)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_video -> {
                goBack()
                val bundle =
                    bundleOf("type" to "video")
                navController.navigate(R.id.action_dashboardFragment_to_galleryFragment, bundle)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_photo -> {
                goBack()
                val bundle =
                    bundleOf("type" to "photo")
                navController.navigate(R.id.action_dashboardFragment_to_galleryFragment, bundle)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_pdf -> {
                goBack()
                val bundle =
                    bundleOf("type" to "pdf")
                navController.navigate(R.id.action_dashboardFragment_to_galleryFragment, bundle)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_policy -> {
                goBack()
                val bundle =
                    bundleOf("title" to getString(R.string.policy_title), "url" to Constants.policy)
                navController.navigate(R.id.action_dashboardFragment_to_webViewActivity, bundle)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_terms -> {
                goBack()
                val bundle =
                    bundleOf("title" to getString(R.string.terms_title), "url" to Constants.terms)
                navController.navigate(R.id.action_dashboardFragment_to_webViewActivity, bundle)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_payment -> {
                goBack()
                navController.navigate(R.id.action_dashboardFragment_to_productListFragment)
                binding.drawer.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_logout -> {
                binding.drawer.closeDrawer(GravityCompat.START)
                CoroutineScope(Dispatchers.Main).launch {
                    mainViewModel.clearData()
                    val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                return true
            }
            else -> {
                return false
            }
        }
    }


}