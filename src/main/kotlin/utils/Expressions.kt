package org.analizer.utils

import java.util.Collections.max

interface AbsExpression {
    val body: List<AbsExpression>?
    val nestedDepth: Int
}

// this expression is used to mark all kinds of expressions, that haven't got a nested block of code.
class SimpleExpression : AbsExpression {
    override val body: List<AbsExpression>?
        get() = null
    override val nestedDepth: Int
        get() = 0
}

class FileExpression(val functions: List<FunctionExpression>)

class FunctionExpression(val name: String, override val body: List<AbsExpression>?) : AbsExpression {
    override val nestedDepth: Int
        get() = if (!body.isNullOrEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class IfExpression(override val body: List<AbsExpression>) : AbsExpression {
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class ElseExpression(override val body: List<AbsExpression>) : AbsExpression {
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class WhenExpression(
    override val body: List<AbsExpression>?,
    val branches: List<AbsExpression?>,
    val elseBody: List<AbsExpression>? = null
) : AbsExpression {
    override val nestedDepth: Int
        get() = if (! body.isNullOrEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class WhileExpression(override val body: List<AbsExpression>) : AbsExpression {
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class ForExpression(override val body: List<AbsExpression>) : AbsExpression {
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class DoWhileExpression(override val body: List<AbsExpression>) : AbsExpression {
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}
