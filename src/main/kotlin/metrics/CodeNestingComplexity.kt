package org.analizer.metrics

import org.analizer.file.code.FileCodeRepresentation

class CodeNestingComplexity : CodeAnalyzeMetric {
    private val nestingLimit: Int
        get() = 3

    override fun apply(code: FileCodeRepresentation): String {
        require(code.codeAsExpressions != null) { "CodeComplexity requires code structure!" }
        val response = StringBuilder()
        for (function in code.codeAsExpressions.functions) {
            if (function.nestedDepth - 1 >= nestingLimit) {
                response.append("${function.name} has to many nested blocks (${function.nestedDepth - 1}).")
            }
        }
        if (response.isEmpty()) {
            response.append("Functions passed Nesting analyze.")
        }
        return response.toString()
    }
}