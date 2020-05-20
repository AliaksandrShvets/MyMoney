package com.goodsoft.mymoney.implementations.backdrop.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.goodsoft.mymoney.AccountsFragmentBinding
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.createItemBinding


class AccountsFragment : Fragment() {

    private val viewModel by viewModels<AccountsViewModel>()
    private lateinit var binding: AccountsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accounts, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.accountItem, R.layout.item_account)
        return binding.root
    }
}