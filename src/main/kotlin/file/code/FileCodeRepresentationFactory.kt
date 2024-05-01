package org.analizer.file.code

import org.analizer.file.load.Loader
import org.analizer.file.processing.Lexer
import java.io.File

class FileCodeRepresentationFactory {
    companion object {
        fun generateCodeRepresentation(loader: Loader, lexer: Lexer, file: File): FileCodeRepresentation {
            val fileCodeAsStrings = loader.loadFile(file)
            val fileCodeAsExpressions = lexer.constructFileStructure(fileCodeAsStrings)
            return FileCodeRepresentation(fileCodeAsStrings, fileCodeAsExpressions)
        }
    }
}