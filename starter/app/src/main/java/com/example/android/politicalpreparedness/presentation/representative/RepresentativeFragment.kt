package com.example.android.politicalpreparedness.presentation.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.domain.base.ResponseInterface
import com.example.android.politicalpreparedness.domain.base.observeWithResource
import com.example.android.politicalpreparedness.presentation.representative.adapter.RepresentativeListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailFragment : Fragment(), ResponseInterface  {

    // Declare ViewModel
    private val representativeViewModel : RepresentativeViewModel by viewModel()
    private lateinit var viewBinding: FragmentRepresentativeBinding




    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Establish bindings
        viewBinding = FragmentRepresentativeBinding.inflate(inflater, container, false)
        viewBinding.viewModel = representativeViewModel
        viewBinding.lifecycleOwner = this
        Log.d("onCreateView", "onCreateView" )
        if (savedInstanceState != null) {
            val uiState = savedInstanceState.getBundle("motionState")
            viewBinding.motionLayout.transitionState = uiState
        }
        // Define and assign Representative adapter
        viewBinding.representativeList.adapter = RepresentativeListAdapter()


        representativeViewModel.representativeLiveData.observeWithResource(this,
            onLoading = {
                viewBinding.statusLoadingWheel.isVisible = it
            }, onError = {
                Log.d("representativeLiveData", "error" )
            }){
            Log.d("representativeLiveData", it.toString() )

            //Populate Representative adapter
            //already in xml by data binding
            representativeViewModel.representativeList.value = it
            //representativeAdapter.submitList(it)
        }

        viewBinding.buttonSearch.setOnClickListener {
            hideKeyboard()
            val address =  Address(
                line1 = viewBinding.addressLine1.text.toString(),
                line2 = viewBinding.addressLine2.text.toString(),
                city = viewBinding.city.text.toString(),
                state = viewBinding.state.selectedItem.toString(),
                zip = viewBinding.zip.text.toString())
            if (validAddress(address)){
                representativeViewModel.setLocation(address)
            }else{
                Toast.makeText(requireContext(), "All field is required", Toast.LENGTH_LONG).show()
            }

        }


        viewBinding.buttonLocation.setOnClickListener {
            hideKeyboard()
            if (checkLocationPermissions()) {
                getLocation()
            }
        }

        return viewBinding.root

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Handle location permission result to get location on permission granted
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                Snackbar.make(viewBinding.root, getString(R.string.err_gps_permission), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            // Request Location permissions
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            false
        }
    }

    private fun isPermissionGranted() : Boolean {
        // Check if permission is already granted and return (true = granted, false = denied/other)
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        // Get location from LocationServices
        // The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
        val locationManager = requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true)
        if (provider != null) {
            val location: Location? = locationManager.getLastKnownLocation(provider)

            if (location != null) {
                val address = geoCodeLocation(location)
                representativeViewModel.setLocation(address)
            } else {
                Snackbar.make(viewBinding.root, getString(R.string.err_fetch_location), Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(viewBinding.root, getString(R.string.err_devices), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
            }
            .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("onConfigurationChanged", "onConfigurationChanged")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("onSaveInstanceState", "onSaveInstanceState")

        val state = viewBinding.motionLayout.transitionState
        outState.putBundle("motionState", state)
        super.onSaveInstanceState(outState)
    }

    private fun validAddress(address: Address): Boolean{
       return  address.line1.isNotEmpty()
            && address.line2?.isNotEmpty() == true
            && address.city.isNotEmpty()
            && address.state.isNotEmpty()
            && address.zip.isNotEmpty()
    }


    companion object {
        // Add Constant for Location request
        private val REQUEST_LOCATION_PERMISSION = 1
    }
}