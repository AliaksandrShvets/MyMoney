package com.goodsoft.mymoney.implementations.sms.parser

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.SmsParserCreationFragmentBinding
import com.goodsoft.mymoney.core.checkPermissions
import com.goodsoft.mymoney.database.tables.parsers.SmsParserEntity
import com.goodsoft.mymoney.database.tables.parsers.SmsParsersRoomRepository
import com.goodsoft.mymoney.enums.SmsParameterEnum
import com.goodsoft.mymoney.implementations.sms.list.SmsListFragment
import com.goodsoft.mymoney.implementations.sms.list.SmsListFragmentArgs
import com.goodsoft.mymoney.implementations.sms.selector.SmsPartSelectorBottomSheet
import com.goodsoft.mymoney.implementations.sms.selector.SmsPartSelectorBottomSheet.Companion.SELECTION_END
import com.goodsoft.mymoney.implementations.sms.selector.SmsPartSelectorBottomSheet.Companion.SELECTION_START
import com.google.gson.Gson


class SmsParserCreationFragment : Fragment() {

    val viewModel by viewModels<SmsParserCreationViewModel>()
    private lateinit var binding: SmsParserCreationFragmentBinding
    private val args: SmsListFragmentArgs by navArgs()
    private val parts = hashMapOf<SmsParameterEnum, ExpressionEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms_parser_creation, container, false)
        binding.viewModel = viewModel
        binding.partAmount.setOnClickListener {
            showSmsPartSelectorBottomSheet(SmsParameterEnum.AMOUNT.code)
        }
        binding.partDate.setOnClickListener {
            showSmsPartSelectorBottomSheet(SmsParameterEnum.DATE.code)
        }
        binding.partGroup.setOnClickListener {
            showSmsPartSelectorBottomSheet(SmsParameterEnum.GROUP.code)
        }
        binding.apply.setOnClickListener {
            val smsParserEntity = SmsParserEntity(
                    address = args.sms.address,
                    body = args.sms.body,
                    parts = Gson().toJson(parts)
            )
            SmsParsersRoomRepository().insert(smsParserEntity).subscribe({
                requireActivity().finish()
            }, {})
            if (checkPermissions(Manifest.permission.RECEIVE_SMS)) {
                requestPermissions(arrayOf(Manifest.permission.RECEIVE_SMS), SmsListFragment.PERMISSIONS_REQUEST_READ_SMS)
            } else {
                requireActivity().finish()
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            SmsParameterEnum.getElementByCode(requestCode)?.let { smsParameter ->
                val selectionStart = data?.getIntExtra(SELECTION_START, 0) ?: 0
                val selectionEnd = data?.getIntExtra(SELECTION_END, 0) ?: 0
                val messageRegex = args.sms.body.replaceRange(selectionStart, selectionEnd, REGEXP_REPLACEMENT)
                messageRegex.split("; ").forEachIndexed { index, expression ->
                    if (expression.contains(REGEXP_REPLACEMENT)) {
                        val parameter = parts.toList().firstOrNull {it.second.delimiterIndex == index }?.first ?: smsParameter
                        if (parameter == smsParameter) {
                            parts[smsParameter] = ExpressionEntity(selectionStart, selectionEnd, expression, index)
                            setSpannable(smsParameter, selectionStart, selectionEnd)
                        } else {
                            Toast.makeText(requireContext(), "Выражение уже используется для \"${getString(parameter.stringRes)}\"", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setSpannable(smsElementEnum: SmsParameterEnum, selectionStart: Int, selectionEnd: Int) {
        val spannableSms = SpannableString(args.sms.body)
        parts.forEach {
            spannableSms.setSpan(
                    BackgroundColorSpan(ContextCompat.getColor(requireContext(), it.key.spanColor)),
                    it.value.start, it.value.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        viewModel.spannableSms.set(spannableSms)
        when (smsElementEnum) {
            SmsParameterEnum.AMOUNT -> {
                binding.partAmount.setText(args.sms.body.substring(selectionStart, selectionEnd))
                binding.partAmountLayout.setEndIconOnClickListener {
                    binding.partAmount.text?.clear()
                    binding.partAmountLayout.setEndIconOnClickListener(null)
                }
            }
            SmsParameterEnum.DATE -> {
                binding.partDate.setText(args.sms.body.substring(selectionStart, selectionEnd))
                binding.partDateLayout.setEndIconOnClickListener {
                    binding.partDate.text?.clear()
                    binding.partDateLayout.setEndIconOnClickListener(null)
                }
            }
            SmsParameterEnum.GROUP -> {
                viewModel.group.set(args.sms.body.substring(selectionStart, selectionEnd))
                binding.partGroupLayout.setEndIconOnClickListener {
                    binding.partGroup.text?.clear()
                    binding.partGroupLayout.setEndIconOnClickListener(null)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.sms.set(args.sms)
        viewModel.spannableSms.set(args.sms.body)
    }

    private fun showSmsPartSelectorBottomSheet(requestCode: Int) {
        val smsBody = viewModel.sms.get()?.body ?: ""
        val dialog = SmsPartSelectorBottomSheet.newInstance(smsBody)
        dialog.setTargetFragment(this, requestCode)
        parentFragmentManager
                .beginTransaction()
                .add(dialog, SmsPartSelectorBottomSheet::class.java.name)
                .commitAllowingStateLoss()
    }

    companion object {
        const val REGEXP_REPLACEMENT = "([\\S ]+)"
    }
}