package org.analyzer.metrics

import org.analyzer.file.code.FileCodeRepresentation

class CodeStyle : CodeAnalyzeMetric {
    override fun apply(code: FileCodeRepresentation): String {
        require(code.codeAsExpressions != null) { "CodeStyle requires code structure!" }
        val response = StringBuilder()
        for (function in code.codeAsExpressions.functions) {
            if (!checkFuncNamesByCamelCase(function.name)) {
                response.append("${function.name} is not a CamelCase pattern name!")
            }
        }
        if (response.isEmpty()) {
            response.append("All functions passed CamelCase names test.\n")
        }
        return response.toString()
    }

    private fun checkFuncNamesByCamelCase(name: String): Boolean {
        val pat = Regex("[a-z][A-Za-z0-9]*")
        return pat.matches(name)
    }
}