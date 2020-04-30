package com.goodsoft.mymoney.enums

import androidx.annotation.DrawableRes
import com.goodsoft.mymoney.R


enum class CategoryIcon(@DrawableRes val iconRes: Int) {
    BAR(R.drawable.ic_category_bar),
    TAXI(R.drawable.ic_category_taxi),
    TRANSPORT(R.drawable.ic_category_transport),
    RESTAURANT(R.drawable.ic_category_restaurant),
    SHOP(R.drawable.ic_category_shop),
    AIRBUS(R.drawable.ic_category_airbus),
    AUTO(R.drawable.ic_category_auto),
    SPORT(R.drawable.ic_category_sport),
    CHEQUE(R.drawable.ic_category_checkue),
    MEDICINE(R.drawable.ic_category_medicine),
    FAST_FOOD(R.drawable.ic_category_fastfood),
    FUEL(R.drawable.ic_category_fuel),
    PRESENTS(R.drawable.ic_category_presents),
    PETS(R.drawable.ic_category_pets),
    KIDS(R.drawable.ic_category_kids),
    KITCHEN(R.drawable.ic_category_kitchen),
    WORK(R.drawable.ic_category_work),
    POPULAR(R.drawable.ic_category_popular),
    CIGARETTES(R.drawable.ic_category_cigarettes),
    CASINO(R.drawable.ic_category_casino),
    TRAVEL(R.drawable.ic_category_travel),
    FLOWER(R.drawable.ic_category_flower),
    HOBBY(R.drawable.ic_category_hobby);
}