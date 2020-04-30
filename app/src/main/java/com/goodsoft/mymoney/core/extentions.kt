@file:Suppress("UNCHECKED_CAST")

package com.goodsoft.mymoney.core

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.Observable


fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
    if (this is CoordinatorLayout.LayoutParams) {
        behavior as? T
                ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
    } else {
        throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")
    }
}

fun <T : Observable> T.addOnPropertyChanged(callback: (T) -> Unit) = addOnPropertyChangedCallback(
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable?, i: Int) = callback(observable as T)
        }
)