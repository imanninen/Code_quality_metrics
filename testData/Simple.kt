
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
                for (el in aboba) {
                    print(el)
                }
            }
            y--
        }
        return y;
    }

    fun thirdFunction(): Unit {
        do {
            val a = 10
            if (a == 10) {
                println(a)
            }
        } while (true)
        while (false) {
            println("hhhh")
        }
    }

    fun forthfunction1(a: Int) {
        var x = 10
        when {
            x == 9 -> return
            a == x -> return
        }

        for (el in listOf(a)) {
            println(el)
        }

        when (x) {
            10 -> {
                if (a == 10) {
                    if (x == 10) {
                        return
                    }
                }
            }
        }
    }
}