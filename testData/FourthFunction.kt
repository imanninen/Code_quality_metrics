fun fourthfunction1(a: Int) {
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