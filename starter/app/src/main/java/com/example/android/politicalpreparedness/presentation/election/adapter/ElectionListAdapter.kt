package com.example.android.politicalpreparedness.presentation.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding

class ElectionListAdapter(private val clickListener: (Election) -> Unit)
    : ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    // Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

// Create ElectionViewHolder
class ElectionViewHolder(private val viewBinding: ItemElectionBinding): RecyclerView.ViewHolder(viewBinding.root) {
    // Add companion object to inflate ViewHolder (from)
    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val viewBinding = ItemElectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ElectionViewHolder(viewBinding)
        }
    }

    fun bind(data: Election, clickListener: (Election) -> Unit ) {
        viewBinding.electionData = data
        viewBinding.root.setOnClickListener {
            clickListener(data)
        }
        viewBinding.executePendingBindings()
    }
}

// Create ElectionDiffCallback
class ElectionDiffCallback: DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }
}



// Create ElectionListener
//i can create like this, but I prefer the approach  I used because it is more compact and has less code, is that OK?
//interface ElectionListener {
//    fun onElectionItemSelected(election: Election)
//}