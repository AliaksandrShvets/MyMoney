package com.goodsoft.mymoney.implementations.sms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.SmsParserCreationActivityBinding


class SmsParserCreationActivity : AppCompatActivity() {

    private lateinit var binding: SmsParserCreationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sms_parser_creation)
        binding.close.setOnClickListener { onBackPressed() }
    }

}