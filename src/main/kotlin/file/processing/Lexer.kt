package org.analyzer.file.processing

import org.analyzer.utils.*
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
        isMatchingDo: Boolean = false
    ): Pair<List<AbsExpression>, Int> {
        var i = index
        val body = mutableListOf<AbsExpression>()
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
                    body.add(func)
                }
                // if matching
                line.contains(Regex("if.*\\(.*\\)")) -> {
                    var (ifStmt, j) = constructIfStructure(fileStream, i)
                    i = --j
                    body.add(ifStmt)
                    ifElseStmt.push("if")
                }
                // else matching
                line.contains(Regex(".*else.*")) -> {
                    if (ifElseStmt.isEmpty())
                        return Pair(body, i) // make sure to not add else to if body
                    var (elseStmt, j) = constructElseStructure(fileStream, i)
                    i = --j
                    body.add(elseStmt)
                }
                // while matching
                line.contains(Regex("while.*\\(.*\\)")) && !isMatchingDo -> {
                    var (whileStmt, j) = constructWhileStructure(fileStream, i)
                    i = --j
                    body.add(whileStmt)
                }
                // for matching
                line.contains(Regex("for.*\\(.*\\)")) -> {
                    var (forStmt, j) = constructForStructure(fileStream, i)
                    i = --j
                    body.add(forStmt)
                }
                // do match
                line.contains(Regex(" do ")) -> {
                    var (doStmt, j) = constructDoExpression(fileStream, i)
                    i = --j
                    body.add(doStmt)
                }

                line.contains('{') -> brackets.add('{')
                line.contains('}') -> brackets.pop()

                else -> {
                    body.add(SimpleExpression())
                }
            }
            ++i
        }
        return Pair(body, i)
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

    private fun constructForStructure(fileStream: List<String>, index: Int): Pair<ForExpression, Int> {
        val (forBody, i) = constructBodyStructure(fileStream, index)
        return Pair(ForExpression(forBody), i)
    }

    private fun constructDoExpression(fileStream: List<String>, index: Int): Pair<DoWhileExpression, Int> {
        var (doBody, i) = constructBodyStructure(fileStream, index, true)
        if (!fileStream[i - 1].contains(Regex("while.*\\(.*\\)")))
            throw IllegalStateException("while keyword expected at line ${i + 1}: ${fileStream[i]}.")
        val brackets = ArrayDeque<Char>()
        while (i < fileStream.size && brackets.isNotEmpty()) {
            for (symbol in fileStream[i]) {
                if (symbol == '(')
                    brackets.push(symbol)
                if (symbol == ')')
                    brackets.pop()
            }
            i++
        }
        return Pair(DoWhileExpression(doBody), i)
    }
}
