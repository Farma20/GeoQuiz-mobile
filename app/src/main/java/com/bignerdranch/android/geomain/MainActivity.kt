package com.bignerdranch.android.geomain

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Создание переменных, в которых будуд лежать кнопки
    // lateinit обещает JVM, что мы инициализируем эту переменную
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    //Создаем неизменяемый список с вопросами и ответами
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Достаем виджеты по их id, описанному в xml
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)


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
            currentIndex = (currentIndex + 1) % questionBank.size

            updateQuestion()
        }

        prevButton.setOnClickListener { view: View ->
            currentIndex = currentIndex - 1
            if(currentIndex < 0){
                currentIndex = questionBank.size - 1
            }

            updateQuestion()
        }

        //Задание 2.1 Добавить слушателя на TextView
        questionTextView.setOnClickListener { view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size

            updateQuestion()
        }

       updateQuestion()
    }

    //Достаем idx актуального вопроса и вставляем его в виджет
    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    //Функция проверки вопроса
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if(userAnswer == correctAnswer){
            R.string.correct_toast
        } else{
            R.string.incorrect_toast
        }

        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
    }
}