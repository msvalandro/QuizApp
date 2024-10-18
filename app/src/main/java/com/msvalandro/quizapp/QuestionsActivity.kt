package com.msvalandro.quizapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuestionsActivity : AppCompatActivity(), OnClickListener {
    private var stateCurrentPosition: Int = 1
    private var stateQuestionsList: ArrayList<Question>? = null
    private var stateSelectedOptionPosition: Int = 0

    private var progressBar: ProgressBar? = null
    private var textViewProgress: TextView? = null
    private var textViewQuestion: TextView? = null
    private var imageViewFlag: ImageView? = null

    private var textViewOptionOne: TextView? = null
    private var textViewOptionTwo: TextView? = null
    private var textViewOptionThree: TextView? = null
    private var textViewOptionFour: TextView? = null

    private var buttonSubmit: Button? = null

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

        buttonSubmit = findViewById(R.id.button_submit)

        textViewOptionOne?.setOnClickListener(this)
        textViewOptionTwo?.setOnClickListener(this)
        textViewOptionThree?.setOnClickListener(this)
        textViewOptionFour?.setOnClickListener(this)
        buttonSubmit?.setOnClickListener(this)

        stateQuestionsList = Constants.getQuestions()

        setQuestion()
    }

    private fun setQuestion() {
        if (stateQuestionsList.isNullOrEmpty()) {
            return
        }

        defaultOptionsView()

        val question = stateQuestionsList!![stateCurrentPosition - 1]

        progressBar?.progress = stateCurrentPosition
        textViewProgress?.text = "$stateCurrentPosition / ${progressBar?.max}"
        textViewQuestion?.text = question.question
        imageViewFlag?.setImageResource(question.image)

        textViewOptionOne?.text = question.optionOne
        textViewOptionTwo?.text = question.optionTwo
        textViewOptionThree?.text = question.optionThree
        textViewOptionFour?.text = question.optionFour

        if (stateCurrentPosition == stateQuestionsList!!.size) {
            buttonSubmit?.text = "FINISH"
        } else {
            buttonSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        textViewOptionOne?.let {
            options.add(0, it)
        }
        textViewOptionTwo?.let {
            options.add(1, it)
        }
        textViewOptionThree?.let {
            options.add(2, it)
        }
        textViewOptionFour?.let {
            options.add(3, it)
        }

        options.forEach {
            it.setTextColor(Color.parseColor("#7A8089"))
            it.typeface = Typeface.DEFAULT
            it.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(textView: TextView, selectedOptionNumber: Int) {
        defaultOptionsView()

        stateSelectedOptionPosition = selectedOptionNumber

        textView.setTextColor(Color.parseColor("#363A43"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.text_view_option_one -> {
                textViewOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.text_view_option_two -> {
                textViewOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.text_view_option_three -> {
                textViewOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.text_view_option_four -> {
                textViewOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.button_submit -> {
                if (stateSelectedOptionPosition == 0) {
                    stateCurrentPosition++

                    when {
                        stateCurrentPosition <= stateQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            Toast.makeText(this, "You made it to the end", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val question = stateQuestionsList?.get(stateCurrentPosition - 1)

                    if (question!!.correctAnswer != stateSelectedOptionPosition) {
                        answerView(stateSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (stateCurrentPosition == stateQuestionsList!!.size) {
                        buttonSubmit?.text = "FINISH"
                    } else {
                        buttonSubmit?.text = "NEXT"
                    }

                    stateSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                textViewOptionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                textViewOptionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                textViewOptionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                textViewOptionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}