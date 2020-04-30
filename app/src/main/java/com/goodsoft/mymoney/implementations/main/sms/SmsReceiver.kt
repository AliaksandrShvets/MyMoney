package com.goodsoft.mymoney.implementations.main.sms

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.widget.Toast
import com.goodsoft.mymoney.database.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.database.categories.CategoryEntity
import com.goodsoft.mymoney.database.parsers.SmsParserEntity
import com.goodsoft.mymoney.database.parsers.SmsParsersRoomRepository
import com.goodsoft.mymoney.enums.CategoryIcon
import com.goodsoft.mymoney.enums.SmsParameterEnum
import com.goodsoft.mymoney.implementations.sms.parser.ExpressionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Toast.makeText(context, "sms received", Toast.LENGTH_SHORT).show()
            Telephony.Sms.Intents.getMessagesFromIntent(intent).forEach { parseSms(it) }
        }
    }

    companion object {
        private fun parseSms(currentSMS: SmsMessage) {
            GlobalScope.launch {
                SmsParsersRoomRepository().getAll().collect { smsParsers ->
                    smsParsers.filter {
                        it.address == currentSMS.displayOriginatingAddress
                    }.forEach { smsParserEntity ->
                        Thread.sleep(20000L)
                        addOperation(smsParserEntity, currentSMS.displayMessageBody)
                    }
                    return@collect
                }
            }
        }

        @SuppressLint("CheckResult")
        @Suppress("UNREACHABLE_CODE")
        fun addOperation(smsParserEntity: SmsParserEntity, message: String) : List<String>{
            val result = mutableListOf<String>()
            val messageRows = message.split("; ")
            var isFullData = messageRows.size == smsParserEntity.body.split("; ").size
            if (isFullData) {
                val parts: HashMap<SmsParameterEnum, ExpressionEntity> = Gson().fromJson(
                        smsParserEntity.parts,
                        object : TypeToken<HashMap<SmsParameterEnum, ExpressionEntity>>() {}.type
                )
                parts.forEach { part ->
                    Regex(part.value.regex).find(messageRows[part.value.delimiterIndex])?.destructured?.toList()?.let {
                        if (it.isEmpty()) {
                            isFullData = false
                            return@forEach
                        }
                        result.addAll(it)
                    }
                }
            }
            if (isFullData) {
                CategoriesRoomRepository().addCategory(CategoryEntity(
                        UUID.randomUUID().toString(),
                        "${smsParserEntity.address}\n$result",
                        CategoryIcon.AUTO.name
                )).subscribe({}, {})
            }
            return result
        }
    }
}