package com.goodsoft.mymoney.implementations.backdrop

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.goodsoft.mymoney.BackdropFragmentBinding
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.findBehavior
import com.goodsoft.mymoney.widgets.BackdropBehavior


class BackdropFragment : Fragment() {

    private val viewModel by viewModels<BackdropViewModel>()

    private lateinit var binding: BackdropFragmentBinding
    private lateinit var backdropBehavior: BackdropBehavior

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_backdrop, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackdrop()
        initNavigation()
        initOnBackPressed()
    }

    private fun initBackdrop() {
        backdropBehavior = binding.frontLayout.findBehavior()
        backdropBehavior.attachBackLayout(R.id.backLayout)
        backdropBehavior.addOnDropListener(object : BackdropBehavior.OnDropListener {
            override fun onDrop(dropState: BackdropBehavior.DropState, fromUser: Boolean) {
                viewModel.backdropExpandIconIsVisible.set(dropState == BackdropBehavior.DropState.OPEN)
            }
        })
        binding.backdropExpandIcon.setOnClickListener { backdropBehavior.close() }
    }

    private fun initNavigation() {
        binding.navigationView.setNavigationItemSelectedListener { item ->
            backdropBehavior.close()
            viewModel.backdropTitle.set(item.title?.toString())
            when (item.itemId) {
                R.id.menuMain -> binding.backdropNavHostFragment.findNavController().navigate(R.id.mainFragment)
                R.id.menuCategories -> binding.backdropNavHostFragment.findNavController().navigate(R.id.categoriesFragment)/*
                R.id.menuAccounts -> binding.backdropNavHostFragment.findNavController().navigate(R.id.accountsFragment)*/
                R.id.menuSmsParser -> binding.backdropNavHostFragment.findNavController().navigate(R.id.smsParsersFragment)
            }
            true
        }
        viewModel.backdropTitle.set(binding.navigationView.checkedItem?.title?.toString())
    }

    private fun initOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!backdropBehavior.close()) {
                    activity?.finish()
                }
            }
        })
    }
}