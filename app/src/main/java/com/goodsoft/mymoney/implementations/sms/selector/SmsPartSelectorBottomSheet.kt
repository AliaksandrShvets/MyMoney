package com.goodsoft.mymoney.implementations.sms.selector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.SmsPartSelectorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SmsPartSelectorBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: SmsPartSelectorBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_sms_part_selector, container, false)
        binding.part.setText(arguments?.getString(SMS_BODY))
        binding.part.keyListener = null
        binding.part.customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = false
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = true
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false.also { menu?.clear() }
            override fun onDestroyActionMode(mode: ActionMode?) {}
        }
        binding.apply.setOnClickListener {
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent().apply {
                putExtra(SELECTION_START, binding.part.selectionStart)
                putExtra(SELECTION_END, binding.part.selectionEnd)
            })
            dismiss()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentAppBottomSheetDialog)
    }

    companion object {
        const val SELECTION_START = "selectionStart"
        const val SELECTION_END = "selectionEnd"
        private const val SMS_BODY = "smsBody"
        fun newInstance(smsBody: String): SmsPartSelectorBottomSheet {
            return SmsPartSelectorBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(SMS_BODY, smsBody)
                }
            }
        }
    }
}