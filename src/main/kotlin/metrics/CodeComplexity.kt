package org.analizer.metrics

import org.analizer.file.code.FileCodeRepresentation
import org.analizer.utils.AbsExpression
import org.analizer.utils.ElseExpression
import org.analizer.utils.SimpleExpression

class CodeComplexity : CodeAnalyzeMetric {
    override fun apply(code: FileCodeRepresentation): String {
        require(code.codeAsExpressions != null) { "CodeComplexity requires code structure!" }
        val response = StringBuilder()
        val funcToComplexity = code.codeAsExpressions.functions.associate { it.name to countComplexity(it) }
        val top3complexFunctions = funcToComplexity.toList().sortedByDescending { it.second }.subList(0, 3)
        top3complexFunctions.forEach { response.append("${it.first} - ${it.second}\n") }
        return response.toString()
    }

    private fun countComplexity(expression: AbsExpression): Int {
        if (expression is SimpleExpression) {
            return 0
        }
        var complexity = 0
        for (element in expression.body!!) {
            complexity += countComplexity(element) + if (element is SimpleExpression || element is ElseExpression) 0 else 1
        }
        return complexity
    }
}