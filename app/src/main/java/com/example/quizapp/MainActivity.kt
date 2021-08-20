package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.Dataset.QuizList
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    var index by remember {
                        mutableStateOf(0)
                    }
                    var answerCount by remember {
                        mutableStateOf(0)
                    }
                    val quizList = QuizList
                    if (index < quizList.size) {
                        QuizSection(
                            quiz = quizList[index],
                            onAnswerSelected = {
                                index++
                                answerCount++
                            }
                        )
                    } else {
                        Column() {
                            Text("퀴즈 끝")
                            Text("맞힌 문제 : ${answerCount}개")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizSection(
    quiz: Quiz,
    onAnswerSelected: () -> Unit
) {
    Column {
        QuestionSection(question = quiz.question, modifier = Modifier.weight(1f))
        AnswerSection(
            answers = quiz.answers,
            answerIndex = quiz.answerIndex,
            onAnswerSelected = onAnswerSelected,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AnswerSection(
    answers: List<String>,
    answerIndex: Int,
    onAnswerSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(10.dp)) {
        for (answer in answers) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { if (answer == answers[answerIndex]) onAnswerSelected() },
            ) {
                Text(text = answer, style = MaterialTheme.typography.body1)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun QuestionSection(question: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuizAppTheme {
        val quiz1 = Quiz(
            question = "세계에서 가장 높은 산은?",
            answers = listOf("에베레스트산", "백두산", "한라산", "킬리만자로산"),
            answerIndex = 0
        )
        QuizSection(quiz = quiz1, onAnswerSelected = {})
    }
}

class Quiz(
    val question: String,
    val answers: List<String>,
    val answerIndex: Int
)

object Dataset {
    val QuizList = listOf(
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
}