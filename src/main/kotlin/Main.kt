package org.analizer

import org.analizer.app.CodeAnalyzer
import java.io.File


internal fun main() {
    val analyzer = CodeAnalyzer(File("testData"))
    println(analyzer.analyzeCode("Simple.kt"))

}