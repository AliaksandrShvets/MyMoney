package com.goodsoft.mymoney.implementations.main.sms

import com.goodsoft.mymoney.database.parsers.SmsParserEntity
import com.goodsoft.mymoney.implementations.sms.parser.SmsEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SmsReceiverTest {

    @Test
    fun parserTest() {
        val smsEntity =  SmsParserEntity("id", "websms", "12123", "{\"AMOUNT\":{\"delimiterIndex\":0,\"end\":2,\"regex\":\"1([\\\\S ]+)123\",\"start\":1}}")
        SmsReceiver.addOperation(smsEntity, "Body")
        assertEquals(2, 2+2)
    }
}