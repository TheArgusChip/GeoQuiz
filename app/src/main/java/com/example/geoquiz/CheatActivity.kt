package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
var EXTRA_CLUES_LEFT = "com.example.geoquiz.clues_left"
class CheatActivity : AppCompatActivity() {
    private lateinit var answerTextView: TextView
    private lateinit var cluesLeft: TextView
    private lateinit var versionTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerIsTrue = false
    private val cheatViewModel: CheatViewModel by
    lazy {
        ViewModelProviders.of(this)[CheatViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        if (cheatViewModel.answerShown == true) setAnswerShownResult(true)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        if (cheatViewModel.answerShown == false){
            cheatViewModel.cheatCluesLeft = intent.getIntExtra(EXTRA_CLUES_LEFT, 0)
        }
        answerTextView = findViewById(R.id.answer_text_view)
        cluesLeft = findViewById(R.id.clues_left)
        cluesLeft.text = "Clues left: " + cheatViewModel.cheatCluesLeft
        versionTextView = findViewById(R.id.version_text_view)
        versionTextView.text = "Version: " + Build.VERSION.SDK_INT
        showAnswerButton = findViewById(R.id.show_answer_button)
        if (cheatViewModel.cheatCluesLeft <= 0) showAnswerButton.visibility = View.GONE
        if (cheatViewModel.answerShown == true) showAnswerButton.visibility = View.GONE
        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
            cheatViewModel.answerShown = true
            cheatViewModel.cheatCluesLeft --
            cluesLeft.text = "Clues left: " + cheatViewModel.cheatCluesLeft
            showAnswerButton.visibility = View.GONE
        }
    }
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatCluesLeft: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CLUES_LEFT, cheatCluesLeft)
            }

        }
    }
}