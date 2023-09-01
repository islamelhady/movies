package com.elhady.movies.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.utilities.Event
import com.elhady.movies.utilities.TextValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository, val textValidation: TextValidation): ViewModel() {

    val userName = MutableLiveData("")
    val password = MutableLiveData("")

    private val _passwordHelperText = MutableLiveData("")
    val passwordHelperText: LiveData<String> = _passwordHelperText

    private val _userNameHelperText = MutableLiveData("")
    val userNameHelperText: LiveData<String> = _userNameHelperText

    private val _loginRequestState = MutableLiveData<State<Boolean>>()
    val loginRequestState: LiveData<State<Boolean>> = _loginRequestState

    private val _loginEvent = MutableLiveData<Event<Boolean>>()
    val loginEvent: LiveData<Event<Boolean>> = _loginEvent


    fun onClickLogin() {
        if (isValidUserName() && isValidPassword()) {
            whenFormIsValid()
        }
    }

    private fun isValidPassword(): Boolean {
        val validPassword = textValidation.validPassword(password.value.toString())
        _passwordHelperText.postValue(validPassword)
        return validPassword == null
    }

    private fun isValidUserName(): Boolean {
        val validUserName = textValidation.validFiled(userName.value.toString())
        _userNameHelperText.postValue(validUserName)
        return validUserName == null
    }


    private fun whenFormIsValid() {
        login()
    }

    fun login() {
        viewModelScope.launch {
            accountRepository.loginWithUsernameAndPassword(userName.value.toString(), password.value.toString())
        }
    }

    private fun onLoginSuccessfully() {
        _loginRequestState.postValue(State.Success(true))
        _loginEvent.postValue(Event(true))
        resetForm()

    }

    private fun onLoginError(message: String) {
        _loginRequestState.postValue(State.Error(message))
        _passwordHelperText.postValue(message)

    }


    private fun resetForm() {
        userName.postValue(null)
        password.postValue(null)
    }

}