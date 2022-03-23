package com.jms.a20220324_chapter1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.jms.a20220324_chapter1.Model.Question
import com.jms.a20220324_chapter1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val questionBank = listOf(
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true),
        Question(R.string.question_australia, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_oceans,true)
    )
    private var currentIndex = 0

    private val nextButtonListener = View.OnClickListener {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private val previousButtonListener = View.OnClickListener {
        val helpIndex = currentIndex - 1
        currentIndex = if(helpIndex < 0) {
            helpIndex + questionBank.size
        } else {
            helpIndex
        }
        updateQuestion()
    }
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {

        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if(correctAnswer==userAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(applicationContext,messageResId,Toast.LENGTH_SHORT).show()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateQuestion()

        binding.questionTextView.setOnClickListener(nextButtonListener)

        binding.nextButton.setOnClickListener(nextButtonListener)

        binding.previousButton.setOnClickListener(previousButtonListener)

        binding.trueButton.setOnClickListener {
            checkAnswer(true)

        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)

        }

    }
}