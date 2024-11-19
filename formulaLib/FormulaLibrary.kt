package formulaLib

class Geometry {
    fun pythagoreanTheorem(): String {
        return "a^2 + b^2 = c^2"
    }

    fun distance(): String {
        return "\$\\overline{AB} =  \\sqrt{(x_{1} - x_{2})^2 + (y_{1} - y_{2})^2}\$"
    }

    fun midpoint(): String {
        return "\$M\\left( \\frac{x_{1} + x_{2}}{2}, \\frac{y_{1} + y_{2}}{2}\\right)\$"
    }

    fun slopeIntercept(): String {
        return "\$y = mx + b\$"
    }

    fun circleEquation(): String {
        return "\$\\left( x - x_{0}\\right) ^2 + \\left( y - y_{0}\\right) ^2 = r^2\$"
    }

    fun sphereEquation(): String {
        return "\$\\left( x - x_{0}\\right) ^2 + \\left( y - y_{0}\\right) ^2 + \\left( z - z_{0}\\right) ^2 = r^2\$"
    }

    fun ellipseEquation(): String {
        return "\$\\left( \\frac{x - h}{a}\\right) ^2 + \\left( \\frac{y - k}{b}\\right) ^2 = 1\$"
    }
}

class Area {
    fun square(): String {
        return "A = l^2"
    }

    fun rectangle(): String {
        val latexText = "\$A = l \\times w\$"

        return "\$A = l \\times w\$"
    }

    fun triangle(): String {
        return "\$A = \\frac{b \\times h}{2}\$"
    }

    fun rhombus(): String {
        return "\$A = \\frac{D \\times d}{2}\$"
    }

    fun trapezoid(): String {
        return "\$A = \\frac{D \\times d}{2} \\times h\$"
    }

    fun polygon(): String {
        return "\$A = \\frac{P}{2} \\times a\$"
    }

    fun circle(): String {
        return "\$A = \\pi r^2\$"
    }

    fun cone(): String {
        return "\$A = \\pi r \\times s\$"
    }

    fun sphere(): String {
        return "\$A = 4 \\pi r^2\$"
    }
}

class Volume {
    fun cube(): String {
        return "V = s^3"
    }

    fun parallelepiped(): String {
        return "\$V = l \\times w \\times h\$"
    }

    fun prism(): String {
        return "\$V = b \\times h\$"
    }

    fun cylinder(): String {
        return "\$V = \\pi r^2 \\times h\$"
    }

    fun cone(): String {
        return "\$V = \\frac{1}{3} b\\times h\$"
    }

    fun sphere(): String {
        return "\$V = \\frac{4}{3} \\pi r^3\$"
    }
}

class StandardEquation {
    fun quadraticPolynomial(): String {
        return "\$ax^2 + bx + c = 0\$"
    }

    fun quadraticFormula(): String {
        return "\$x = \\frac{-b \\pm \\sqrt{b^2 - 4ac}}{2a}\$"
    }

    fun parabola(): String {
        return "\$y = a\\left( x-h \\right) ^2 + k\$"
    }
}

class Trigonometry {
    fun sin(): String {
        return "\$\\sin \\theta = \\frac{opp}{hip}\$"
    }

    fun cos(): String {
        return "\$\\cos \\theta = \\frac{adj}{hip}\$"
    }

    fun tan(): String {
        return "\$\\tan \\theta = \\frac{opp}{adj}\$"
    }

    fun csc(): String {
        return "\$\\csc \\theta = \\frac{1}{\\sin \\theta}\$"
    }

    fun sec(): String {
        return "\$\\sec \\theta = \\frac{1}{\\cos \\theta}\$"
    }

    fun cot(): String {
        return "\$\\cot \\theta = \\frac{1}{\\tan \\theta}\$"
    }

    fun pythagoreanIdentitySinCos(): String {
        return "\$\\sin^2 \\theta + \\cos^2 \\theta = 1\$"
    }

    fun pythagoreanIdentityTanSec(): String {
        return "\$\\tan^2 \\theta + 1 = \\sec^2 \\theta\$"
    }

    fun pythagoreanIdentityCscCot(): String {
        return "\$\\cot^2 \\theta + 1 = \\csc^2 \\theta\$"
    }

    fun tanIdentity(): String {
        return "\$\\tan \\theta =  \\frac{\\sin \\theta}{\\cos \\theta}\$"
    }

    fun lawOfSines(): String {
        return "\$\\frac{\\sin A}{a} = \\frac{\\sin B}{b} = \\frac{\\sin C}{c}\$"
    }

    fun lawOfCosines(): String {
        return "\$a^2 = b^2 + c^2 - 2bc \\cos A\$"
    }

    fun heronFormulaArea(): String {
        return "\$A = \\sqrt{s\\left( s-a\\right)\\left( s-b\\right)\\left( s-c\\right) }\$"
    }

    fun heronFormulaSide(): String {
        return "\$s = \\frac{a + b + c}{2}\$"
    }
}

class Statistics {
    fun mean(): String {
        return "\\bar{x} = \\frac{\\sum_{i=1}^{N} x_i}{N} "
    }

    fun medianOdd(): String {
        return "\$Me = x_{k}, k = \\frac{N + 1}{2} \$"
    }

    fun medianEven(): String {
        return "\$Me = \\frac{x_{k} + x_{k + 1}}{2}, k = \\frac{N}{2} \$"
    }

    fun variance(): String {
        return "\$\\sigma^2 = \\frac{1}{N} \\sum_{i=1}^{N} (x_i - \\mu)^2\$"
    }

    fun standardDeviation(): String {
        return "\$\\sigma = \\sqrt{\\frac{1}{N} \\sum_{i=1}^{N} (x_i - \\mu)^2}\$"
    }

}

class Derivatives {
    fun slopeOfSecantLine(): String {
        return "\$SSL = \\frac{f(b) - f(a)}{b - a}\$"
    }

    fun rateOfChange(): String {
        return "\$f'\\left( x_0\\right) = \\lim_{h \\to 0} \\frac{f\\left( x_0 + h\\right) - f\\left( x_0\\right) }{h}\$"
    }

    fun constant(): String {
        return "\$C' = 0\$"
    }

    fun constantMultiplication(): String {
        return "\$Cu = C\$"
    }

    fun powerRule(): String {
        return "\$\\left( u^n\\right)' = nu^{n-1}\$"
    }

    fun exponential(): String {
        return "\$\\left( C^u\\right)' = u' \\times C^u \\times \\ln C \$"
    }

    fun eulerExponential(): String {
        return "\$\\left( e^u\\right) ' = u' \\times e^u \$"
    }

    fun sumRule(): String {
        return "\$\\left( u + v\\right) ' = u' + v'\$"
    }

    fun productRule(): String {
        return "\$\\left( u \\times v\\right) ' = u'v + uv'\$"
    }

    fun quotientRule(): String {
        return "\$\\left( \\frac{u}{v}\\right) ' = \\frac{u'v - uv'}{v^2}\$"
    }

    fun chainRule(): String {
        return "\$f\\left( g\\left( x\\right) \\right)' = f'\\left( g\\left( x\\right) \\right) \\times g'\\left( x\\right) \$"
    }

    fun sin(): String {
        return "\$\\left( \\sin u\\right)' = u' \\cos u\$"
    }

    fun cos(): String {
        return "\$\\left( \\cos u\\right)' = -u' \\sin u\$"
    }

    fun tan(): String {
        return "\$\\left( \\tan u\\right)' = u' \\sec ^2 u\$"
    }

    fun log(): String {
        return "\$\\left( \\log _{a}u\\right)' = \\frac{u'}{u \\times \\ln a} \$"
    }

    fun naturalLog(): String {
        return "\$\\left( \\ln u\\right)' = \\frac{u'}{u} \$"
    }
}

class Logarithms {
    fun logOfOneProperty(): String {
        return "\$\\log _{a} 1 = 0\$"
    }

    fun logInverseProperty(): String {
        return "\$\\log _{a} a^b = b\$"
    }

    fun product(): String {
        return "\$\\log _{a} \\left( u \\times v\\right) = \\log _{a}u + \\log _{a}v\$"
    }

    fun quotient(): String {
        return "\$\\log _{a} \\left( \\frac{u}{v} \\right) = \\log _{a}u - \\log _{a}v\$"
    }

    fun exponential(): String {
        return "\$\\log _{a} u^v = v \\times \\log _{a}u\$"
    }

    fun changeOfBase(): String {
        return "\$\\log _{a}u = \\frac{\\log _{b}u}{\\log _{b}a} \$"
    }
}

class Integrals {
    fun integralOfOne(): String {
        return "\$\\int 1 \\, dx = x + C, C \\in \\mathbb{R} \$"
    }

    fun log(): String {
        return "\$\\int \\frac{f'\\left( x\\right) }{f\\left( x\\right) }\\, dx = \\ln \\left| f\\left( x\\right) \\right| + C, C \\in \\mathbb{R} \$"
    }

    fun eulerExponential(): String {
        return "\$\\int e^u\\left( x\\right) u'\\left( x\\right)\\, dx = e^u\\left( x\\right) + C, C \\in \\mathbb{R} \$"
    }

    fun sin(): String {
        return "\$\\int \\sin \\left( u\\left( x\\right) \\right) u'\\left( x\\right)\\, dx = -\\cos \\left( u\\left( x\\right) \\right) + C, C \\in \\mathbb{R} \$"
    }

    fun cos(): String {
        return "\$\\int \\cos \\left( u\\left( x\\right) \\right) u'\\left( x\\right)\\, dx = \\sin \\left( u\\left( x\\right) \\right) + C, C \\in \\mathbb{R} \$"
    }

    fun linearityAddition(): String {
        return "\$\\int f\\left( x\\right) + g\\left( x\\right)\\, dx = \\int f\\left( x\\right)\\, dx + \\int g\\left( x\\right)\\, dx \$"
    }

    fun linearityConstantMultiplication(): String {
        return "\$\\int kf\\left( x\\right)\\, dx = k\\int f\\left( x\\right)\\, dx \$"
    }

    fun integrationByParts(): String {
        return "\$\\int u\\, dv = uv - \\int v \\, du\$"
    }

    fun integralReversalProperty(): String {
        return "\$\\int _{a}^{b} f\\left( x\\right) \\, dx = -\\int _{b}^{a} f\\left( x\\right) \\, dx \$"
    }

    fun integralZeroProperty(): String {
        return "\$\\int _{a}^{a} f\\left( x\\right) \\, dx = 0\$"
    }

    fun integralAdditiveProperty(): String {
        return "\$\\int _{a}^{b} f\\left( x\\right) \\, dx = \\int _{a}^{c} f\\left( x\\right) \\, dx + \\int _{c}^{b} f\\left( x\\right) \\, dx \$"
    }

    fun barrowRule(): String {
        return "\$\\int _{a}^{b} f\\left( x\\right) \\, dx = F\\left( b\\right) - F\\left( a\\right) \$"
    }
}