package com.jms.a20220324_chapter1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.jms.a20220324_chapter1.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE ="com.jms.a20220324_chapter1.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.jms.a20220324_chapter1.geoquiz.answer_shown"
const val EXTRA_CUNNING_CHANCE = "com.jms.a20220324_chapter1.geoquiz.cunning_chance"

class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false

    lateinit var binding: ActivityCheatBinding

    private val quizViewModel : QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CUNNING_CHANCE,quizViewModel.cunningChance)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "힌트 보기"
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        setCunningChance(savedInstanceState)
        binding.showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)

            setAnswerShownResult(true)
        }

        binding.returnToMainActivityButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

    }


    private fun setCunningChance(savedInstanceState: Bundle?) {
        val cunningChance = savedInstanceState?.getInt(KEY_CUNNING_CHANCE)
        if(cunningChance != null) {

            quizViewModel.cunningChance = cunningChance
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean = false) {


        val intent = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown)
        }
        setResult(Activity.RESULT_OK, intent)
    }
}