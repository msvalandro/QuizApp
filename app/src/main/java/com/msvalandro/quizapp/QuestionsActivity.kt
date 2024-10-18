package com.msvalandro.quizapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuestionsActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    private var textViewProgress: TextView? = null
    private var textViewQuestion: TextView? = null
    private var imageViewFlag: ImageView? = null

    private var textViewOptionOne: TextView? = null
    private var textViewOptionTwo: TextView? = null
    private var textViewOptionThree: TextView? = null
    private var textViewOptionFour: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progress_bar)
        textViewProgress = findViewById(R.id.text_view_progress)
        textViewQuestion = findViewById(R.id.text_view_question)
        imageViewFlag = findViewById(R.id.image_view_flag)

        textViewOptionOne = findViewById(R.id.text_view_option_one)
        textViewOptionTwo = findViewById(R.id.text_view_option_two)
        textViewOptionThree = findViewById(R.id.text_view_option_three)
        textViewOptionFour = findViewById(R.id.text_view_option_four)


        val questionsList = Constants.getQuestions()

        var currentPosition = 1
        val question = questionsList[currentPosition - 1]

        progressBar?.progress = currentPosition
        textViewProgress?.text = "$currentPosition / ${progressBar?.max}"
        textViewQuestion?.text = question.question
        imageViewFlag?.setImageResource(question.image)

        textViewOptionOne?.text = question.optionOne
        textViewOptionTwo?.text = question.optionTwo
        textViewOptionThree?.text = question.optionThree
        textViewOptionFour?.text = question.optionFour
    }
}