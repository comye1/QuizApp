package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.quizapp.Dataset.QuizList
import com.example.quizapp.quizscreen.*
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    QuizScreen(quizList = QuizList)
                }
            }
        }
    }
}



object Dataset {
    val QuizList = mutableListOf(
        Quiz(
            question = "세계에서 가장 높은 산은?",
            answers = listOf("에베레스트산", "백두산", "한라산", "킬리만자로산"),
            answerIndex = 0
        ),
        Quiz(
            question = "임진왜란이 일어난 연도는?",
            answers = listOf("1472년", "1548년", "1592년", "1623년"),
            answerIndex = 2
        ),
        Quiz(
            question = "다음 중 섬나라는?",
            answers = listOf("스페인", "이탈리아", "독일", "영국"),
            answerIndex = 3
        ),
        Quiz(
            question = "다음 중 가장 무거운 행성은?",
            answers = listOf("수성", "금성", "지구", "화성"),
            answerIndex = 2
        )
    )

    fun addQuiz(quiz: Quiz) {
        QuizList.add(quiz)
    }

}