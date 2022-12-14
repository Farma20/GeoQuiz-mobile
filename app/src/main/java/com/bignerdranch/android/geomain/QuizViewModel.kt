package com.bignerdranch.android.geomain

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {
    //Создаем неизменяемый список с вопросами и ответами
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentIndex = 0
    var cheatsCount = 0
    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId
    val currentQuestionCheats: Boolean get() = questionBank[currentIndex].cheat

    fun cheats(isCheats:Boolean){
        questionBank[currentIndex].cheat = isCheats
        cheatsCount++
    }

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious(){
        currentIndex -= 1
        if(currentIndex < 0){
            currentIndex = questionBank.size - 1
        }
    }
}