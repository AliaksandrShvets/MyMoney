package com.goodsoft.mymoney.core

import com.goodsoft.mymoney.database.tables.accounts.AccountEntity
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.enums.AccountIcon
import com.goodsoft.mymoney.enums.CategoryIcon
import com.goodsoft.mymoney.implementations.backdrop.accounts.AccountItem
import com.goodsoft.mymoney.implementations.backdrop.categories.CategoryItem


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