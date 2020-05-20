package com.goodsoft.mymoney.implementations.backdrop

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel


class BackdropViewModel : ViewModel() {
    val backdropExpandIconIsVisible = ObservableBoolean()
    val backdropTitle = ObservableField<String>()
}