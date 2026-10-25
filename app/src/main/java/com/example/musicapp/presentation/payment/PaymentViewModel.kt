package com.example.musicapp.presentation.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {

    private val _selectedMethodId = MutableLiveData<String>()
    val selectedMethodId: LiveData<String> = _selectedMethodId

    fun selectMethod(id: String) {
        _selectedMethodId.value = id
    }
}