package org.analizer.metrics

import org.analizer.file.code.FileCodeRepresentation

interface CodeAnalyzeMetric {
    fun apply(code: FileCodeRepresentation): String
}