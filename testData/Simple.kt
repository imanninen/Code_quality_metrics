
class Aboba {
    fun firstFunction(x: Int): Boolean {
        if (x > 0) {
            return true
        } else {
            return false
        }
        if (x < 0) {
            val a = 2
            a++
            return false
        }
    }

    fun secondFunction(): Int {
        var y = 10
        while (y > 0) {
            if (y == 2) {
                println("Yes!")
            }
            y--
        }
        return y;
    }
}