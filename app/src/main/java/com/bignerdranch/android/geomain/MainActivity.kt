package com.bignerdranch.android.geomain

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0;

class MainActivity : AppCompatActivity() {

    // Создание переменных, в которых будут лежать кнопки
    // lateinit обещает JVM, что мы инициализируем эту переменную
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton:Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private var selectQuestion: Boolean = false
    private var right_answers: Float = 0.0f

    //подключаем ViewModel к проекту для хранения информации
    // при уничтожении activity
    //Ленивая инициализация quizViewModel
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Записываем в логи создание MainActivity
        Log.d(TAG, "onCreate(Bundle?) called")

        setContentView(R.layout.activity_main)

        // Достаем виджеты по их id, описанному в xml
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        //Добавление слушателей на кнопки
        trueButton.setOnClickListener{view: View ->
            //Создаем всплывающее окно с текстом (Toast) при нажатии
            checkAnswer(true)
        }

        falseButton.setOnClickListener{ view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener { view: View ->
            //Изменяеи idx при нажатии на next
            quizViewModel.moveToNext()
            selectQuestion = false
            updateQuestion()
        }

        prevButton.setOnClickListener { view:View ->
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        cheatButton.setOnClickListener { view:View ->
            //Создаем новую activity через эту
            //создаем интент, где мы говорим ОС какую activity открывать
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(
                this@MainActivity,
                answerIsTrue,
                quizViewModel.cheatsCount,
                quizViewModel.currentQuestionCheats)

            //решения проблемы совместимости вызова функции для более высокого уровня api
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                //создание анимации новой активити в виде опции запуска
                val options = ActivityOptions.makeClipRevealAnimation(
                    view, 0, 0, view.width, view.height)
                //Запускаем новую activity
                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            }
            else{
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }

        }

       updateQuestion()
    }

    //Реализация функии сохраненя данных при разрушении activity
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    //Переопределение функции, которая срабатывает после того,
    // как дочерняя активити перестает существовать
    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != RESULT_OK)
            return

        if(requestCode == REQUEST_CODE_CHEAT){
            val isCheats = data?.getBooleanExtra(
                "com.bignerdranch.android.geoquiz.answer_shown",
                false) ?: false
            quizViewModel.cheats(isCheats)
        }
    }

    //Переопределение функций обратного вызова жизненного цикла Activity
    //Запускает видимость для пользователя
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    //Запускает возобновление
    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    //Приостанавливает приложение
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    //Останавливает приложение
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    //Уничтожает приложение
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    //Достаем idx актуального вопроса и вставляем его в виджет
    private fun updateQuestion(){
//        Log.d(TAG, "exception", Exception())

        questionTextView.setText(quizViewModel.currentQuestionText)
    }

    //Функция проверки вопроса
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId:Int
        selectQuestion = !selectQuestion

        messageResId = when{
            quizViewModel.currentQuestionCheats -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
    }
}