package com.goodsoft.mymoney.util

import android.os.Build
import android.view.View
import java.util.concurrent.atomic.AtomicInteger


object ViewIdGenerator {

    private val generatedId = AtomicInteger(1)

    fun generate(): Int {

        if (Build.VERSION.SDK_INT < 17) {
            while (true) {
                val result = generatedId.get()
                var newValue = result + 1
                if (newValue > 0x00FFFFFF)
                    newValue = 1
                if (generatedId.compareAndSet(result, newValue)) {
                    return result
                }
            }
        } else {
            return View.generateViewId()
        }

    }
}