package org.analizer.app

import org.analizer.file.load.Loader
import org.analizer.file.processing.Lexer
import java.io.File

class CodeAnalyzer(private val workingDirectory: File) {
    private val reader = Loader(workingDirectory)
    private val lexer = Lexer()

    fun analyzeCode(fileName: String): String {
        val file = workingDirectory.listFiles()?.find { it.name == fileName }
            ?: error("File not found: $fileName")

        val fileStruct = lexer.constructFileStructure(reader.loadFile(file))
        TODO("Not implemented yet")
        return "aaa"
    }
}