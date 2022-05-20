package com.technoecorp.gorilladealer.ui.gallery

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.gorilladealer.databinding.FragmentGalleryBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.utils.PermissionUtils
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

class GalleryFragment : Fragment() {

    @Inject
    lateinit var factory: GalleryViewModelFactory
    private lateinit var viewModel: GalleryViewModel

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding get() = _binding!!
    private lateinit var bundle: Bundle
    private lateinit var type: String
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var photoPdfAdapter: PhotoPdfAdapter
    private lateinit var customDialogClass: CustomDialogClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getGallerySubComponent()
            .inject(this)
        viewModel = ViewModelProvider(this, factory)[GalleryViewModel::class.java]
        bundle = requireArguments()
        type = bundle.getString("type", "")
        checkPermission()
        initView()
        initCollector()
        return binding.root
    }

    private fun initView() {
        customDialogClass = CustomDialogClass(requireContext())
        binding.galleryRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        when (type) {
            "video" -> {
                videoAdapter = VideoAdapter(requireContext())
                binding.galleryRecycler.adapter = videoAdapter
            }
            else -> {
                photoPdfAdapter = PhotoPdfAdapter(requireContext())
                binding.galleryRecycler.adapter = photoPdfAdapter
            }
        }
        viewModel.getGalleryData(type)
    }

    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            viewModel.gallery.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        if (!customDialogClass.isShowing) {
                            customDialogClass.show()
                        }
                    }
                    is ResultWrapper.Error -> {
                        if (customDialogClass.isShowing) {
                            customDialogClass.dismiss()
                        }
                        requireContext().showShortToast(result.message)
                    }
                    is ResultWrapper.Success -> {
                        if (customDialogClass.isShowing) {
                            customDialogClass.dismiss()
                        }
                        result.data?.status?.let {
                            if (it) {
                                when (type) {
                                    "video" -> {
                                        videoAdapter.submitList(result.data?.data)
                                    }
                                    else -> {
                                        photoPdfAdapter.submitList(result.data?.data)
                                    }
                                }

                            } else {
                                requireContext().showShortToast(result.data?.message)
                            }
                        }

                    }
                }
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!PermissionUtils.checkStorage(requireContext())) {
                PermissionUtils.requestStorage(requestPermissionLauncher)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Timber.e("Permission Granted")
            }
        }
}