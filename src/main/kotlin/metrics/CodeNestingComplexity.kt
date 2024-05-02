package org.analyzer.metrics

import org.analyzer.file.code.FileCodeRepresentation

class CodeNestingComplexity : CodeAnalyzeMetric {
    private val nestingLimit: Int
        get() = 3

    override fun apply(code: FileCodeRepresentation): String {
        require(code.codeAsExpressions != null) { "CodeNestingComplexity requires code structure!" }
        val response = StringBuilder()
        response.append("---Code nesting complexity metric---\n")
        for (function in code.codeAsExpressions.functions) {
            if (function.nestedDepth - 1 >= nestingLimit) {
                response.append("${function.name} has to many nested blocks (${function.nestedDepth - 1}).\n")
            }
        }
        if (response.isEmpty()) {
            response.append("Functions passed Nesting analyze.\n")
        }
        response.append("------------------------------------\n")
        return response.toString()
    }
}