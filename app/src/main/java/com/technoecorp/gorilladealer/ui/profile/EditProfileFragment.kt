package com.technoecorp.gorilladealer.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.request.UpdateProfileRequest
import com.technoecorp.domain.domainmodel.response.auth.otp.City
import com.technoecorp.domain.domainmodel.response.auth.otp.Country
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.State
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentEditProfileBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.utils.NetworkChecker
import com.technoecorp.gorilladealer.utils.Validator
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding: FragmentEditProfileBinding get() = _binding!!

    @Inject
    lateinit var factory: ProfileViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private lateinit var customDialogClass: CustomDialogClass
    private lateinit var countryAdapter: AutoCompleteAdapter<Country>
    private lateinit var stateAdapter: AutoCompleteAdapter<State>
    private lateinit var cityAdapter: AutoCompleteAdapter<City>
    private var countryId = -1
    private var stateId = -1
    private var cityId = -1
    private var _dealer: Dealer? = null
    private val dealer: Dealer get() = _dealer!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getProfileSubComponent()
            .inject(this)
        _dealer = (requireActivity().application as TechnoecorpApplication).getUpdateDealer()
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        initView()
        initCollector()
        return binding.root
    }

    private fun initView() {
        customDialogClass = CustomDialogClass(requireActivity())
        countryAdapter =
            AutoCompleteAdapter(
                requireContext(), R.layout.state_selected_item, R.id.stateName,
                ArrayList()
            )
        stateAdapter =
            AutoCompleteAdapter(
                requireContext(), R.layout.state_selected_item, R.id.stateName,
                ArrayList()
            )
        cityAdapter =
            AutoCompleteAdapter(
                requireContext(), R.layout.state_selected_item, R.id.stateName,
                ArrayList()
            )
        binding.userCity.setDropDownBackgroundResource(R.color.white)
        binding.userCountry.setDropDownBackgroundResource(R.color.white)
        binding.userState.setDropDownBackgroundResource(R.color.white)

        binding.userCountry.setAdapter(countryAdapter)
        binding.userState.setAdapter(stateAdapter)
        binding.userCity.setAdapter(cityAdapter)
        if (checkNetwork()) {
            viewModel.getCountry()
        }
        binding.userCountry.setOnItemClickListener { adapterView, _, i, _ ->
            val item: Any = adapterView.getItemAtPosition(i)
            countryId = setItemData(item)
        }

        binding.userState.setOnItemClickListener { adapterView, _, i, _ ->
            val item: Any = adapterView.getItemAtPosition(i)
            stateId = setItemData(item)
        }
        binding.userCity.setOnItemClickListener { adapterView, _, i, _ ->
            val item: Any = adapterView.getItemAtPosition(i)
            cityId = setItemData(item)
        }
        dealer.country?.let {
            binding.userCountry.setText(it.countryName)
        }
        dealer.state?.let {
            binding.userState.setText(it.stateName)
        }
        dealer.city?.let {
            binding.userCity.setText(it.cityName)
        }

        binding.userEmail.setText(dealer.email)
        binding.userAltMobile.setText(dealer.alternetMobileNo1)
        binding.userAddress.setText(dealer.userAddress)
        binding.userDistrict.setText(dealer.districtName)
        binding.userPincode.setText(dealer.pincode)
        binding.fatherName.setText(dealer.fatherName)
        binding.dob.setText(dealer.dob)

        binding.registerButton.setOnClickListener {
            val checkAddress: Boolean = Validator.validTextField(binding.userAddress, "Address")
            val checkDistrict: Boolean = Validator.validTextField(binding.userDistrict, "District")
            val emailCheck = Validator.validEmailAddress(binding.userEmail)
            val userAltMobileCheck = Validator.validMobileNumber(binding.userAltMobile)
            val fatherNameCheck = Validator.validTextField(binding.fatherName, "Father Name")
            val pinCodeCheck = Validator.validOtpCodeWithLength(binding.userPincode, 6)
            val dobCheck = Validator.validDateField(binding.dob, "DOB")
            val countryCheck =
                Validator.validDropDownSelection(binding.userCountry, "Country", countryId, -1)
            val stateCheck =
                Validator.validDropDownSelection(binding.userState, "State", stateId, -1)
            val cityCheck = Validator.validDropDownSelection(binding.userCity, "City", cityId, -1)

            if (checkAddress && checkDistrict && emailCheck && userAltMobileCheck && fatherNameCheck
                && pinCodeCheck && dobCheck && countryCheck && stateCheck && cityCheck && checkNetwork()
            ) {
                viewModel.updateProfile(
                    UpdateProfileRequest(
                        dealer.dealerId,
                        binding.userEmail.text.toString(),
                        binding.userAltMobile.text.toString(),
                        binding.userAddress.text.toString(),
                        binding.userDistrict.text.toString(),
                        countryId,
                        stateId,
                        cityId,
                        binding.userPincode.text.toString(),
                        binding.fatherName.text.toString(),
                        binding.dob.text.toString()
                    )
                )

            }
        }
    }

    private fun setItemData(item: Any): Int {
        when (item) {
            is Country -> {
                countryId = item.id
                stateId = -1
                cityId = -1
                binding.userCity.setText("")
                binding.userState.setText("")
                if (checkNetwork()) {
                    viewModel.getState(countryId)
                }
                return item.id
            }
            is State -> {
                stateId = item.stateId
                cityId = -1
                binding.userCity.setText("")
                if (checkNetwork()) {
                    viewModel.getCity(stateId)
                }
                return item.stateId
            }
            is City -> {
                return item.cityId
            }
            else -> {
                return -1
            }
        }
    }

    private fun checkNetwork(): Boolean {
        return if (NetworkChecker.isInternetAvailable(requireContext())) {
            true
        } else {
            requireContext().showShortToast(getString(R.string.require_internet))
            false
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

    private fun setCountryData(data: CountryResponse) {
        if (!data.status) {
            requireContext().showShortToast(data.message)
            return
        }
        countryAdapter.addItems(data.data)
    }

    private fun setStateData(data: StateResponse) {
        if (!data.status) {
            requireContext().showShortToast(data.message)
            return
        }
        stateAdapter.addItems(data.data)
    }

    private fun setCityData(data: CityResponse) {
        if (!data.status) {
            requireContext().showShortToast(data.message)
            return
        }
        cityAdapter.addItems(data.data)
    }

    private fun onEditSuccess(data: OtpResponse) {
        data.status.let {
            if (!it) {
                requireContext().showShortToast(data.message)
                return@let
            }
            viewModel.saveData(data.data.data.dealer)
            requireContext().showShortToast(getString(R.string.update_success))
            binding.root.findNavController().navigateUp()
        }
    }

    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            viewModel.country.collectLatest { result ->
                checkResponse(result, ::setCountryData)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.stateList.collectLatest { result ->
                checkResponse(result, ::setStateData)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.city.collectLatest { result ->
                checkResponse(result, ::setCityData)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.updateProfile.collectLatest { result ->
                checkResponse(result, ::onEditSuccess)
            }
        }

    }


    private fun <T> checkResponse(result: ResultWrapper<T>, callback: (T) -> Unit) {
        when (result) {
            is ResultWrapper.Loading -> {
                showDialog()
            }
            is ResultWrapper.Error -> {
                dismissDialog()
                requireContext().showShortToast(result.message)
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

}