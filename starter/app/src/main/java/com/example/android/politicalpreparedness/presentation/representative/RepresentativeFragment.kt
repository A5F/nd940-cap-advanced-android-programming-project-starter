package com.example.android.politicalpreparedness.presentation.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.presentation.election.ElectionsViewModel
import com.example.android.politicalpreparedness.presentation.representative.adapter.RepresentativeListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.util.Locale

class DetailFragment : Fragment() {

    companion object {
        // Add Constant for Location request
        private val REQUEST_LOCATION_PERMISSION = 1
    }

    // Declare ViewModel
    private val representativeViewModel : RepresentativeViewModel by inject()
    private lateinit var viewBinding: FragmentRepresentativeBinding


    val representativeAdapter= RepresentativeListAdapter()
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Establish bindings
        viewBinding = FragmentRepresentativeBinding.inflate(inflater, container, false)
        viewBinding.viewModel = representativeViewModel
        viewBinding.lifecycleOwner = this
        if (savedInstanceState != null) {
            val uiState = savedInstanceState.getBundle("motionState")
            viewBinding.motionLayout.transitionState = uiState
        }
        // Define and assign Representative adapter
        viewBinding.representativeList.adapter = representativeAdapter
        //todo submit list
        //TODO: Populate Representative adapter


        viewBinding.buttonLocation.setOnClickListener {
            hideKeyboard()
            if (checkLocationPermissions()) {
                getLocation()
            }
        }

        //TODO: Establish button listeners for field and location search
//        representativeViewModel.showSnackBarInt.showSnackBarInt.observe(viewLifecycleOwner, Observer {
//            Snackbar.make(viewBinding.root, it, Snackbar.LENGTH_LONG).show()
//        })
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

    override fun onSaveInstanceState(outState: Bundle) {
        val state = viewBinding.motionLayout.transitionState
        outState.putBundle("motionState", state)
        super.onSaveInstanceState(outState)
    }

}