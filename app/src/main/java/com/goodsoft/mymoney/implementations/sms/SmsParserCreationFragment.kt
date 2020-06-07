package com.goodsoft.mymoney.implementations.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.SmsParserCreationActivityBinding


class SmsParserCreationFragment : Fragment() {

    private lateinit var binding: SmsParserCreationActivityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms_parser_creation, container, false)
        binding.close.setOnClickListener {
            if (!binding.smsParserNavHostFragment.findNavController().popBackStack()) {
                findNavController().popBackStack()
            }
        }
        return binding.root
    }
}