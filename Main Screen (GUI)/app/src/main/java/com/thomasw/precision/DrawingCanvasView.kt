package com.thomasw.precision


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.ColorDrawable
import android.graphics.pdf.PdfDocument
import android.os.Environment
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
import androidx.compose.material3.Text
import androidx.compose.material3.Icon


import androidx.compose.material.icons.filled.*

import androidx.compose.ui.Alignment


//pens and inks
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.thomasw.precision.ui.*
import java.io.File
import java.io.FileOutputStream


@Composable
            fun NotesPageWithDrawing(
                navController: NavController,
            ) {
                println("Navigating to NotesPageWithDrawing DRAWING CANVAS")

                val drawingCanvasView = remember { mutableStateOf<DrawingCanvasView?>(null) }
                var expandedSettings by remember { mutableStateOf(false) }
                var showPensPopup by remember { mutableStateOf(false) }

                //formulas
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
                var printFormulas by remember { mutableStateOf(false) }



                //notebook
                var showNotebookPreferences by remember { mutableStateOf(false) }

                //export
                var showExportPopup by remember { mutableStateOf(false) }
                val context = LocalContext.current

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
                drawingCanvasView.value?.dismissActivePopups()
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
                androidx.compose.material3.IconButton(onClick = { showExportPopup = true }) {
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
                            showNotebookPreferences = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Formulas") },
                        onClick = {
                            expandedFormula = true
                        }
                    )
                }


                // Notebook Preferences Popup
                NotebookPreferencesPopup(
                    showPopup = showNotebookPreferences,
                    onDismiss = { showNotebookPreferences = false },
                    onBackgroundColorChange = { isBlack ->
                        drawingCanvasView.value?.setBackgroundColorPreference(isBlack)
                    },
                    onLinedChange = { isLined ->
                        drawingCanvasView.value?.setBackgroundLinedPreference(isLined)
                    }
                )

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

                // Export Popup
                ExportPopup(
                    showPopup = showExportPopup,
                    onDismiss = { showExportPopup = false },
                    onExport = {
                        val filePath = drawingCanvasView.value?.exportAsPdf(context, "Notebook_${System.currentTimeMillis()}")
                        if (filePath != null) {
                            Toast.makeText(context, "PDF saved to $filePath", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onCopyLink = { filePath ->
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                        val clip = android.content.ClipData.newPlainText("PDF Link", filePath)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
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
                            printFormulas = true
                            formula = Area().square()
                        },
                        onRectangleClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Area().rectangle()
                        },
                        onTriangleClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Area().triangle()
                        },
                        onRhombusClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Area().rhombus()
                        },
                        onTrapezoidClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Area().trapezoid()
                        },
                        onPolygonClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Area().polygon()
                        },
                        onCircleClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Area().circle()
                        },
                        onConeClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Area().cone()
                        },
                        onSphereClick = {
                            expandedArea = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Volume().cube()
                        },
                        onParallelepipedClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Volume().parallelepiped()
                        },
                        onPrismClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Volume().prism()
                        },
                        onCylinderClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Volume().cylinder()
                        },
                        onConeClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Volume().cone()
                        },
                        onSphereClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Geometry().pythagoreanTheorem()
                        },
                        onDistanceClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Geometry().distance()
                        },
                        onMidpointClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Geometry().midpoint()
                        },
                        onSlopeInterceptClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Geometry().slopeIntercept()
                        },
                        onCircleEquationClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Geometry().circleEquation()
                        },
                        onSphereEquationClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Geometry().sphereEquation()
                        },
                        onEllipseEquationClick = {
                            expandedGeometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Trigonometry().sin()
                        },
                        onCosClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().cos()
                        },
                        onTanClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().tan()
                        },
                        onCscClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().csc()
                        },
                        onSecClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().sec()
                        },
                        onCotClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().cot()
                        },
                        onPythagoreanIdentitySinCosClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().pythagoreanIdentitySinCos()
                        },
                        onPythagoreanIdentityTanSecClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().pythagoreanIdentityTanSec()
                        },
                        onPythagoreanIdentityCscCotClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().pythagoreanIdentityCscCot()
                        },
                        onTanIdentityClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().tanIdentity()
                        },
                        onLawOfSinesClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().lawOfSines()
                        },
                        onLawOfCosinesClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().lawOfCosines()
                        },
                        onHeronFormulaAreaClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Trigonometry().heronFormulaArea()
                        },
                        onHeronFormulaSideClick = {
                            expandedTrigonometry = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Derivatives().slopeOfSecantLine()
                        },
                        onRateOfChangeClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().rateOfChange()
                        },
                        onConstantClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().constant()
                        },
                        onConstantMultiplicationClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().constantMultiplication()
                        },
                        onPowerRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().powerRule()
                        },
                        onExponentialClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().exponential()
                        },
                        onEulerExponentialClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().eulerExponential()
                        },
                        onSumRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().sumRule()
                        },
                        onProductRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().productRule()
                        },
                        onQuotientRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().quotientRule()
                        },
                        onChainRuleClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().chainRule()
                        },
                        onSinClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().sin()
                        },
                        onCosClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().cos()
                        },
                        onTanClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().tan()
                        },
                        onLogClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Derivatives().log()
                        },
                        onNaturalLogClick = {
                            expandedDerivatives = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Integrals().integralOfOne()
                        },
                        onLogClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().log()
                        },
                        onEulerExponentialClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().eulerExponential()
                        },
                        onSinClick = {
                            expandedVolume = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().sin()
                        },
                        onCosClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().cos()
                        },
                        onLinearityAdditionClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().linearityAddition()
                        },
                        onLinearityConstantMultiplicationClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().linearityConstantMultiplication()
                        },
                        onIntegrationByPartsClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().integrationByParts()
                        },
                        onIntegralReversalPropertyClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().integralReversalProperty()
                        },
                        onIntegralZeroPropertyClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().integralZeroProperty()
                        },
                        onIntegralAdditivePropertyClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Integrals().integralAdditiveProperty()
                        },
                        onBarrowRuleClick = {
                            expandedIntegrals = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Statistics().mean()
                        },
                        onMedianOddClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Statistics().medianOdd()
                        },
                        onMedianEvenClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Statistics().medianEven()
                        },
                        onVarianceClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Statistics().variance()
                        },
                        onStandardDeviationClick = {
                            expandedStatistics = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Logarithms().logOfOneProperty()
                        },
                        onLogInversePropertyClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Logarithms().logInverseProperty()
                        },
                        onProductClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Logarithms().product()
                        },
                        onQuotientClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Logarithms().quotient()
                        },
                        onExponentialClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Logarithms().exponential()
                        },
                        onChangeOfBaseClick = {
                            expandedLogarithms = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
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
                            printFormulas = true
                            formula = Statistics().mean()
                        },
                        onQuadraticFormulaClick = {
                            expandedStandardEquations = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Statistics().medianOdd()
                        },
                        onParabolaClick = {
                            expandedStandardEquations = false
                            expandedFormula = false
                            expandedSettings = false
                            printFormulas = true
                            formula = Statistics().medianEven()
                        }
                    )
                }
            }
        }



        if (formula.isNotEmpty() && printFormulas) {
            drawingCanvasView.value?.ShowFormulaInOverlay(formula)

            printFormulas = false
        }
    }
}



class DrawingCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var currentColor = androidx.compose.ui.graphics.Color.Black
    var currentSize = 8f

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
    @Composable
    fun PensPopup(
        showPopup: Boolean,
        onDismiss: () -> Unit,
        onSizeChange: (Float) -> Unit
    ) {
        if (showPopup) {
            AlertDialog(
                onDismissRequest = { onDismiss() },
                title = { Text("Adjust Pen Settings") },
                text = {
                    Column {
                        // Size change section
                        Text("Choose Pen Size:")
                        var penSize by remember { mutableStateOf(20f) } // Default size
                        Slider(
                            value = penSize,
                            onValueChange = { size ->
                                penSize = size
                                onSizeChange(size)
                            },
                            valueRange = 1f..50f, // Range of sizes
                            steps = 49 // Number of steps in the slider
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { onDismiss() }) {
                        Text("Done")
                    }
                }
            )
        }
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
        pathPaint.color = currentColor.toArgb()
        pathPaint.strokeWidth = currentSize
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

    fun updatePenSettings(color: Any, size: Float) {
        currentColor = color as androidx.compose.ui.graphics.Color
        currentSize = size
        invalidate()
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


    // New methods for background preferences
    fun setBackgroundColorPreference(isBlack: Boolean) {
        setBackgroundColor(if (isBlack) Color.BLACK else Color.WHITE)
        invalidate() // Redraw the canvas with the updated background color
    }

    fun setBackgroundLinedPreference(isLined: Boolean) {
        // Logic to draw lines if "lined" is true
        if (isLined) {
            val lineSpacing = 100 // Example spacing between lines
            val linePaint = Paint().apply {
                color = android.graphics.Color.GRAY
                strokeWidth = 2f
            }

            val canvas = Canvas()
            for (y in 0..height step lineSpacing) {
                canvas.drawLine(0f, y.toFloat(), width.toFloat(), y.toFloat(), linePaint)
            }
        } else {
            path.reset() // Reset the canvas if "lined" is disabled
        }
        invalidate() // Trigger a redraw
    }


    // Global or shared set to track active formulas
    // Global or shared set to track active formulas
    val activeFormulas = mutableSetOf<String>()
    val activePopups = mutableListOf<PopupWindow>() // To track active PopupWindows

    fun showFormulaOverlay(formula: String) {

        // Add the formula to the active set
        activeFormulas.add(formula)

        // Inflate the layout for the LaTeX formula
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.latex_view, null)

        layout.setBackgroundColor(Color.TRANSPARENT)

        // Find the JLatexMathView and set the LaTeX formula
        val latexMathView = layout.findViewById<ru.noties.jlatexmath.JLatexMathView>(R.id.j_latex_math_view)
        latexMathView?.setBackgroundColor(Color.TRANSPARENT)

        try {
            latexMathView?.setLatex(formula)
        } catch (e: Exception) {
            Log.e("Debug", "Error setting LaTeX formula: ${e.message}")
        }

        // Create and show the PopupWindow
        val popupWindow = PopupWindow(layout, 500, 250, true)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isFocusable = false
        popupWindow.isOutsideTouchable = false

        // Track this PopupWindow
        activePopups.add(popupWindow)

        // Set touch listener for moving the PopupWindow
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
                        popupWindow.update(updatedX, updatedY, -1, -1)
                    }
                }
                return true
            }
        })

        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0)
    }

    // Dismiss all active PopupWindows
    fun dismissActivePopups() {
        for (popup in activePopups) {
            popup.dismiss()
        }
        activePopups.clear()
        activeFormulas.clear()
    }

    // New method to export as PDF
    fun exportAsPdf(context: Context, fileName: String): String? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        // Draw the canvas content onto the PDF page
        this.draw(page.canvas)
        pdfDocument.finishPage(page)

        // Save the PDF to a file
        val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "PrecisionNotes")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, "$fileName.pdf")
        return try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
