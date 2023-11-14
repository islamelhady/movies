package com.elhady.movies.utilities

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.data.remote.response.video.ResultDto
import com.elhady.movies.databinding.ItemChipCategoryBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BasePagingAdapter
import com.elhady.movies.ui.category.CategoryGenreUiState
import com.elhady.movies.ui.category.CategoryInteractionListener
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun <T> LifecycleOwner.collectLast(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action)
        }
    }
}

fun <T> LifecycleOwner.collect(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(action)
        }
    }
}

fun <T : Any> GridLayoutManager.setSpanSize(
    footerAdapter: LoadAdapter, adapter: BasePagingAdapter<T>, spanCount: Int
) {
    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if ((position == adapter.itemCount) && footerAdapter.itemCount > 0) {
                spanCount
            } else {
                1
            }
        }
    }
}

fun Date.convertToDayMonthYearFormat(): String {
    val formatter = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
    return formatter.format(this)
}

@BindingAdapter(value = ["app:movieHours", "app:movieMinutes"])
fun setDuration(view: TextView, hours: Int?, minutes: Int?) {
    if (hours == 0) {
        view.text = String.format(view.context.getString(R.string.minutes_pattern), minutes)
    } else if (minutes == 0) {
        view.text = String.format(view.context.getString(R.string.hours_pattern), hours)
    } else {
        view.text =
            String.format(view.context.getString(R.string.hours_minutes_pattern), hours, minutes)
    }
}

fun <T> ChipGroup.createChip(item: CategoryGenreUiState, listener: T): View {
    val chipBinding: ItemChipCategoryBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.item_chip_category, this, false
    )
    chipBinding.item = item
    chipBinding.listener = listener as CategoryInteractionListener
    return chipBinding.root
}

fun List<ResultDto?>.getKey(): String? = this.map {
    if (it?.type == "Trailer") return it.key
}.toString()