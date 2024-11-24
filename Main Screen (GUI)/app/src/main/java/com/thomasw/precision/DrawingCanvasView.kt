package com.thomasw.precision


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
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.material3.Typography.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon


import androidx.compose.material.icons.filled.*

import androidx.compose.ui.Alignment


//pens and inks
import  android.app.Dialog
import android.util.Log
import android.widget.ImageButton
import androidx.annotation.Nullable
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Style
import androidx.compose.ui.unit.sp

import ru.noties.jlatexmath.JLatexMathView

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable

import android.graphics.Color.BLACK as BlackColor
import androidx.compose.ui.text.AnnotatedString

@Composable
fun NotesPageWithDrawing() {
    println("Navigating to NotesPageWithDrawing DRAWING CANVAS")

    var expandedSettings by remember { mutableStateOf(false) }
    var expandedFormula by remember { mutableStateOf(false) }
    var expandedArea by remember { mutableStateOf(false) }
    var expandedVolume by remember { mutableStateOf(false) }
    var expandedGeometry by remember { mutableStateOf(false) }
    var expandedTrigonometry by remember { mutableStateOf(false) }
    var expandedDerivatives by remember { mutableStateOf(false) }
    var expandedIntegrals by remember { mutableStateOf(false) }
    var expandedStatistics by remember { mutableStateOf(false) }
    var expandedLogarithms by remember { mutableStateOf(false) }
    var expandedStandardEquations by remember { mutableStateOf(false) }
    //var subject by remember { mutableStateOf<Geometry?>(null) }
    var formula by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                DrawingCanvasView(context).apply {
                    setBackgroundColor(Color.WHITE)
                    this.isFocusable = true
                    this.isFocusableInTouchMode = true// Optional: Explicitly set the background
                }

            },
            modifier = Modifier.fillMaxSize()

        )

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Plus Button (Top Left) with Create Dropdown
            androidx.compose.material3.IconButton(onClick = { expandedFormula = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Plus Button"
                )
            }

            androidx.compose.material3.DropdownMenu(
                expanded = expandedFormula,
                onDismissRequest = { expandedFormula = false }
            ) {
                // "Create New:" Header
                Text(
                    text = "Create New:",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 16.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                )




                DropdownMenuItem(
                    text = { Text("Notebook") },
                    onClick = {   },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Book,
                            contentDescription = "Notebook Icon"
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Flashcard") },
                    onClick = { /* Handle Create Flashcard */ },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Style,
                            contentDescription = "Flashcard Icon"
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Folder") },
                    onClick = { /* Handle Create Folder */ },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Folder,
                            contentDescription = "Folder Icon"
                        )
                    }
                )
            }

            // App Name in the Center (keeps it centered)
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Precision",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            //Right-side buttons
            Row {
                androidx.compose.material3.IconButton(onClick = { /* Add Share functionality */ }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Button"
                    )
                }

                androidx.compose.material3.IconButton(onClick = { expandedSettings = true }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings Button"
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedSettings,
                    onDismissRequest = { expandedSettings = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Pens") },
                        onClick = {
                            expandedSettings = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Notebook Preferences") },
                        onClick = {
                            expandedSettings = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Formulas") },
                        onClick = {
                            expandedFormula = true
                        }
                    )
                }


                androidx.compose.material3.DropdownMenu(
                    expanded = expandedFormula,
                    onDismissRequest = { expandedFormula = false }
                ) {
                    FormulaDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onAreaClick = { expandedArea = true },
                        onVolumeClick = { expandedVolume = true },
                        onGeometryClick = { expandedGeometry = true },
                        onTrigonometryClick = { expandedTrigonometry = true },
                        onDerivativesClick = { expandedDerivatives = true },
                        onIntegralsClick = { expandedIntegrals = true },
                        onStatisticsClick = { expandedStatistics = true },
                        onLogarithmsClick = { expandedLogarithms = true },
                        onStandardEquationsClick = { expandedStandardEquations = true }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedArea,
                    onDismissRequest = { expandedArea = false }
                ) {
                    AreaDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onSquareClick = {
                            expandedArea = false
                            formula = Area().square()
                        },
                        onRectangleClick = {
                            expandedArea = false
                            formula = Area().rectangle()
                        },
                        onTriangleClick = {
                            expandedArea = false
                            formula = Area().triangle()
                        },
                        onRhombusClick = {
                            expandedArea = false
                            formula = Area().rhombus()
                        },
                        onTrapezoidClick = {
                            expandedArea = false
                            formula = Area().trapezoid()
                        },
                        onPolygonClick = {
                            expandedArea = false
                            formula = Area().polygon()
                        },
                        onCircleClick = {
                            expandedArea = false
                            formula = Area().circle()
                        },
                        onConeClick = {
                            expandedArea = false
                            formula = Area().cone()
                        },
                        onSphereClick = {
                            expandedArea = false
                            formula = Area().sphere()
                        }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedVolume,
                    onDismissRequest = { expandedVolume = false }
                ) {
                    VolumeDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onCubeClick = {
                            expandedVolume = false
                            formula = Volume().cube()
                        },
                        onParallelepipedClick = {
                            expandedVolume = false
                            formula = Volume().parallelepiped()
                        },
                        onPrismClick = {
                            expandedVolume = false
                            formula = Volume().prism()
                        },
                        onCylinderClick = {
                            expandedVolume = false
                            formula = Volume().cylinder()
                        },
                        onConeClick = {
                            expandedVolume = false
                            formula = Volume().cone()
                        },
                        onSphereClick = {
                            expandedVolume = false
                            formula = Volume().sphere()
                        }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedGeometry,
                    onDismissRequest = { expandedGeometry = false }
                ) {
                    GeometryDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onPythagoreanTheoremClick = {
                            expandedGeometry = false
                            formula = Geometry().pythagoreanTheorem()
                        },
                        onDistanceClick = {
                            expandedGeometry = false
                            formula = Geometry().distance()
                        },
                        onMidpointClick = {
                            expandedGeometry = false
                            formula = Geometry().midpoint()
                        },
                        onSlopeInterceptClick = {
                            expandedGeometry = false
                            formula = Geometry().slopeIntercept()
                        },
                        onCircleEquationClick = {
                            expandedGeometry = false
                            formula = Geometry().circleEquation()
                        },
                        onSphereEquationClick = {
                            expandedGeometry = false
                            formula = Geometry().sphereEquation()
                        },
                        onEllipseEquationClick = {
                            expandedGeometry = false
                            formula = Geometry().ellipseEquation()
                        }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedTrigonometry,
                    onDismissRequest = { expandedTrigonometry = false }
                ) {
                    TrigonometryDropDownMenu (
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onSinClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().sin()
                        },
                        onCosClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().cos()
                        },
                        onTanClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().tan()
                        },
                        onCscClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().csc()
                        },
                        onSecClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().sec()
                        },
                        onCotClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().cot()
                        },
                        onPythagoreanIdentitySinCosClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().pythagoreanIdentitySinCos()
                        },
                        onPythagoreanIdentityTanSecClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().pythagoreanIdentityTanSec()
                        },
                        onPythagoreanIdentityCscCotClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().pythagoreanIdentityCscCot()
                        },
                        onTanIdentityClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().tanIdentity()
                        },
                        onLawOfSinesClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().lawOfSines()
                        },
                        onLawOfCosinesClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().lawOfCosines()
                        },
                        onHeronFormulaAreaClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().heronFormulaArea()
                        },
                        onHeronFormulaSideClick = {
                            expandedTrigonometry = false
                            formula = Trigonometry().heronFormulaSide()
                        }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedDerivatives,
                    onDismissRequest = { expandedDerivatives = false }
                ) {
                    DerivativesDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onSlopeOfSecantLineClick = {
                            expandedDerivatives = false
                            formula = Derivatives().slopeOfSecantLine()
                        },
                        onRateOfChangeClick = {
                            expandedDerivatives = false
                            formula = Derivatives().rateOfChange()
                        },
                        onConstantClick = {
                            expandedDerivatives = false
                            formula = Derivatives().constant()
                        },
                        onConstantMultiplicationClick = {
                            expandedDerivatives = false
                            formula = Derivatives().constantMultiplication()
                        },
                        onPowerRuleClick = {
                            expandedDerivatives = false
                            formula = Derivatives().powerRule()
                        },
                        onExponentialClick = {
                            expandedDerivatives = false
                            formula = Derivatives().exponential()
                        },
                        onEulerExponentialClick = {
                            expandedDerivatives = false
                            formula = Derivatives().eulerExponential()
                        },
                        onSumRuleClick = {
                            expandedDerivatives = false
                            formula = Derivatives().sumRule()
                        },
                        onProductRuleClick = {
                            expandedDerivatives = false
                            formula = Derivatives().productRule()
                        },
                        onQuotientRuleClick = {
                            expandedDerivatives = false
                            formula = Derivatives().quotientRule()
                        },
                        onChainRuleClick = {
                            expandedDerivatives = false
                            formula = Derivatives().chainRule()
                        },
                        onSinClick = {
                            expandedDerivatives = false
                            formula = Derivatives().sin()
                        },
                        onCosClick = {
                            expandedDerivatives = false
                            formula = Derivatives().cos()
                        },
                        onTanClick = {
                            expandedDerivatives = false
                            formula = Derivatives().tan()
                        },
                        onLogClick = {
                            expandedDerivatives = false
                            formula = Derivatives().log()
                        },
                        onNaturalLogClick = {
                            expandedDerivatives = false
                            formula = Derivatives().naturalLog()
                        }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedIntegrals,
                    onDismissRequest = { expandedIntegrals = false }
                ) {
                    IntegralsDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onIntegralOfOneClick = {
                            expandedIntegrals = false
                            formula = Integrals().integralOfOne()
                        },
                        onLogClick = {
                            expandedIntegrals = false
                            formula = Integrals().log()
                        },
                        onEulerExponentialClick = {
                            expandedIntegrals = false
                            formula = Integrals().eulerExponential()
                        },
                        onSinClick = {
                            expandedVolume = false
                            formula = Integrals().sin()
                        },
                        onCosClick = {
                            expandedIntegrals = false
                            formula = Integrals().cos()
                        },
                        onLinearityAdditionClick = {
                            expandedIntegrals = false
                            formula = Integrals().linearityAddition()
                        },
                        onLinearityConstantMultiplicationClick = {
                            expandedIntegrals = false
                            formula = Integrals().linearityConstantMultiplication()
                        },
                        onIntegrationByPartsClick = {
                            expandedIntegrals = false
                            formula = Integrals().integrationByParts()
                        },
                        onIntegralReversalPropertyClick = {
                            expandedIntegrals = false
                            formula = Integrals().integralReversalProperty()
                        },
                        onIntegralZeroPropertyClick = {
                            expandedIntegrals = false
                            formula = Integrals().integralZeroProperty()
                        },
                        onIntegralAdditivePropertyClick = {
                            expandedIntegrals = false
                            formula = Integrals().integralAdditiveProperty()
                        },
                        onBarrowRuleClick = {
                            expandedIntegrals = false
                            formula = Integrals().barrowRule()
                        }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedStatistics,
                    onDismissRequest = { expandedStatistics = false }
                ) {
                    StatisticsDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onMeanClick = {
                            expandedStatistics = false
                            formula = Statistics().mean()
                        },
                        onMedianOddClick = {
                            expandedStatistics = false
                            formula = Statistics().medianOdd()
                        },
                        onMedianEvenClick = {
                            expandedStatistics = false
                            formula = Statistics().medianEven()
                        },
                        onVarianceClick = {
                            expandedStatistics = false
                            formula = Statistics().variance()
                        },
                        onStandardDeviationClick = {
                            expandedStatistics = false
                            formula = Statistics().standardDeviation()
                        },
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedLogarithms,
                    onDismissRequest = { expandedLogarithms = false }
                ) {
                    LogarithmsDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onLogOfOnePropertyClick = {
                            expandedLogarithms = false
                            formula = Logarithms().logOfOneProperty()
                        },
                        onLogInversePropertyClick = {
                            expandedLogarithms = false
                            formula = Logarithms().logInverseProperty()
                        },
                        onProductClick = {
                            expandedLogarithms = false
                            formula = Logarithms().product()
                        },
                        onQuotientClick = {
                            expandedLogarithms = false
                            formula = Logarithms().quotient()
                        },
                        onExponentialClick = {
                            expandedLogarithms = false
                            formula = Logarithms().exponential()
                        },
                        onChangeOfBaseClick = {
                            expandedLogarithms = false
                            formula = Logarithms().changeOfBase()
                        }
                    )
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expandedStandardEquations,
                    onDismissRequest = { expandedStandardEquations = false }
                ) {
                    StandardEquationsDropdownMenu(
                        expanded = expandedFormula,
                        onDismissRequest = { expandedFormula = false },
                        onQuadraticPolynomialClick = {
                            expandedStandardEquations = false
                            formula = Statistics().mean()
                        },
                        onQuadraticFormulaClick = {
                            expandedStandardEquations = false
                            formula = Statistics().medianOdd()
                        },
                        onParabolaClick = {
                            expandedStandardEquations = false
                            formula = Statistics().medianEven()
                        }
                    )
                }
            }
        }

        RenderLatexFormula(formula)
    }
}



class DrawingCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (typingEnabled) {
            when (keyCode) {
                KeyEvent.KEYCODE_DEL -> {
                    // Handle backspace
                    if (currentText.isNotEmpty()) {
                        currentText.deleteCharAt(currentText.length - 1)
                    }
                }
                else -> {
                    val char = event?.unicodeChar?.toChar()
                    if (char != null && char.isLetterOrDigit()) {
                        currentText.append(char)
                    }
                }
            }
            invalidate()
            return true // Indicate the event was handled
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (typingEnabled && keyCode == KeyEvent.KEYCODE_ENTER) {
            finishTyping() // Finalize the current text entry
            return true // Indicate the event was handled
        }
        return super.onKeyUp(keyCode, event)
    }
    private val pathPaint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = 8f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint().apply {
        color = Color.BLUE
        textSize = 50f
        isAntiAlias = true
    }

    private val path = Path()
    private val texts = mutableListOf<Pair<String, Pair<Float, Float>>>()

    private var typingEnabled = false
    private var currentText = StringBuilder()
    private var cursorPosition: Pair<Float, Float>? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val toolType = event.getToolType(0)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when (toolType) {
                    MotionEvent.TOOL_TYPE_MOUSE -> {
                        // Enable text input on mouse click
                        cursorPosition = Pair(x, y)
                        typingEnabled = true
                        invalidate()
                    }
                    MotionEvent.TOOL_TYPE_FINGER, MotionEvent.TOOL_TYPE_STYLUS -> {
                        // Enable drawing
                        typingEnabled = false
                        path.moveTo(x, y)
                        return true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (toolType == MotionEvent.TOOL_TYPE_FINGER || toolType == MotionEvent.TOOL_TYPE_STYLUS) {
                    path.lineTo(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {
                // Optional: Do something when touch is lifted
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the drawing paths
        canvas.drawPath(path, pathPaint)

        // Draw the existing text
        texts.forEach { (text, position) ->
            canvas.drawText(text, position.first, position.second, textPaint)
        }

        // Draw the current typing text
        cursorPosition?.let { position ->
            if (typingEnabled && currentText.isNotEmpty()) {
                canvas.drawText(currentText.toString(), position.first, position.second, textPaint)
            }
        }
    }

    fun addTypedText(keyCode: Int) {
        if (typingEnabled) {
            when (keyCode) {
                android.view.KeyEvent.KEYCODE_DEL -> {
                    // Handle backspace
                    if (currentText.isNotEmpty()) {
                        currentText.deleteCharAt(currentText.length - 1)
                    }
                }
                else -> {
                    val char = android.view.KeyEvent.keyCodeToString(keyCode).replace("KEYCODE_", "").firstOrNull()
                    if (char != null) {
                        currentText.append(char)
                    }
                }
            }
            invalidate()
        }
    }

    fun finishTyping() {
        if (typingEnabled && currentText.isNotEmpty() && cursorPosition != null) {
            texts.add(currentText.toString() to cursorPosition!!)
            currentText.clear()
            typingEnabled = false
            cursorPosition = null
            invalidate()
        }
    }

    fun clearCanvas() {
        path.reset()
        texts.clear()
        currentText.clear()
        typingEnabled = false
        cursorPosition = null
        invalidate()
    }

//    private fun showPenSizeChooserDialog() {
//        val penDialog = Dialog(this)
//        penDialog.setContentView(R.layout.dialog_pen_size)
//        penDialog.setTitle("Pen size: ")
//        val smallBtn = penDialog.findViewById<View>(R.id.ib_small_pen)
//        val mediumBtn = penDialog.findViewById<View>(R.id.ib_medium_pen)
//        val largeBtn = penDialog.findViewById<View>(R.id.ib_large_pen)
//        // Small size Pen
//        smallBtn?.setOnClickListener {
//            drawingView?.setSizeForPen(10.toFloat())
//            penDialog.dismiss()
//        }
//        // Medium size Pen
//        mediumBtn?.setOnClickListener {
//            drawingView?.setSizeForPen(20.toFloat())
//            penDialog.dismiss()
//        }
//        // Large size pen
//        largeBtn?.setOnClickListener {
//            drawingView?.setSizeForPen(30.toFloat())
//            penDialog.dismiss()
//        }
//        // Shows pen dialog
//        penDialog.show()
//    }
//
//    fun colorClicked(view: View){
//        if(view !== mImageButtonCurrentPaint){
//            val imageButton = view as ImageButton
//            val colorTag = imageButton.tag.toString()
//            drawingView?.setColor(colorTag)
//
//            imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
//
//            )
//            mImageButtonCurrentPaint?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_normal)
//
//            )
//
//
//            mImageButtonCurrentPaint = view
//
//        }
//    }
}
