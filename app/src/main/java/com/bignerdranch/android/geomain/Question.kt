package com.bignerdranch.android.geomain

import android.support.annotation.StringRes

//data говорит, что класс создан для хранения данных и автоматически добавляет
//методы equals(), hashCode(), toString()
data class Question(@StringRes val textResId: Int, val answer: Boolean)