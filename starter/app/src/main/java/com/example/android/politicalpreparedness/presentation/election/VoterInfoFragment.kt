package com.example.android.politicalpreparedness.presentation.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.domain.base.ResponseInterface
import com.example.android.politicalpreparedness.domain.base.observeWithResource
import org.koin.android.ext.android.inject

class VoterInfoFragment : Fragment(), ResponseInterface {

    private val voterInfoViewModel :VoterInfoViewModel by inject()
    lateinit var  viewBinding : FragmentVoterInfoBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Add ViewModel values and create ViewModel
        // Add binding values
        viewBinding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.viewModel = voterInfoViewModel

        // Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        arguments?.let {
            val division = VoterInfoFragmentArgs.fromBundle(it).argDivision
            val electionId = VoterInfoFragmentArgs.fromBundle(it).argElectionId
            voterInfoViewModel.getElectionData(division, electionId.toLong())
        }

        // Handle loading of URLs
        voterInfoViewModel.webUrl.observe(viewLifecycleOwner) {
            loadUrl(it)
        }

        // Handle save button UI state
//        voterInfoViewModel.electionSaved.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                viewBinding.actionFollow.text = getString(R.string.label_unfollow_election)
//            } else {
//                viewBinding.actionFollow.text = getString(R.string.label_follow_election)
//            }
//        })

//        voterInfoViewModel.state.observe(viewLifecycleOwner, Observer {
//            if (it is State.ERROR) {
//                it.message?.let { message ->
//                    Snackbar.make(viewBinding.root, message, Snackbar.LENGTH_SHORT).show()
//                }
//            }
//        })
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver(){
        voterInfoViewModel.voterInfoLiveData.observeWithResource(this,
            onLoading = {
                viewBinding.statusLoading.isVisible = it
            }, onError = {
                Log.d("voterInfoLiveData", it.toString())
            }){
            Log.d("voterInfoLiveData", it.toString())
            (activity as AppCompatActivity).supportActionBar?.title= it.election.name
            viewBinding.voterInfo = it
        }

        voterInfoViewModel.electionLiveData.observeWithResource(this,
            onLoading = {

            }, onError = {
                Log.d("electionLiveData", it.toString())
                if (it.message =="ELECTION_NOT_ALREADY_SAVED"){
                    viewBinding.actionUpdate.text = getString(R.string.label_follow_election)
                }
            }) {
            Log.d("electionLiveData", it.toString())
            viewBinding.actionUpdate.text = getString(R.string.label_unfollow_election)
        }

    }


    // Create method to load URL intents
    private fun loadUrl(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }


    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.title= getString(R.string.app_name)
        super.onDestroyView()
    }
}