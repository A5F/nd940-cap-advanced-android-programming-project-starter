package com.example.android.politicalpreparedness.presentation.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.domain.base.ResponseInterface
import com.example.android.politicalpreparedness.domain.base.observeWithResource
import com.example.android.politicalpreparedness.presentation.election.adapter.ElectionListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class ElectionsFragment: Fragment(), ResponseInterface {

    // Declare ViewModel
    private val electionViewModel :ElectionsViewModel by inject()

    private val electionAdapter = ElectionListAdapter { election ->
        // Link elections to voter info
            this@ElectionsFragment.findNavController()
                .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
    }

    private val savedElectionAdapter = ElectionListAdapter{election ->
        // Link elections to voter info
       this@ElectionsFragment.findNavController()
                .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
    }
    private lateinit var viewBinding: FragmentElectionBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewBinding = FragmentElectionBinding.inflate(inflater, container, false)
        // Add ViewModel values and create ViewModel
        // Add binding values
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.electionViewModel = electionViewModel



        //Initiate recycler adapters
        viewBinding.listElections.adapter = electionAdapter
        viewBinding.listSavedElections.adapter = savedElectionAdapter
        //Populate recycler adapters
        electionViewModel.getData()

        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }

    private fun initObserver(){
        electionViewModel.electionsLiveData.observeWithResource(this,
            onLoading = {
                viewBinding.statusLoadingWheel.isVisible = it
            }
        ) {
            Log.d("electionsLiveData", it.toString())
            electionAdapter.submitList(it)
        }

        electionViewModel.savedElectionsLiveData.observeWithResource(this,
            onLoading = {
                // do nothing, is a local query
            }
        ) {
            Log.d("savedElectionsLiveData", it.toString())
            viewBinding.labelNoSavedElections.isVisible = it.isEmpty()
            viewBinding.listSavedElections.isVisible = it.isNotEmpty()

            savedElectionAdapter.submitList(it)
        }

    }

    override fun onError(error: Throwable) {
        super.onError(error)
        showError()
    }

    private fun showError(){
        Toast.makeText(requireActivity(), getString(R.string.label_err_retrieve_elections), Toast.LENGTH_LONG).show()
    }
}