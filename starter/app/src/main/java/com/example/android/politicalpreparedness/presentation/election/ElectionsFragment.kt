package com.example.android.politicalpreparedness.presentation.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.presentation.election.adapter.ElectionListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class ElectionsFragment: Fragment() {

    // Declare ViewModel
    private val electionViewModel :ElectionsViewModel by inject()

    private val electionAdapter = ElectionListAdapter { election ->
            this@ElectionsFragment.findNavController()
                .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
    }

    private val savedElectionAdapter = ElectionListAdapter{election ->
       this@ElectionsFragment.findNavController()
                .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewBinding = FragmentElectionBinding.inflate(inflater, container, false)
        // Add ViewModel values and create ViewModel
        // Add binding values
        viewBinding.lifecycleOwner = this
        viewBinding.electionViewModel = electionViewModel

        //TODO: Link elections to voter info

        //Initiate recycler adapters
        viewBinding.listElections.adapter = electionAdapter
        viewBinding.listSavedElections.adapter = savedElectionAdapter
        //TODO: Populate recycler adapters
//        electionViewModel.fetchElectionData()
//        electionViewModel.fetchSavedData()
//        viewModel.state.observe(viewLifecycleOwner, Observer {
//            if (it is State.ERROR) {
//                it.message?.let { message ->
//                    Snackbar.make(viewBinding.root, message, Snackbar.LENGTH_SHORT).show()
//                }
//            }
//        })
        //TODO: Refresh adapters when fragment loads

        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}