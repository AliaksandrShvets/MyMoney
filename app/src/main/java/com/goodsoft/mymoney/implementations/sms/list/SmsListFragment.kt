package com.goodsoft.mymoney.implementations.sms.list

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.SmsListBottomSheetBinding
import com.goodsoft.mymoney.core.checkPermissions
import com.goodsoft.mymoney.core.createItemBinding
import com.goodsoft.mymoney.implementations.sms.parser.SmsEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.tatarka.bindingcollectionadapter2.recyclerview.BR


class SmsListFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<SmsListViewModel>()
    private lateinit var binding: SmsListBottomSheetBinding

    companion object {
        const val PERMISSIONS_REQUEST_READ_SMS = 1
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms_list, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.smsItem, R.layout.item_sms, {
            val bundle = SmsListFragmentArgs(it).toBundle()
            findNavController().navigate(R.id.action_smsListFragment_to_smsParserCreationFragment, bundle)
        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkPermissions(Manifest.permission.READ_SMS)) {
            requestPermissions(arrayOf(Manifest.permission.READ_SMS), PERMISSIONS_REQUEST_READ_SMS)
        } else {
            getSmsList()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_SMS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getSmsList()
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun getSmsList() {
        val cursor = requireContext().contentResolver.query(
                Uri.parse("content://sms/inbox"),
                arrayOf("address", "body"),
                null, null, null)
        viewModel.setSmsList(generateSequence { if (cursor?.moveToNext() == true) cursor else null }
                .map { SmsEntity(it.getString(0), it.getString(1)) })
        cursor?.close()
    }
}