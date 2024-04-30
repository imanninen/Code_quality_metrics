package org.analizer

import org.analizer.app.CodeAnalyzer
import java.io.File
import java.util.Comparator

fun main() {
    val analyzer = CodeAnalyzer(File("testData"))
    analyzer.analyzeCode("Simple.kt")

}