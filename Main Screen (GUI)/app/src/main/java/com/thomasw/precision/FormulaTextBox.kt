package com.thomasw.precision

import android.app.Activity
import ru.noties.jlatexmath.JLatexMathView
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.FrameLayout
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*

import androidx.compose.ui.viewinterop.AndroidView


import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert


import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.material3.Typography.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon


import androidx.compose.material.icons.filled.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp


@Composable
fun RenderLatexFormula(formula: String) {
    // Get the current context
    val context = LocalContext.current

    // Make sure the LaTeX formula is set dynamically
    LaunchedEffect(formula) {
        val latexMathView = (context as Activity).findViewById<JLatexMathView>(R.id.j_latex_math_view)
        latexMathView?.setLatex(formula)
    }

    // Layout for the LaTeX formula view
    Box(
        modifier = Modifier
            .padding(16.dp) // Apply padding to Box
            .fillMaxSize() // Make sure the Box takes up available space
    ) {
        // Add a placeholder or a blank space where the LaTeX formula will be rendered
        Text(
            text = formula, // Display the LaTeX formula as text as a fallback or description
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Center) // Position the text at the center
        )
    }
}