import org.analyzer.file.processing.Lexer
import org.analyzer.utils.*
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTests {

    private fun firstFunction(): FunctionExpression {
        val name = "firstFunction"
        val body: List<AbsExpression> = listOf(
            IfExpression(listOf(SimpleExpression())),
            ElseExpression(listOf(SimpleExpression())),
            IfExpression(listOf(SimpleExpression(), SimpleExpression(), SimpleExpression()))
        )
        return FunctionExpression(name, body)
    }

    private fun secondFunction(): FunctionExpression {
        val name = "secondFunction"
        val body: List<AbsExpression> = listOf(
            SimpleExpression(),
            WhileExpression(
                listOf(
                    IfExpression(listOf(SimpleExpression(), ForExpression(listOf(SimpleExpression())))),
                    SimpleExpression()
                )
            ),
            SimpleExpression()
        )
        return FunctionExpression(name, body)
    }

    private fun thirdFunction(): FunctionExpression {
        val name = "thirdFunction"
        val body: List<AbsExpression> = listOf(
            DoWhileExpression(
                listOf(SimpleExpression(), IfExpression(listOf(SimpleExpression())))
            ),
            WhileExpression(listOf(SimpleExpression()))
        )
        return FunctionExpression(name, body)
    }

    private fun fourthFunction(): FunctionExpression {
        val name = "fourthfunction1"
        val body: List<AbsExpression> = listOf(
            SimpleExpression(),
            WhenExpression(listOf(SimpleExpression(), SimpleExpression())),
            ForExpression(listOf(SimpleExpression())),
            WhenExpression(listOf(IfExpression(
                listOf(IfExpression(listOf(SimpleExpression())))
            )))
        )
        return FunctionExpression(name, body)
    }

    @Test
    fun firstFunctionTest() {
        val expected = FileExpression(listOf(firstFunction()))
        val actual = Lexer().constructFileStructure(File("testData/FirstFunction.kt").readLines())
        assert(expected == actual) { "Incorrect file structure" }
    }

    @Test
    fun secondFunctionTest() {
        val expected = FileExpression(listOf(secondFunction()))
        val actual = Lexer().constructFileStructure(File("testData/SecondFunction.kt").readLines())
        assert(expected == actual) { "Incorrect file structure" }
    }
    @Test
    fun thirdFunctionTest() {
        val expected = FileExpression(listOf(thirdFunction()))
        val actual = Lexer().constructFileStructure(File("testData/ThirdFunction.kt").readLines())
        assertEquals(expected, actual, "Incorrect file structure")
    }
    @Test
    fun fourthFunctionTest() {
        val expected = FileExpression(listOf(fourthFunction()))
        val actual = Lexer().constructFileStructure(File("testData/FourthFunction.kt").readLines())
        assertEquals(expected, actual, "Incorrect file structure.")
    }
}