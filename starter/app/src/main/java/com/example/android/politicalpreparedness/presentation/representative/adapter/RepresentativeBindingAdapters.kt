package com.example.android.politicalpreparedness.presentation.representative.adapter

import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.domain.base.ResourceState
import com.example.android.politicalpreparedness.presentation.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.presentation.representative.model.Representative

@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    if (src!= null){
        val uri = src.toUri().buildUpon().scheme("https").build()
        // Add Glide call to load image and circle crop - user ic_profile as a placeholder and for errors.
        //i prefer coil lib... the glide impl is like this
//        GlideApp.load(uri)
//        .placeholder(R.drawable.ic_profile)
//         .error(R.drawable.ic_profile)
//         .into(view)

        view.load(uri){
            transformations(CircleCropTransformation())
            placeholder(ContextCompat.getDrawable(view.context, R.drawable.ic_profile))
            error(ContextCompat.getDrawable(view.context, R.drawable.ic_profile))
        }

    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T>{
    return adapter as ArrayAdapter<T>
}

@BindingAdapter("shouldShowView")
fun shouldShowView(textView: TextView, any: Any?) {
    textView.visibility = if (any != null) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("loaderState")
fun bindLoaderState(progressBar: ProgressBar, state: ResourceState?) {
    when (state) {
        ResourceState.LOADING -> progressBar.visibility = View.VISIBLE
        ResourceState.ERROR -> progressBar.visibility = View.GONE
        ResourceState.SUCCESS -> progressBar.visibility = View.GONE
    }
}

