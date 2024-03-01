package com.elhady.viewmodel.favorite.details

import androidx.lifecycle.SavedStateHandle
import com.elhady.base.BaseViewModel
import com.elhady.usecase.favList.GetFavListDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavListDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFavListDetailsUseCase: GetFavListDetailsUseCase,
    private val mediaUiStateMapper: MediaUiStateMapper
) : BaseViewModel<ListDetailsUIState, ListDetailsUiEvent>(ListDetailsUIState()),
    ListDetailsInteractionListener {

    val args = FavListDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true, isEmpty = false, onErrors = emptyList()) }
        tryToExecute(
            call = { getFavListDetailsUseCase(args.mediaId)},
            onSuccess = ::onSuccessFavourite,
            mapper = mediaUiStateMapper,
            onError = ::onError
        )
    }

    private fun onSuccessFavourite(favouriteList: List<FavMediaUiState>) {
        _state.update { it.copy(isLoading = false, onErrors = emptyList(), isEmpty = favouriteList.isEmpty(),  savedMedia = favouriteList) }
    }

    private fun onError(throwable: Throwable){
        _state.update { it.copy(onErrors = listOf(throwable.message.toString()), isLoading = false) }
    }
    override fun onItemClick(item: FavMediaUiState) {
        sendEvent(ListDetailsUiEvent.OnItemSelected(item))
    }
}