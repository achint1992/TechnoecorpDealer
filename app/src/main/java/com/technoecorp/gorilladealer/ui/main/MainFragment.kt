package com.technoecorp.gorilladealer.ui.main

import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentMainBinding
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    @Inject
    lateinit var mainViewModelFactory: SharedViewModelFactory
    private lateinit var mainViewModel: SharedViewModel
    private var count = 0
    private var textList = arrayOf(
        "India's Best mobile security app",
        "20+ Features Available ",
        "Become a Dealer in minimal price amount",
        "Start your earning in few simple steps",
        "Sign up now to join gorilla army"
    )
    private var textView: TextView? = null
    private lateinit var expandAnimation: ObjectAnimator
    private lateinit var shrinkAnimation: ObjectAnimator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getMainSubComponent().inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[SharedViewModel::class.java]
        dealerData()

        initView()
        initAnimation()
        return binding.root
    }

    private fun dealerData() {
        CoroutineScope(Dispatchers.Main).launch {
            (requireActivity().application as TechnoecorpApplication).updateDealer(mainViewModel.dealerData())
        }
    }


    override fun onResume() {
        super.onResume()
        expandAnimation.resume()
        shrinkAnimation.resume()
    }

    override fun onStop() {
        super.onStop()
        expandAnimation.pause()
        shrinkAnimation.pause()
    }

    private fun initView() {
        binding.loginButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
        }
        CoroutineScope(Dispatchers.Main).launch {
            val isLoggedIn = mainViewModel.shouldNavigateToDashBoard()

            if (isLoggedIn) {
                binding.registerButton.visibility = View.INVISIBLE
                binding.loginButton.visibility = View.INVISIBLE
                delay(6000)
                binding.registerButton.findNavController()
                    .navigate(R.id.action_mainFragment_to_dashboardActivity)
                requireActivity().finish()
            }
        }

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(requireActivity().intent)
            .addOnSuccessListener(requireActivity())
            { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                val deepLink: Uri?
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    val referLink = deepLink.toString()
                    try {
                        val referId = referLink.substring(referLink.lastIndexOf("=") + 1)
                        saveReferCode(referId)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            .addOnFailureListener(requireActivity()) { e -> Timber.w("Dynamic links ${e.message}") }

    }

    fun saveReferCode(referCode: String) {
        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.saveReferCode(referCode)
        }
    }

    private fun initAnimation() {
        expandAnimation = ObjectAnimator.ofFloat(binding.view6, "scaleX", 1f)
        expandAnimation.duration = 1000
        expandAnimation.repeatMode = ObjectAnimator.REVERSE
        shrinkAnimation = ObjectAnimator.ofFloat(binding.view6, "scaleX", 0f)
        shrinkAnimation.duration = 1000
        shrinkAnimation.startDelay = 2000
        shrinkAnimation.start()
        val slideAnim =
            AnimationUtils.loadAnimation(requireContext(), R.anim.anim_slide_up_fade_in)
        binding.tvSwitch.setFactory {
            textView = TextView(requireContext())
            textView!!.setTextColor(
                ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.secondaryColor,
                    null
                )
            )
            textView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            textView!!.gravity = Gravity.CENTER_HORIZONTAL
            textView!!.setTypeface(
                ResourcesCompat.getFont(requireContext(), R.font.proxinova),
                Typeface.NORMAL
            )
            textView
        }
        binding.tvSwitch.inAnimation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_in
        )
        binding.tvSwitch.outAnimation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_out
        )
        slideAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                //animation Starts here
            }

            override fun onAnimationEnd(animation: Animation) {
                ViewCompat.animate(binding.view6).alpha(1f)
                ViewCompat.animate(binding.tvSwitch).alpha(1f)
            }

            override fun onAnimationRepeat(animation: Animation) {
                //animation if repeat
            }
        })
        binding.linear.startAnimation(slideAnim)
        expandAnimation.addListener({
            shrinkAnimation.startDelay = 0
            shrinkAnimation.start()
        })

        shrinkAnimation.addListener({
            expandAnimation.start()
            binding.tvSwitch.setText(textList[count])
            if (count == 4) {
                count = 0
            } else {
                count++
            }
        })

        shrinkAnimation.addUpdateListener { valueAnimator ->
            if (valueAnimator.animatedFraction > 0.5) {
                binding.tvSwitch.alpha = 1 - valueAnimator.animatedFraction
            }
        }


        expandAnimation.addUpdateListener { valueAnimator ->
            binding.tvSwitch.alpha = abs(valueAnimator.animatedFraction)
        }
    }

}