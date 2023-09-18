package com.example.geoquiz

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var cheatButton: Button
    private lateinit var restartButton: Button
    private lateinit var questionTextView: TextView
    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        if(userAnswer==correctAnswer) quizViewModel.correctAnswers ++
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun disableButtons(){
        trueButton.visibility = View.GONE
        falseButton.visibility = View.GONE
    }
    private fun enableButtons(){
        if(!quizViewModel.questionBank[quizViewModel.currentIndex].answered) {
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
        }
        else disableButtons()

    }
    private fun getResult(){
        if(quizViewModel.answeredQuestions==quizViewModel.questionBank.size){
            nextButton.visibility = View.GONE
            prevButton.visibility = View.GONE
            questionTextView.text = "Вы дали ${quizViewModel.correctAnswers} / ${quizViewModel.questionBank.size} верных ответов"
            restartButton.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        restartButton = findViewById(R.id.restart_button)
        restartButton.visibility = View.GONE
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        enableButtons()
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            quizViewModel.questionBank[quizViewModel.currentIndex].answered = true
            disableButtons()
            quizViewModel.answeredQuestions ++
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            quizViewModel.questionBank[quizViewModel.currentIndex].answered = true
            disableButtons()
            quizViewModel.answeredQuestions ++
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            enableButtons()
            getResult()
        }

        cheatButton.setOnClickListener {

        }


        prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
            enableButtons()
            getResult()
        }

        restartButton.setOnClickListener{
            finish()
            startActivity(intent)
        }

       updateQuestion()
    }
}
