@file:JvmName("MainKt")
package org.analyzer

import org.analyzer.app.CodeAnalyzer
import java.io.File


fun main(args: Array<String>) {
    if (args.size != 2) {
        println("To run this application pass a path to directory and a file to inspect.")
        println("Example: my/dir/ MyFile.kt")
        return
    }
    val analyzer = CodeAnalyzer(File(args[0]))
    println(analyzer.analyzeCode(args[1]))

}