package org.analyzer.app

import org.analyzer.file.code.FileCodeRepresentationFactory
import org.analyzer.file.load.Loader
import org.analyzer.file.processing.Lexer
import org.analyzer.metrics.AllCodeAnalyzeMetrics
import org.analyzer.metrics.CodeAnalyzeMetric
import java.io.File

class CodeAnalyzer(private val workingDirectory: File) {
    private val reader = Loader(workingDirectory)
    private val lexer = Lexer()
    private val codeAnalyzeMetrics: List<CodeAnalyzeMetric> = AllCodeAnalyzeMetrics.entries.map { it.metric }

    fun analyzeCode(fileName: String): String {
        val file = workingDirectory.listFiles()?.find { it.name == fileName }
            ?: error("File not found: $fileName in $workingDirectory.")

        val code = FileCodeRepresentationFactory.generateCodeRepresentation(reader, lexer, file)
        val response = StringBuilder()

        codeAnalyzeMetrics.forEach { response.append(it.apply(code)) }
        return response.toString()
    }

}
