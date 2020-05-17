package com.goodsoft.mymoney.core

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.goodsoft.mymoney.database.tables.parsers.SmsParserEntity
import com.goodsoft.mymoney.enums.SmsParameterEnum
import com.goodsoft.mymoney.implementations.sms.parser.ExpressionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


@BindingAdapter("isSelected")
fun setSelected(view: View, isSelected: Boolean) {
    view.isSelected = isSelected
}

@BindingAdapter("isVisible")
fun setVisibility(view: View, option: Boolean) {
    view.visibility = if (option) View.VISIBLE else View.GONE
}

@BindingAdapter("sms_parser")
fun setSmsParser(textView: TextView, smsParser: SmsParserEntity) {
    val listType = object : TypeToken<HashMap<SmsParameterEnum, ExpressionEntity>>() {}.type
    val parts: HashMap<SmsParameterEnum, ExpressionEntity> =
            Gson().fromJson(smsParser.parts, listType)
    textView.text = SpannableString(smsParser.body).apply {
        parts.forEach {
            setSpan(
                    BackgroundColorSpan(ContextCompat.getColor(textView.context, it.key.spanColor)),
                    it.value.start, it.value.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}

/*@BindingAdapter("errorText")
fun setErrorText(textInputLayout: TextInputLayout, errorText: String) {
    if (errorText.isNotEmpty()) {
        textInputLayout.error = errorText
    } else {
        textInputLayout.isErrorEnabled = false
    }
}*/
