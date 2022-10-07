package com.bignerdranch.android.geomain

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Создание переменных, в которых будуд лежать кнопки
    // lateinit обещает JVM, что мы инициализируем эту переменную
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Достаем кнопки по их id, описанному в xml
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        //Добавление слушателей на кнопки
        trueButton.setOnClickListener{view: View ->

            //Создаем всплывающее окно с текстом (Toast) при нажатии

            var toast: Toast = Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            )
            toast.show()

        }

        falseButton.setOnClickListener{view: View ->
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}