package com.bignerdranch.android.geomain

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
private const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val IS_CHEATER = "is_cheater"
private var cheats: Boolean = false

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var answerShowButton: Button
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        cheats = savedInstanceState?.getBoolean(IS_CHEATER, false) ?: false

        //Доставание extra из intent при создании activity через main activity
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        answerShowButton = findViewById(R.id.show_answer_button)

        answerShowButton.setOnClickListener {
            cheats = true
            val answerText = when{
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

        if(cheats){
            answerShowButton.callOnClick()
            println("Читер!")
        }
        else{
            println("не читер!")
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putBoolean(IS_CHEATER, cheats)
    }

    //Функция передачи кода ответа родительской activity
    private fun setAnswerShownResult(isAnswerShown:Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        //Функия создающая необходимый интент для открытия новой активити
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}