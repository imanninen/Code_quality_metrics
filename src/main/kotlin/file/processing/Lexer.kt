package org.analizer.file.processing

import org.analizer.utils.*
import java.util.*

class Lexer {
    fun constructFileStructure(fileStream: List<String>): FileExpression {
        var i = 0
        val fileBody = mutableListOf<FunctionExpression>()
        while (i < fileStream.size) {
            val line = fileStream[i]
            when {
                line.contains(Regex("fun [a-z]+.*\\(.*\\)")) -> {
                    val (func, index) = constructFunctionStructure(fileStream, i)
                    i = index
                    fileBody.add(func)
                }

                else -> {
                    ++i
                    continue
                }
            }

            ++i
        }
        return FileExpression(fileBody)
    }

    // don't support functional declarations!!!
    // expect you to put as many '{}' as possible.
    // TODO: may be add global indexing!
    private fun constructBodyStructure(
        fileStream: List<String>,
        index: Int,
    ): Pair<List<AbsExpression>, Int> {
        var i = index
        val funcBody = mutableListOf<AbsExpression>()
        val brackets = ArrayDeque<Char>()
        val ifElseStmt = ArrayDeque<String>()

        if (!fileStream[i].contains('{')) {
            throw IllegalStateException("'{' was expected at line: ${fileStream[i]}.")
        }
        if (fileStream[i].count { it == '{' } != 1)
            throw IllegalStateException("To many '{' in line: ${fileStream[i]}.")
        brackets.push('{')
        ++i

        while (i < fileStream.size && brackets.isNotEmpty()) {
            val line = fileStream[i]

            when {
                // function matching
                line.contains(Regex("fun [a-z]+.*\\(.*\\)")) -> {
                    var (func, j) = constructFunctionStructure(fileStream, i)
                    i = --j
                    funcBody.add(func)
                }
                // if matching
                line.contains(Regex("if.*\\(.*\\)")) -> {
                    var (ifStmt, j) = constructIfStructure(fileStream, i)
                    i = --j
                    funcBody.add(ifStmt)
                    ifElseStmt.push("if")
                }
                // else matching
                line.contains(Regex(".*else.*")) -> {
                    if (ifElseStmt.isEmpty())
                        return Pair(funcBody, i) // make sure to not add else to if body
                    var (elseStmt, j) = constructElseStructure(fileStream, i)
                    i = --j
                    funcBody.add(elseStmt)
                }
                // while matching
                line.contains(Regex("while.*\\(.*\\)")) -> {
                    var (whileStmt, j) = constructWhileStructure(fileStream, i)
                    i = --j
                    funcBody.add(whileStmt)
                }


                line.contains('{') -> brackets.add('{')
                line.contains('}') -> brackets.pop()

                else -> {
                    funcBody.add(SimpleExpression())
                }
            }
            ++i
        }
        return Pair(funcBody, i)
    }

    private fun constructFunctionStructure(fileStream: List<String>, index: Int): Pair<FunctionExpression, Int> {
        val funcName = fileStream[index].split("fun ")
            .last()
            .split("(")
            .first()
            .trimIndent()
        val (funcBody, i) = constructBodyStructure(fileStream, index)
        return Pair(FunctionExpression(funcName, funcBody), i)
    }

    private fun constructIfStructure(fileStream: List<String>, index: Int): Pair<IfExpression, Int> {
        val (ifBody, i) = constructBodyStructure(fileStream, index)
        return Pair(IfExpression(ifBody), i)
    }

    private fun constructElseStructure(fileStream: List<String>, index: Int): Pair<ElseExpression, Int> {
        val (elseBody, i) = constructBodyStructure(fileStream, index)
        return Pair(ElseExpression(elseBody), i)
    }

    private fun constructWhileStructure(fileStream: List<String>, index: Int): Pair<WhileExpression, Int> {
        val (whileBody, i) = constructBodyStructure(fileStream, index)
        return Pair(WhileExpression(whileBody), i)
    }
}
