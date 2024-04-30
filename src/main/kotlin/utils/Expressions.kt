package org.analizer.utils

interface AbsExpression {
    val body: List<AbsExpression>?
}

// this expression is used to mark all kinds of expressions, that haven't got a nested block of code.
class SimpleExpression : AbsExpression {
    override val body: List<AbsExpression>?
        get() = null
}

class FileExpression(val functions: List<FunctionExpression>)

class FunctionExpression(val name: String, override val body: List<AbsExpression>?) : AbsExpression

class IfExpression(override val body: List<AbsExpression>) : AbsExpression

class ElseExpression(override val body: List<AbsExpression>) : AbsExpression

class WhenExpression(
    override val body: List<AbsExpression>?,
    val branches: List<AbsExpression?>,
    val elseBody: List<AbsExpression>? = null
) : AbsExpression

class WhileExpression(override val body: List<AbsExpression>?) : AbsExpression

class ForExpression(override val body: List<AbsExpression>?) : AbsExpression

class DoExpression(override val body: List<AbsExpression>?, val whileExpr: WhileExpression) : AbsExpression
