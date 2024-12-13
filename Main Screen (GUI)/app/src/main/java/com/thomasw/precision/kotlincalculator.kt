package cdn.kotlincalculator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext


@Composable
fun CalculatorApp() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val context = LocalContext.current
    var history by remember { mutableStateOf(listOf<String>()) } // State to store history

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Expression and Result
            Column {
                Text(
                    text = expression,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = result,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // History Display
            Text("History:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Column {
                history.take(10).forEach { historyItem ->
                    Text(text = historyItem, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons Grid
            CalculatorButtons(
                onButtonClick = { value ->
                    if (value == "C") {
                        expression = ""
                        result = ""
                    } else if (value == "=") {
                        try {
                            val evalResult = evaluateExpression(expression)
                            val resultText = evalResult?.toString() ?: "Error"
                            result = resultText

                            // Update history with the new result
                            history = listOf("$expression = $resultText") + history
                        } catch (e: Exception) {
                            Toast.makeText(context, "Invalid Expression", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        expression += value
                    }
                }
            )
        }
    }
}


@Composable
fun CalculatorButtons(onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf(".", "0", "=", "+"),
        listOf("C", "^", "%", "√")
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    Button(
                        onClick = { onButtonClick(label) },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        Text(text = label, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

fun evaluateExpression(expression: String): Double? {
    return try {
        // Replace any unsupported characters or spaces for safety
        val sanitizedExpression = expression.replace(Regex("[^\\d.+\\-*/^%()√]"), "")
        val tokens = sanitizedExpression.split(" ").joinToString("")

        val result = object {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < tokens.length) tokens[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < tokens.length) throw RuntimeException("Unexpected: ${ch.toChar()}")
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.code)) x += parseTerm() // addition
                    else if (eat('-'.code)) x -= parseTerm() // subtraction
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.code)) x *= parseFactor() // multiplication
                    else if (eat('/'.code)) x /= parseFactor() // division
                    else if (eat('%'.code)) x %= parseFactor() // modulo
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor() // unary plus
                if (eat('-'.code)) return -parseFactor() // unary minus
                if (eat('√'.code)) return Math.sqrt(parseFactor()) // square root

                var x: Double
                val startPos = pos
                if (eat('('.code)) { // parentheses
                    x = parseExpression()
                    eat(')'.code)
                } else if ((ch >= '0'.code && ch <= '9'.code) || ch == '.'.code) { // numbers
                    while ((ch >= '0'.code && ch <= '9'.code) || ch == '.'.code) nextChar()
                    x = tokens.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: ${ch.toChar()}")
                }

                // Check for exponentiation (^)
                if (eat('^'.code)) {
                    val power = parseFactor()
                    x = Math.pow(x, power)
                }

                return x
            }
        }.parse()

        result
    } catch (e: Exception) {
        Log.e("Calculator", "Evaluation error: ${e.message}")
        null
    }
}
