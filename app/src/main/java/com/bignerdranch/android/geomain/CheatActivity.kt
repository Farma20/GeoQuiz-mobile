package com.bignerdranch.android.geomain

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
private const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val IS_CHEATER = "is_cheater"
private const val CHEATS_COUNT = "com.bignerdranch.android.geoquiz.cheats_count"
private const val ANSWER_IS_CHEAT = "com.bignerdranch.android.geoquiz.answer_is_cheat"


class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var apiLvlView: TextView
    private lateinit var cheatsCountView: TextView
    private lateinit var answerShowButton: Button
    private var answerIsTrue = false


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

//        cheats = savedInstanceState?.getBoolean(IS_CHEATER, false) ?: false

        //Доставание extra из intent при создании activity через main activity
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        val cheatsCount = intent.getIntExtra(CHEATS_COUNT, 0)
        var cheats: Boolean = intent.getBooleanExtra(ANSWER_IS_CHEAT, false)

        answerTextView = findViewById(R.id.answer_text_view)
        apiLvlView = findViewById(R.id.api_lvl_view)
        answerShowButton = findViewById(R.id.show_answer_button)
        cheatsCountView = findViewById(R.id.cheats_count)

        val countCheatsText: CharSequence = "Hints left: "+(3-cheatsCount)
        cheatsCountView.append(countCheatsText)

        //добавление актуальной версии api
        val apiLvl: CharSequence = "API Level " + Build.VERSION.SDK_INT
        apiLvlView.append(apiLvl)

        answerShowButton.setOnClickListener {
           if(cheatsCount < 3 && !cheats){
               cheats = true
               val answerText = when{
                   answerIsTrue -> R.string.true_button
                   else -> R.string.false_button
               }
               answerTextView.setText(answerText)
               setAnswerShownResult(true)
           }

           if(cheats){
               val answerText = when{
                   answerIsTrue -> R.string.true_button
                   else -> R.string.false_button
               }
               answerTextView.setText(answerText)
           }

           else{
               answerTextView.setText(R.string.all_cheats_using)
           }
        }

        if(cheats){
            answerShowButton.callOnClick()
        }


    }

//    override fun onSaveInstanceState(savedInstanceState: Bundle) {
//        super.onSaveInstanceState(savedInstanceState)
//
//        savedInstanceState.putBoolean(IS_CHEATER, cheats)
//    }

    //Функция передачи кода ответа родительской activity
    private fun setAnswerShownResult(isAnswerShown:Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        //Функия создающая необходимый интент для открытия новой активити
        fun newIntent(packageContext: Context, answerIsTrue: Boolean,
                      cheatsCount: Int, answer_cheat:Boolean): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }.apply {
                putExtra(CHEATS_COUNT, cheatsCount)
            }.apply {
                putExtra(ANSWER_IS_CHEAT, answer_cheat)
            }
        }
    }
}