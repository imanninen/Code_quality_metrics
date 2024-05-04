package org.analyzer.utils

import java.util.Collections.max

interface AbsExpression {
    val body: List<AbsExpression>?
    val nestedDepth: Int
}

// this expression is used to mark all kinds of expressions, that haven't got a nested block of code.
class SimpleExpression : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is SimpleExpression) return true
        return false
    }
    override val body: List<AbsExpression>?
        get() = null
    override val nestedDepth: Int
        get() = 0
}

class FileExpression(val functions: List<FunctionExpression>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is FileExpression) {
            return functions == other.functions
        }
        return false
    }
}

class FunctionExpression(val name: String, override val body: List<AbsExpression>?) : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is FunctionExpression) {
            if (body == null) {
                return name == other.name && other.body == null
            }
            if (other.body == null) return false
            return name == other.name && body.zip(other.body).all { (a, b) -> a == b }
        }
        return false
    }
    override val nestedDepth: Int
        get() = if (!body.isNullOrEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class IfExpression(override val body: List<AbsExpression>) : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is IfExpression) {
            return body.zip(other.body).all { (a, b) -> a == b }
        }
        return false
    }
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class ElseExpression(override val body: List<AbsExpression>) : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is ElseExpression) {
            return body.zip(other.body).all { (a, b) -> a == b }
        }
        return false
    }
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class WhenExpression(
    override val body: List<AbsExpression>,
) : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is WhenExpression) {
            return body.zip(other.body).all { (a, b) -> a == b }
        }
        return false
    }
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class WhileExpression(override val body: List<AbsExpression>) : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is WhileExpression) {
            return body.zip(other.body).all { (a, b) -> a == b }
        }
        return false
    }
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class ForExpression(override val body: List<AbsExpression>) : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is ForExpression) {
            return body.zip(other.body).all { (a, b) -> a == b }
        }
        return false
    }
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}

class DoWhileExpression(override val body: List<AbsExpression>) : AbsExpression {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is DoWhileExpression) {
            return body.zip(other.body).all { (a, b) -> a == b }
        }
        return false
    }
    override val nestedDepth: Int
        get() = if (body.isNotEmpty()) max(body.map { it.nestedDepth }) + 1 else 1
}
