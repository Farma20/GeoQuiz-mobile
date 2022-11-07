package com.bignerdranch.android.geomain

import androidx.annotation.StringRes

//data говорит, что класс создан для хранения данных и автоматически добавляет
//методы equals(), hashCode(), toString()
data class Question(@StringRes val textResId: Int, val answer: Boolean, var cheat: Boolean = false)