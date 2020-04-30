package com.goodsoft.mymoney.implementations.main.categories.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.goodsoft.mymoney.CategoryBottomSheetBinding
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.createItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CategoryBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: CategoryBottomSheetBinding =
                DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_category, container, false)
        binding.close.setOnClickListener { dismiss() }
        binding.viewModel = CategoryViewModel(arguments?.getString(CATEGORY_ID, null))
        binding.itemBinding = createItemBinding(BR.categoryIcon, R.layout.item_category_icon, {
            binding.viewModel?.updateItems(it.categoryIcon)
        })
        binding.apply.setOnClickListener {
            if (binding.viewModel?.apply() == true) {
                dismiss()
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentAppBottomSheetDialog)
    }

    companion object {

        const val CATEGORY_ID = "categoryId"

        fun newInstance(categoryId: String? = null): CategoryBottomSheet {
            return CategoryBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_ID, categoryId)
                }
            }
        }
    }

}