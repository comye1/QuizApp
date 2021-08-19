package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val quiz1 = Quiz(
                        question = "세계에서 가장 높은 산은?",
                        answers = listOf("에베레스트산", "백두산", "한라산", "킬리만자로산"),
                        answerIndex = 0
                    )
                    QuizSection(quiz = quiz1, onAnswerSelected = { })
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
        QuizSection(quiz = quiz1, onAnswerSelected = { })
    }
}

class Quiz(
    val question: String,
    val answers: List<String>,
    val answerIndex: Int
)

