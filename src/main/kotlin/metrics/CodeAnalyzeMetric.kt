package org.analyzer.metrics

import org.analyzer.file.code.FileCodeRepresentation

interface CodeAnalyzeMetric {
    fun apply(code: FileCodeRepresentation): String
}