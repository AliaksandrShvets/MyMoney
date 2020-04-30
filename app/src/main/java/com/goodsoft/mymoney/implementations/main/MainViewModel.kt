package com.goodsoft.mymoney.implementations.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val backdropExpandIconIsVisible = ObservableBoolean()
    val backdropTitle = ObservableField<String>()
}