package com.example.quizapp.quizscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.Dataset
import com.example.quizapp.ui.theme.QuizAppTheme

@Composable
fun QuizScreen(quizList: List<Quiz>) {
    var index by remember {
        mutableStateOf(0)
    }
    var answerCount by remember {
        mutableStateOf(0)
    }
    val quizList = Dataset.QuizList
    if (index < quizList.size) {
        QuizSection(
            quiz = quizList[index],
            onCorrectAnswer = {
                index++
                answerCount++
            },
            onIncorrectAnswer = {
                index++
            }
        )
    } else {
        QuizEndScreen(
            correctCount = answerCount,
            totalCount = quizList.size
        )
    }
}
@Composable
fun QuizSection(
    quiz: Quiz,
    onCorrectAnswer: () -> Unit,
    onIncorrectAnswer: () -> Unit
) {
    Column {
        QuestionSection(question = quiz.question, modifier = Modifier.weight(1f))
        AnswerSection(
            answers = quiz.answers,
            answerIndex = quiz.answerIndex,
            onCorrectAnswer = onCorrectAnswer,
            onInCorrectAnswer = onIncorrectAnswer,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AnswerSection(
    answers: List<String>,
    answerIndex: Int,
    onCorrectAnswer: () -> Unit,
    onInCorrectAnswer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(10.dp)) {
        for (answer in answers) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = {
                    if (answer == answers[answerIndex]) onCorrectAnswer()
                    else onInCorrectAnswer()
                },
            ) {
                Text(text = answer, style = MaterialTheme.typography.body1)
            }
            Spacer(modifier = androidx.compose.ui.Modifier.height(10.dp))
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = androidx.compose.ui.Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun QuizEndScreen(
    correctCount: Int,
    totalCount: Int
) {
    Column(modifier = androidx.compose.ui.Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "퀴즈 끝",
                style = MaterialTheme.typography.h3
            )
        }
        Card(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            elevation = 4.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "맞힌 문제 : ${correctCount}개",
                    style = MaterialTheme.typography.h5
                )
            }
        }
        Card(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            elevation = 4.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "틀린 문제 : ${totalCount - correctCount}개",
                    style = MaterialTheme.typography.h5
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressBar(
                percentage = correctCount / totalCount.toFloat(),
                text = "${correctCount}개 / ${totalCount}개"
            )

        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    text: String,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 100.dp,
    color: Color = MaterialTheme.colors.primary,
    strokeWith: Dp = 20.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    /*
    Fire-and-forget animation function for Float.
    When the provided targetValue is changed, the animation will run automatically.
    If there is already an animation in-flight when targetValue changes,
    the on-going animation will adjust course to animate towards the new target value.
    animateFloatAsState returns a State object.
    The value of the state object will continuously be updated by the animation until the animation finishes.
    Note, animateFloatAsState cannot be canceled/stopped without removing this composable function from the tree.
     */
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            rotate(-90f){
                drawArc(
                    color = Color.LightGray,
                    0f,
                    360f,
                    useCenter = false,
                    style = Stroke(strokeWith.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    brush = Brush.sweepGradient(colors = listOf(color.copy(0.3f), color)),
                    0f,
                    360 * curPercentage.value,
                    useCenter = false,
                    style = Stroke(strokeWith.toPx(), cap = StrokeCap.Butt)
                )

            }
        }
        Text(
            text = text,
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizSectionPreview() {
    QuizAppTheme {
        val quiz1 = Quiz(
            question = "세계에서 가장 높은 산은?",
            answers = listOf("에베레스트산", "백두산", "한라산", "킬리만자로산"),
            answerIndex = 0
        )
        QuizSection(quiz = quiz1, onCorrectAnswer = {}, onIncorrectAnswer = {})
    }
}

class Quiz(
    val question: String,
    val answers: List<String>,
    val answerIndex: Int
)