package com.goodsoft.mymoney.implementations.main.sms

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.createItemBinding
import com.goodsoft.mymoney.databinding.FragmentSmsParsersBinding
import com.goodsoft.mymoney.implementations.sms.SmsParserCreationActivity
import me.tatarka.bindingcollectionadapter2.BR


class SmsParsersFragment : Fragment() {

    private val viewModel by viewModels<SmsParsersViewModel>()
    private lateinit var binding: FragmentSmsParsersBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms_parsers, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.smsParserItem, R.layout.item_sms_parser, {
            startSmsParserCreationActivity()
        })
        binding.create.setOnClickListener { startSmsParserCreationActivity() }
        return binding.root
    }

    private fun startSmsParserCreationActivity() {
        startActivity(Intent(requireContext(), SmsParserCreationActivity::class.java))
    }

}