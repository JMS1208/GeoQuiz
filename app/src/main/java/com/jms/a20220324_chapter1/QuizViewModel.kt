package com.jms.a20220324_chapter1

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jms.a20220324_chapter1.Model.Question

private val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true),
        Question(R.string.question_australia, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_oceans,true)
    )

    var isCheater = BooleanArray(questionBank.size){false}

    var currentIndex = 0

    var cunningChance = 3
    val maxCunningChance = 3
    var correctBank = BooleanArray(questionBank.size){false}

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val correctAnswer: Boolean
        get() = correctBank[currentIndex]

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        val helpIndex = currentIndex - 1
        currentIndex = if(helpIndex < 0) {
            helpIndex + questionBank.size
        } else {
            helpIndex
        }
    }



    fun setCorrectBank(answer: Boolean) {
        correctBank[currentIndex]=answer
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel 객체 소멸됨")
    }

}