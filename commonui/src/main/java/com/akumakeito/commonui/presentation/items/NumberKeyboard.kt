package com.akumakeito.commonui.presentation.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//
//@Composable
//fun NumberKeyboard(
//    onNumberClick: (String) -> Unit,
//    onDoneClick: () -> Unit,
//    onBackspaceClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//
//    Row(modifier = modifier.fillMaxWidth()) {
//
//        Column(Modifier.weight(1f)) {
//            Row(modifier =Modifier.aspectRatio(1f)) {
//                NumberButton("1", onNumberClick)
//                NumberButton("2", onNumberClick)
//                NumberButton("3", onNumberClick)
//            }
//
//            Row(modifier =Modifier.aspectRatio(1f)) {
//                NumberButton("4", onNumberClick)
//                NumberButton("5", onNumberClick)
//                NumberButton("6", onNumberClick)
//            }
//
//            Row(modifier =Modifier.aspectRatio(1f)) {
//                NumberButton("7", onNumberClick)
//                NumberButton("8", onNumberClick)
//                NumberButton("9", onNumberClick)
//            }
//
//            Row(modifier =Modifier.aspectRatio(1f)) {
//                NumberButton(",", onNumberClick)
//                NumberButton("0", onNumberClick)
//                NumberButton(".", onNumberClick)
//            }
//        }
//
//        Column(Modifier.weight(1f)) {
//
//            Button(
//                modifier = Modifier.aspectRatio(1f),
//                onClick = { onBackspaceClick },
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_backspace),
//                    contentDescription = null
//                )
//            }
//
//            Button(
//                modifier = Modifier.aspectRatio(1f),
//                onClick = { onDoneClick },
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
//            ) {
//                Icon(
//                    Icons.Default.Done,
//                    contentDescription = null
//                )
//            }
//        }
//    }
//
//}
//
//
//
//@Composable
//fun NumberButton(
//    numberString: String,
//    onNumberClick: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Button(
//        onClick = { onNumberClick(numberString) },
//        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
//        modifier = modifier.aspectRatio(1f)
//    ) {
//        Text(
//            text = numberString,
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.onSurface
//        )
//    }
//}

@Composable
fun KeyboardScreen() {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Введите число", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = input, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Keyboard(onKeyPress = { key ->
            input = when (key) {
                "⌫" -> if (input.isNotEmpty()) input.dropLast(1) else input
                "✓" -> input // Обработка завершения ввода
                else -> input + key
            }
        })
    }
}

@Composable
fun Keyboard(onKeyPress: (String) -> Unit) {
    val keys = listOf(
        listOf("1", "2", "3", "⌫"),
        listOf("4", "5", "6", ""),
        listOf("7", "8", "9", "✓"),
        listOf(",", "0", ".", "")
    )
    Column {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    Key(key, onKeyPress)
                }
            }
        }
    }
}

@Composable
fun Key(key: String, onKeyPress: (String) -> Unit) {
    val backgroundColor = when (key) {
        "⌫" -> Color(0xFFFFC0CB)
        "✓" -> Color(0xFFADD8E6)
        "" -> Color.Transparent
        else -> Color.White
    }

    val textColor = when (key) {
        "⌫", "✓" -> Color.Black
        else -> Color.Black
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .size(60.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(30.dp))
            .clickable { if (key.isNotEmpty()) onKeyPress(key) }
    ) {
        Text(text = key, fontSize = 24.sp, color = textColor, fontWeight = FontWeight.Bold)
    }

}
