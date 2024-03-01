package com.elhady.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.elhady.base.BaseInteractionListener
import com.elhady.base.BasePagingAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.models.MediaUiState

class MediaSearchAdapter(listener: MediaSearchInteractionListener): BasePagingAdapter<MediaUiState>(
    MediaSearchComparator, listener = listener) {

    override val layoutID: Int = R.layout.item_media_search


    object MediaSearchComparator: DiffUtil.ItemCallback<MediaUiState>() {
        override fun areItemsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean {
            return true
        }
    }

}

interface MediaSearchInteractionListener: BaseInteractionListener {
    fun onClickMediaResult(media: MediaUiState)
}
