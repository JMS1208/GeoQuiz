package com.jms.a20220324_chapter1

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.jms.a20220324_chapter1.Model.Question
import com.jms.a20220324_chapter1.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val KEY_CORRECTBANK = "correctBank"
private const val KEY_CUNNING = "cunning"
const val KEY_CUNNING_CHANCE = "cunning_chance"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val quizViewModel : QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private val nextButtonListener = View.OnClickListener {
        quizViewModel.moveToNext()
        setButtonActivated()
        updateQuestion()
    }



    private val previousButtonListener = View.OnClickListener {
        quizViewModel.moveToPrev()
        setButtonActivated()
        updateQuestion()
    }



    private fun setButtonActivated() {
        if(quizViewModel.correctAnswer) {
            binding.falseButton.isVisible = false
            binding.trueButton.isVisible = false
            binding.correctedComment.visibility = View.VISIBLE
        } else {
            binding.falseButton.isVisible = true
            binding.trueButton.isVisible = true
            binding.correctedComment.visibility = View.INVISIBLE
        }

    }

    private fun updateQuestion() {

        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.correctAnswer
        val messageResId = when {
            quizViewModel.isCheater[quizViewModel.currentIndex] -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }


        Toast.makeText(applicationContext,messageResId,Toast.LENGTH_SHORT).show()


    }

    private fun initSavedInstanceStateData(savedInstanceState: Bundle?) {
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        quizViewModel.currentIndex=currentIndex

        val correctBank = savedInstanceState?.getBooleanArray(KEY_CORRECTBANK)
        if(correctBank != null) {
            quizViewModel.correctBank=correctBank
        }

        val cunningArr = savedInstanceState?.getBooleanArray(KEY_CUNNING)
        if(cunningArr != null) {
            quizViewModel.isCheater = cunningArr
        }

        val cunningChance = savedInstanceState?.getInt(KEY_CUNNING_CHANCE)
        if(cunningChance != null) {
            quizViewModel.cunningChance = cunningChance
        }




    }

    private fun setCunningChanceNumber() {
        val cunningChanceNumber =  "(${quizViewModel.cunningChance}/${quizViewModel.maxCunningChance})"
        binding.cunningChanceNumber.text = cunningChanceNumber
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG,"onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        outState.putBooleanArray(KEY_CORRECTBANK,quizViewModel.correctBank)
        outState.putBooleanArray(KEY_CUNNING,quizViewModel.isCheater)
        outState.putInt(KEY_CUNNING_CHANCE,quizViewModel.cunningChance)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) {
            return
        }

        if(requestCode == REQUEST_CODE_CHEAT) {

            quizViewModel.cunningChance-=1

            quizViewModel.isCheater[quizViewModel.currentIndex] =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false

            setCunningChanceNumber()

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreated() called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSavedInstanceStateData(savedInstanceState)

        setCunningChanceNumber()

        setButtonActivated()
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

        binding.cheatButton.setOnClickListener {
            val cunningChance = quizViewModel.cunningChance
            if(cunningChance > 0) {
                val answerIsTrue = quizViewModel.currentQuestionAnswer
                val intent = CheatActivity.newIntent(this, answerIsTrue)
                val options =
                    ActivityOptionsCompat.makeClipRevealAnimation(it, 0, 0, it.width, it.height)

                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                Toast.makeText(applicationContext, R.string.cannot_cunning_toast, Toast.LENGTH_SHORT).show()
            }
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