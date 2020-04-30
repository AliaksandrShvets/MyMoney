package com.goodsoft.mymoney.core

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Fragment.checkPermissions(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED
}