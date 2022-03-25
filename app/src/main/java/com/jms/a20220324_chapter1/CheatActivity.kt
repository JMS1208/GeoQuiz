package com.jms.a20220324_chapter1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jms.a20220324_chapter1.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE ="com.jms.a20220324_chapter1.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.jms.a20220324_chapter1.geoquiz.answer_shown"


class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false


    lateinit var binding: ActivityCheatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean = false) {
        val intent = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown)
        }

        setResult(Activity.RESULT_OK, intent)
    }
}