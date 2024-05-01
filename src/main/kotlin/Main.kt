package org.analizer

import org.analizer.app.CodeAnalyzer
import java.io.File


internal fun main() {
    val analyzer = CodeAnalyzer(File("testData"))
    analyzer.analyzeCode("Simple.kt")

}