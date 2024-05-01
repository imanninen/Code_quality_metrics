package org.analyzer.metrics

enum class AllCodeAnalyzeMetrics(val metric: CodeAnalyzeMetric) {
    CODE_COMPLEXITY(CodeComplexity()),
    CODE_STYLE(CodeStyle()),
    CODE_NESTING_COMPLEXITY(CodeNestingComplexity()),
}