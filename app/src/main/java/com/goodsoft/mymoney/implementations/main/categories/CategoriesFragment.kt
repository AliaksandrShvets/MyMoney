package com.goodsoft.mymoney.implementations.main.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.goodsoft.mymoney.CategoriesFragmentBinding
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.createItemBinding
import com.goodsoft.mymoney.database.categories.CategoryEntity
import com.goodsoft.mymoney.database.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.implementations.main.categories.editor.CategoryBottomSheet


class CategoriesFragment : Fragment() {

    private val viewModel by viewModels<CategoriesViewModel>()
    private lateinit var binding: CategoriesFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.categoryItem, R.layout.item_category, {}, { view, item ->
            showMenu(view, item.categoryEntity)
        })
        binding.add.setOnClickListener { showCategoryBottomSheet() }
        return binding.root
    }

    private fun showMenu(view: View, categoryEntity: CategoryEntity) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu_category_item)
        popup.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.edit -> {
                    showCategoryBottomSheet(categoryEntity)
                    viewModel.saveFavoriteCategory(categoryEntity.id)
                    true
                }
                R.id.delete -> {
                    CategoriesRoomRepository().deleteCategory(categoryEntity).subscribe({}, {})
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun showCategoryBottomSheet(categoryEntity: CategoryEntity? = null) {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(CategoryBottomSheet.newInstance(categoryEntity?.id), tag)
                ?.commitAllowingStateLoss()
    }

}