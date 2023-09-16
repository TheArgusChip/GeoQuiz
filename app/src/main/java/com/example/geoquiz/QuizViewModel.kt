package com.example.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    val questionBank = arrayListOf(
        Question(R.string.question_australia,true, false),
        Question(R.string.question_oceans,true, false),
        Question(R.string.question_mideast,false, false),
        Question(R.string.question_africa,false, false),
        Question(R.string.question_americas,true, false),
        Question(R.string.question_asia,true, false))

    var currentIndex = 0
    var correctAnswers = 0
    var answeredQuestions = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev() {
        if(currentIndex != 0){
            currentIndex = (currentIndex - 1) % questionBank.size
        }
        else {
            currentIndex = (questionBank.size-1)
        }
    }
}