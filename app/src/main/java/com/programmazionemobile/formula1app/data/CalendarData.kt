package com.programmazionemobile.formula1app.data

import androidx.annotation.DrawableRes
import java.util.Date

data class CalendarData(
    val raceName: String,
    val date: Date,
    val descRace: String,
//    @DrawableRes
//    val flag: Int?,
//    @DrawableRes
//    val circuit: Int?
)