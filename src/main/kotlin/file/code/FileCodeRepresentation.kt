package org.analyzer.file.code

import org.analyzer.utils.FileExpression

class FileCodeRepresentation(
    val codeAsStrings: List<String>? = null,
    val codeAsExpressions: FileExpression? = null
)