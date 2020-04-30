package com.goodsoft.mymoney.core

import android.view.View
import androidx.annotation.LayoutRes
import me.tatarka.bindingcollectionadapter2.BR
import me.tatarka.bindingcollectionadapter2.ItemBinding


interface OnItemClickListener<T> {
    fun onClick(item: T)
    fun onMenuClick(view: View, item: T)
}

fun <T> createItemBinding(
        variableId: Int,
        @LayoutRes layoutRes: Int,
        onClick: ((T) -> Unit)? = null,
        onMenuClick: ((View, T) -> Unit)? = null
) = ItemBinding.of<T> { itemBinding, _, item ->
    itemBinding.set(variableId, layoutRes)
            .bindExtra(variableId, item)
            .bindExtra(BR.listener, object : OnItemClickListener<T> {
                override fun onClick(item: T) {
                    onClick?.invoke(item)
                }

                override fun onMenuClick(view: View, item: T) {
                    onMenuClick?.invoke(view, item)
                }
            })
}