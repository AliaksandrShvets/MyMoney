package com.goodsoft.mymoney.core

import com.goodsoft.mymoney.database.accounts.AccountEntity
import com.goodsoft.mymoney.database.categories.CategoryEntity
import com.goodsoft.mymoney.enums.AccountIcon
import com.goodsoft.mymoney.enums.CategoryIcon
import com.goodsoft.mymoney.implementations.main.accounts.AccountItem
import com.goodsoft.mymoney.implementations.main.categories.CategoryItem
import kotlin.random.Random


fun CategoryEntity.toCategoryItem() = CategoryItem(
        this,
        CategoryIcon.valueOf(icon).iconRes
)

fun AccountEntity.toAccountItem() = AccountItem(
        id,
        name,
        currencyCode,
        AccountIcon.valueOf(icon).iconRes
)

fun main() {
    val list: MutableList<Int> = mutableListOf<Int>()
    for (i in 0..10) {
        list.add(Random.nextInt(100, 1000))
    }
    println("source: $list")
    list.sort()
    println("sorted: $list")
    var startPosition = 0
    var position = 2
    var result = 0
    while (position <= list.size) {

        if (list[position] - list[startPosition] < 200) {
            startPosition = position
            position += 3
        } else if (list[position - 1] - list[startPosition] < 100) {
            startPosition = position - 1
            position += 2
        } else {
            startPosition = position - 2
            position += 1
        }
        result++
    }
    println("result: $result")
}