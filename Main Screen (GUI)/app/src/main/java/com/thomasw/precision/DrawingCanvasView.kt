package com.thomasw.precision


import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.material3.Typography.*
import androidx.compose.material3.Icon


import androidx.compose.material.icons.filled.*

import androidx.compose.ui.Alignment


//pens and inks
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Style
import androidx.compose.ui.unit.sp

import ru.noties.jlatexmath.JLatexMathView

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable

import android.widget.TextView
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController


@Composable
fun NotesPageWithDrawing(
    navController: NavController,
) {
    println("Navigating to NotesPageWithDrawing DRAWING CANVAS")

    var expandedSettings by remember { mutableStateOf(false) }
    var showPensPopup by remember { mutableStateOf(false) }
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
    var formula by remember { mutableStateOf("") }
    val drawingCanvasView = remember { mutableStateOf<DrawingCanvasView?>(null) }


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
                    drawingCanvasView.value = this // Store reference
                }
                //LayoutInflater.from(context).inflate(R.layout.`latex_view.txt`, null)

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
            // Back Button (Top Left)
            androidx.compose.material3.IconButton(onClick = {
                // Simply pop the current screen from the navigation stack
                drawingCanvasView.value?.dismissPopups()
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button"
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
                            showPensPopup = true
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

                PensPopup(
                    showPopup = showPensPopup,
                    onDismiss = { showPensPopup = false },
                    onSizeChange = { size ->
                        drawingCanvasView.value?.updatePenSettings(
                            color = drawingCanvasView.value?.currentColor ?: Color.BLACK,
                            size = size
                        )
                    },
                    onColorChange = { color ->
                        drawingCanvasView.value?.updatePenSettings(
                            color = color,
                            size = drawingCanvasView.value?.currentSize ?: 8f
                        )
                    }
                )



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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().square()
                        },
                        onRectangleClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().rectangle()
                        },
                        onTriangleClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().triangle()
                        },
                        onRhombusClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().rhombus()
                        },
                        onTrapezoidClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().trapezoid()
                        },
                        onPolygonClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().polygon()
                        },
                        onCircleClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().circle()
                        },
                        onConeClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Area().cone()
                        },
                        onSphereClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Volume().cube()
                        },
                        onParallelepipedClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Volume().parallelepiped()
                        },
                        onPrismClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Volume().prism()
                        },
                        onCylinderClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Volume().cylinder()
                        },
                        onConeClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Volume().cone()
                        },
                        onSphereClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Geometry().pythagoreanTheorem()
                        },
                        onDistanceClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Geometry().distance()
                        },
                        onMidpointClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Geometry().midpoint()
                        },
                        onSlopeInterceptClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Geometry().slopeIntercept()
                        },
                        onCircleEquationClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Geometry().circleEquation()
                        },
                        onSphereEquationClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Geometry().sphereEquation()
                        },
                        onEllipseEquationClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().sin()
                        },
                        onCosClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().cos()
                        },
                        onTanClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().tan()
                        },
                        onCscClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().csc()
                        },
                        onSecClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().sec()
                        },
                        onCotClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().cot()
                        },
                        onPythagoreanIdentitySinCosClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().pythagoreanIdentitySinCos()
                        },
                        onPythagoreanIdentityTanSecClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().pythagoreanIdentityTanSec()
                        },
                        onPythagoreanIdentityCscCotClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().pythagoreanIdentityCscCot()
                        },
                        onTanIdentityClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().tanIdentity()
                        },
                        onLawOfSinesClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().lawOfSines()
                        },
                        onLawOfCosinesClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().lawOfCosines()
                        },
                        onHeronFormulaAreaClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Trigonometry().heronFormulaArea()
                        },
                        onHeronFormulaSideClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().slopeOfSecantLine()
                        },
                        onRateOfChangeClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().rateOfChange()
                        },
                        onConstantClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().constant()
                        },
                        onConstantMultiplicationClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().constantMultiplication()
                        },
                        onPowerRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().powerRule()
                        },
                        onExponentialClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().exponential()
                        },
                        onEulerExponentialClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().eulerExponential()
                        },
                        onSumRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().sumRule()
                        },
                        onProductRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().productRule()
                        },
                        onQuotientRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().quotientRule()
                        },
                        onChainRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().chainRule()
                        },
                        onSinClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().sin()
                        },
                        onCosClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().cos()
                        },
                        onTanClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().tan()
                        },
                        onLogClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Derivatives().log()
                        },
                        onNaturalLogClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().integralOfOne()
                        },
                        onLogClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().log()
                        },
                        onEulerExponentialClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().eulerExponential()
                        },
                        onSinClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().sin()
                        },
                        onCosClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().cos()
                        },
                        onLinearityAdditionClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().linearityAddition()
                        },
                        onLinearityConstantMultiplicationClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().linearityConstantMultiplication()
                        },
                        onIntegrationByPartsClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().integrationByParts()
                        },
                        onIntegralReversalPropertyClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().integralReversalProperty()
                        },
                        onIntegralZeroPropertyClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().integralZeroProperty()
                        },
                        onIntegralAdditivePropertyClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Integrals().integralAdditiveProperty()
                        },
                        onBarrowRuleClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Statistics().mean()
                        },
                        onMedianOddClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Statistics().medianOdd()
                        },
                        onMedianEvenClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Statistics().medianEven()
                        },
                        onVarianceClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Statistics().variance()
                        },
                        onStandardDeviationClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Logarithms().logOfOneProperty()
                        },
                        onLogInversePropertyClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Logarithms().logInverseProperty()
                        },
                        onProductClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Logarithms().product()
                        },
                        onQuotientClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Logarithms().quotient()
                        },
                        onExponentialClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Logarithms().exponential()
                        },
                        onChangeOfBaseClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
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
                            expandedFormula = false
                            expandedSettings = false
                            formula = Statistics().mean()
                        },
                        onQuadraticFormulaClick = {
                            expandedStandardEquations = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Statistics().medianOdd()
                        },
                        onParabolaClick = {
                            expandedStandardEquations = false
                            expandedFormula = false
                            expandedSettings = false
                            formula = Statistics().medianEven()
                        }
                    )
                }
            }
        }



        if (formula.isNotEmpty()) {
            drawingCanvasView.value?.ShowFormulaInOverlay(formula)
        }
    }
}



class DrawingCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var currentColor = androidx.compose.ui.graphics.Color.Black
    private var currentSize = 8f

    private val pathPaint = Paint().apply {
        isAntiAlias = true
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

    @Composable
    fun ShowFormulaInOverlay(formula: String) {
        showFormulaOverlay(formula)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (typingEnabled) {
            when (keyCode) {
                KeyEvent.KEYCODE_DEL -> {
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
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (typingEnabled && keyCode == KeyEvent.KEYCODE_ENTER) {
            finishTyping()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val toolType = event.getToolType(0)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when (toolType) {
                    MotionEvent.TOOL_TYPE_MOUSE -> {
                        cursorPosition = Pair(x, y)
                        typingEnabled = true
                        invalidate()
                    }
                    MotionEvent.TOOL_TYPE_FINGER, MotionEvent.TOOL_TYPE_STYLUS -> {
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
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathPaint.color = currentColor.toArgb()
        pathPaint.strokeWidth = currentSize
        canvas.drawPath(path, pathPaint)

        texts.forEach { (text, position) ->
            canvas.drawText(text, position.first, position.second, textPaint)
        }

        cursorPosition?.let { position ->
            if (typingEnabled && currentText.isNotEmpty()) {
                canvas.drawText(currentText.toString(), position.first, position.second, textPaint)
            }
        }
    }

    fun updatePenSettings(color: androidx.compose.ui.graphics.Color, size: Float) {
        currentColor = color
        currentSize = size
    }

    fun addTypedText(keyCode: Int) {
        if (typingEnabled) {
            when (keyCode) {
                android.view.KeyEvent.KEYCODE_DEL -> {
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

    // Global or shared set to track active formulas
    val activeFormulas = mutableSetOf<String>()
    private var popupWindow: PopupWindow? = null

    fun showFormulaOverlay(formula: String) {
        if (activeFormulas.contains(formula)) {
            Log.d("Debug", "Formula '$formula' is already displayed.")
            return
        }

        activeFormulas.add(formula)
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.latex_view, null)
        layout.setBackgroundColor(Color.TRANSPARENT)

        val latexMathView = layout.findViewById<ru.noties.jlatexmath.JLatexMathView>(R.id.j_latex_math_view)
        latexMathView.setBackgroundColor(Color.TRANSPARENT)

        if (latexMathView != null) {
            try {
                latexMathView.setLatex(formula)
            } catch (e: Exception) {
                Log.e("Debug", "Error setting LaTeX formula: ${e.message}")
            }
        }

        popupWindow = PopupWindow(layout, 500, 250, true)
        popupWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow?.isFocusable = false
        popupWindow?.isOutsideTouchable = false

        layout.setOnTouchListener(object : View.OnTouchListener {
            private var offsetX = 0f
            private var offsetY = 0f
            var updatedX = 0
            var updatedY = 0

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        offsetX = event.rawX - updatedX
                        offsetY = event.rawY - updatedY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        updatedX = (event.rawX - offsetX).toInt()
                        updatedY = (event.rawY - offsetY).toInt()
                        popupWindow?.update(updatedX, updatedY, -1, -1)
                    }
                }
                return true
            }
        })

        popupWindow?.showAtLocation(layout, Gravity.CENTER, 0, 0)
    }

    fun dismissPopups() {
        popupWindow?.dismiss()
        activeFormulas.clear()
        Log.d("Debug", "Popups cleared and formulas removed.")
    }
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

