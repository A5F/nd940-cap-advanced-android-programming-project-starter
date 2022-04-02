package com.example.android.politicalpreparedness.presentation.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class VoterInfoFragment : Fragment() {

    private val voterInfoViewModel :VoterInfoViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Add ViewModel values and create ViewModel
        // Add binding values
        val viewBinding : FragmentVoterInfoBinding= FragmentVoterInfoBinding.inflate(inflater, container, false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewModel = voterInfoViewModel

        // Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        arguments?.let {
            val division = VoterInfoFragmentArgs.fromBundle(it).argDivision
            val electionId = VoterInfoFragmentArgs.fromBundle(it).argElectionId
//            voterInfoViewModel.fetchElectionData(division, electionId.toLong())
//            voterInfoViewModel.onPageLoad(electionId.toLong())
            viewBinding.electionId = electionId.toLong()
        }

        // Handle loading of URLs
        voterInfoViewModel.webUrl.observe(viewLifecycleOwner, Observer {
            loadUrl(it)
        })

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
//        voterInfoViewModel.voterInfoLiveData.observe(viewLifecycleOwner, Observer {
//            (activity as AppCompatActivity).supportActionBar?.title= it.election.name
//        })
        // cont'd Handle save button clicks
        return viewBinding.root

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