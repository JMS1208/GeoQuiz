package com.jms.a20220324_chapter1

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.jms.a20220324_chapter1.Model.Question
import com.jms.a20220324_chapter1.databinding.ActivityMainBinding

private val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val quizViewModel : QuizViewModel by lazy {
        Log.d(TAG,"QuizViewModel 객체 가져옴")
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private val nextButtonListener = View.OnClickListener {
        quizViewModel.moveToNext()
        setActivateButton()
        updateQuestion()
    }



    private val previousButtonListener = View.OnClickListener {
        quizViewModel.moveToPrev()
        setActivateButton()
        updateQuestion()
    }

    private fun setActivateButton() {
        if(quizViewModel.correctAnswer) {
            binding.falseButton.isVisible = false
            binding.trueButton.isVisible = false
            binding.correctedComment!!.visibility = View.VISIBLE
        } else {
            binding.falseButton.isVisible = true
            binding.trueButton.isVisible = true
            binding.correctedComment!!.visibility = View.INVISIBLE
        }

    }

    private fun updateQuestion() {

        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.correctAnswer
        val messageResId = if(correctAnswer==userAnswer) {
            quizViewModel.setCorrectBank(answer = true)
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(applicationContext,messageResId,Toast.LENGTH_SHORT).show()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreated() called")
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroyed() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }

}