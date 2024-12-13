package com.thomasw.precision

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpOffset



@Composable
fun FormulaDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onAreaClick: () -> Unit,
    onVolumeClick: () -> Unit,
    onGeometryClick: () -> Unit,
    onTrigonometryClick: () -> Unit,
    onDerivativesClick: () -> Unit,
    onIntegralsClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onLogarithmsClick: () -> Unit,
    onStandardEquationsClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(944.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Area") },
            onClick = onAreaClick
        )
        DropdownMenuItem(
            text = { Text("Volume") },
            onClick = onVolumeClick
        )
        DropdownMenuItem(
            text = { Text("Geometry") },
            onClick = onGeometryClick
        )
        DropdownMenuItem(
            text = { Text("Trigonometry") },
            onClick = onTrigonometryClick
        )
        DropdownMenuItem(
            text = { Text("Derivatives") },
            onClick = onDerivativesClick
        )
        DropdownMenuItem(
            text = { Text("Integrals") },
            onClick = onIntegralsClick
        )
        DropdownMenuItem(
            text = { Text("Statistics") },
            onClick = onStatisticsClick
        )
        DropdownMenuItem(
            text = { Text("Logarithms") },
            onClick = onLogarithmsClick
        )
        DropdownMenuItem(
            text = { Text("Standard Equations") },
            onClick = onStandardEquationsClick
        )
    }
}

@Composable
fun AreaDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSquareClick: () -> Unit,
    onRectangleClick: () -> Unit,
    onTriangleClick: () -> Unit,
    onRhombusClick: () -> Unit,
    onTrapezoidClick: () -> Unit,
    onPolygonClick: () -> Unit,
    onCircleClick: () -> Unit,
    onConeClick: () -> Unit,
    onSphereClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Square") },
            onClick = onSquareClick
        )
        DropdownMenuItem(
            text = { Text("Rectangle") },
            onClick = onRectangleClick
        )
        DropdownMenuItem(
            text = { Text("Triangle") },
            onClick = onTriangleClick
        )
        DropdownMenuItem(
            text = { Text("Rhombus") },
            onClick = onRhombusClick
        )
        DropdownMenuItem(
            text = { Text("Trapezoid") },
            onClick = onTrapezoidClick
        )
        DropdownMenuItem(
            text = { Text("Polygon") },
            onClick = onPolygonClick
        )
        DropdownMenuItem(
            text = { Text("Circle") },
            onClick = onCircleClick
        )
        DropdownMenuItem(
            text = { Text("Cone") },
            onClick = onConeClick
        )
        DropdownMenuItem(
            text = { Text("Sphere") },
            onClick = onSphereClick
        )
    }
}

@Composable
fun VolumeDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onCubeClick: () -> Unit,
    onParallelepipedClick: () -> Unit,
    onPrismClick: () -> Unit,
    onCylinderClick: () -> Unit,
    onConeClick: () -> Unit,
    onSphereClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Cube") },
            onClick = onCubeClick
        )
        DropdownMenuItem(
            text = { Text("Parallelepiped") },
            onClick = onParallelepipedClick
        )
        DropdownMenuItem(
            text = { Text("Prism") },
            onClick = onPrismClick
        )
        DropdownMenuItem(
            text = { Text("Cylinder") },
            onClick = onCylinderClick
        )
        DropdownMenuItem(
            text = { Text("Cone") },
            onClick = onConeClick
        )
        DropdownMenuItem(
            text = { Text("Sphere") },
            onClick = onSphereClick
        )
    }
}

@Composable
fun GeometryDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onPythagoreanTheoremClick: () -> Unit,
    onDistanceClick: () -> Unit,
    onMidpointClick: () -> Unit,
    onSlopeInterceptClick: () -> Unit,
    onCircleEquationClick: () -> Unit,
    onSphereEquationClick: () -> Unit,
    onEllipseEquationClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Pythagorean Theorem") },
            onClick = onPythagoreanTheoremClick
        )
        DropdownMenuItem(
            text = { Text("Distance") },
            onClick = onDistanceClick
        )
        DropdownMenuItem(
            text = { Text("Midpoint") },
            onClick = onMidpointClick
        )
        DropdownMenuItem(
            text = { Text("Slope-Intercept Form") },
            onClick = onSlopeInterceptClick
        )
        DropdownMenuItem(
            text = { Text("Circle Equation") },
            onClick = onCircleEquationClick
        )
        DropdownMenuItem(
            text = { Text("Sphere Equation") },
            onClick = onSphereEquationClick
        )
        DropdownMenuItem(
            text = { Text("Ellipse Equation") },
            onClick = onEllipseEquationClick
        )
    }
}

@Composable
fun TrigonometryDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSinClick: () -> Unit,
    onCosClick: () -> Unit,
    onTanClick: () -> Unit,
    onCscClick: () -> Unit,
    onSecClick: () -> Unit,
    onCotClick: () -> Unit,
    onPythagoreanIdentitySinCosClick: () -> Unit,
    onPythagoreanIdentityTanSecClick: () -> Unit,
    onPythagoreanIdentityCscCotClick: () -> Unit,
    onTanIdentityClick: () -> Unit,
    onLawOfSinesClick: () -> Unit,
    onLawOfCosinesClick: () -> Unit,
    onHeronFormulaAreaClick: () -> Unit,
    onHeronFormulaSideClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Sin") },
            onClick = onSinClick
        )
        DropdownMenuItem(
            text = { Text("Cos") },
            onClick = onCosClick
        )
        DropdownMenuItem(
            text = { Text("Tan") },
            onClick = onTanClick
        )
        DropdownMenuItem(
            text = { Text("Csc") },
            onClick = onCscClick
            )
        DropdownMenuItem(
            text = { Text("Sec") },
            onClick = onSecClick
        )
        DropdownMenuItem(
            text = { Text("Cot") },
            onClick = onCotClick
        )
        DropdownMenuItem(
            text = { Text("Pythagorean Identity for Sin and Cos") },
            onClick = onPythagoreanIdentitySinCosClick
        )
        DropdownMenuItem(
            text = { Text("Pythagorean Identity for Tan and Sec") },
            onClick = onPythagoreanIdentityTanSecClick
        )
        DropdownMenuItem(
            text = { Text("Pythagorean Identity for Csc and Cot") },
            onClick = onPythagoreanIdentityCscCotClick
        )
        DropdownMenuItem(
            text = { Text("Tan Identity") },
            onClick = onTanIdentityClick
        )
        DropdownMenuItem(
            text = { Text("Law of Sines") },
            onClick = onLawOfSinesClick
        )
        DropdownMenuItem(
            text = { Text("Law of Cosines") },
            onClick = onLawOfCosinesClick
        )
        DropdownMenuItem(
            text = { Text("Heron's Formula for Area") },
            onClick = onHeronFormulaAreaClick
        )
        DropdownMenuItem(
            text = { Text("Heron's Formula for Side") },
            onClick = onHeronFormulaSideClick
        )
    }
}

@Composable
fun DerivativesDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSlopeOfSecantLineClick: () -> Unit,
    onRateOfChangeClick: () -> Unit,
    onConstantClick: () -> Unit,
    onConstantMultiplicationClick: () -> Unit,
    onPowerRuleClick: () -> Unit,
    onExponentialClick: () -> Unit,
    onEulerExponentialClick: () -> Unit,
    onSumRuleClick: () -> Unit,
    onProductRuleClick: () -> Unit,
    onQuotientRuleClick: () -> Unit,
    onChainRuleClick: () -> Unit,
    onSinClick: () -> Unit,
    onCosClick: () -> Unit,
    onTanClick: () -> Unit,
    onLogClick: () -> Unit,
    onNaturalLogClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
        ) {
        DropdownMenuItem(
            text = { Text("Slope of Secant Line") },
            onClick = onSlopeOfSecantLineClick
        )
        DropdownMenuItem(
            text = { Text("Rate of Change") },
            onClick = onRateOfChangeClick
        )
        DropdownMenuItem(
            text = { Text("Constant") },
            onClick = onConstantClick
        )
        DropdownMenuItem(
            text = { Text("Constant Multiplication") },
            onClick = onConstantMultiplicationClick
        )
        DropdownMenuItem(
            text = { Text("Power Rule") },
            onClick = onPowerRuleClick
        )
        DropdownMenuItem(
            text = { Text("Exponential") },
            onClick = onExponentialClick
        )
        DropdownMenuItem(
            text = { Text("Euler's Exponential") },
            onClick = onEulerExponentialClick
            )
        DropdownMenuItem(
            text = { Text("Sum Rule") },
            onClick = onSumRuleClick
        )
        DropdownMenuItem(
            text = { Text("Product Rule") },
            onClick = onProductRuleClick
        )
        DropdownMenuItem(
            text = { Text("Quotient Rule") },
            onClick = onQuotientRuleClick
        )
        DropdownMenuItem(
            text = { Text("Chain Rule") },
            onClick = onChainRuleClick
        )
        DropdownMenuItem(
            text = { Text("Sin") },
            onClick = onSinClick
        )
        DropdownMenuItem(
            text = { Text("Cos") },
            onClick = onCosClick
        )
        DropdownMenuItem(
            text = { Text("Tan") },
            onClick = onTanClick
        )
        DropdownMenuItem(
            text = { Text("Log") },
            onClick = onLogClick
        )
        DropdownMenuItem(
            text = { Text("Natural Log") },
            onClick = onNaturalLogClick
        )
    }
}

@Composable
fun IntegralsDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onIntegralOfOneClick: () -> Unit,
    onLogClick: () -> Unit,
    onEulerExponentialClick: () -> Unit,
    onSinClick: () -> Unit,
    onCosClick: () -> Unit,
    onLinearityAdditionClick: () -> Unit,
    onLinearityConstantMultiplicationClick: () -> Unit,
    onIntegrationByPartsClick: () -> Unit,
    onIntegralReversalPropertyClick: () -> Unit,
    onIntegralZeroPropertyClick: () -> Unit,
    onIntegralAdditivePropertyClick: () -> Unit,
    onBarrowRuleClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Integral of One") },
            onClick = onIntegralOfOneClick
        )

        DropdownMenuItem(
            text = { Text("Log") },
            onClick = onLogClick
        )
        DropdownMenuItem(
            text = { Text("Euler Exponential") },
            onClick = onEulerExponentialClick
        )
        DropdownMenuItem(
            text = { Text("Sin") },
            onClick = onSinClick
        )
        DropdownMenuItem(
            text = { Text("Cos") },
            onClick = onCosClick
        )
        DropdownMenuItem(
            text = { Text("Linearity Addition") },
            onClick = onLinearityAdditionClick
        )
        DropdownMenuItem(
            text = { Text("Linearity Constant Multiplication") },
            onClick = onLinearityConstantMultiplicationClick
        )
        DropdownMenuItem(
            text = { Text("Integration by Parts") },
            onClick = onIntegrationByPartsClick
        )
        DropdownMenuItem(
            text = { Text("Integral Reversal Property") },
            onClick = onIntegralReversalPropertyClick
        )
        DropdownMenuItem(
            text = { Text("Integral Zero Property") },
            onClick = onIntegralZeroPropertyClick
        )
        DropdownMenuItem(
            text = { Text("Integral Additive Property") },
            onClick = onIntegralAdditivePropertyClick
        )
        DropdownMenuItem(
            text = { Text("Barrow Rule") },
            onClick = onBarrowRuleClick
        )
    }
}

@Composable
fun StatisticsDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onMeanClick: () -> Unit,
    onMedianOddClick: () -> Unit,
    onMedianEvenClick: () -> Unit,
    onVarianceClick: () -> Unit,
    onStandardDeviationClick: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Mean") },
            onClick = onMeanClick
        )
        DropdownMenuItem(
            text = { Text("Median (Odd)") },
            onClick = onMedianOddClick
        )
        DropdownMenuItem(
            text = { Text("Median (Even)") },
            onClick = onMedianEvenClick
        )
        DropdownMenuItem(
            text = { Text("Variance") },
            onClick = onVarianceClick
        )
        DropdownMenuItem(
            text = { Text("Standard Deviation") },
            onClick = onStandardDeviationClick
        )
    }
}

@Composable
fun LogarithmsDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onLogOfOnePropertyClick: () -> Unit,
    onLogInversePropertyClick: () -> Unit,
    onProductClick: () -> Unit,
    onQuotientClick: () -> Unit,
    onExponentialClick: () -> Unit,
    onChangeOfBaseClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Log of One Property") },
            onClick = onLogOfOnePropertyClick
        )
        DropdownMenuItem(
            text = { Text("Log Inverse Property") },
            onClick = onLogInversePropertyClick
        )
        DropdownMenuItem(
            text = { Text("Product") },
            onClick = onProductClick
        )
        DropdownMenuItem(
            text = { Text("Quotient") },
        onClick = onQuotientClick
        )
        DropdownMenuItem(
            text = { Text("Exponential") },
            onClick = onExponentialClick
        )
        DropdownMenuItem(
            text = { Text("Change of Base") },
            onClick = onChangeOfBaseClick
        )
    }
}

@Composable
fun StandardEquationsDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onQuadraticPolynomialClick: () -> Unit,
    onQuadraticFormulaClick: () -> Unit,
    onParabolaClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier,
        offset = DpOffset(832.dp, 172.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Quadratic Polynomial") },
            onClick = onQuadraticPolynomialClick
        )
        DropdownMenuItem(
            text = { Text("Quadratic Formula") },
            onClick = onQuadraticFormulaClick
        )
        DropdownMenuItem(
            text = { Text("Parabola") },
            onClick = onParabolaClick
        )
    }
}