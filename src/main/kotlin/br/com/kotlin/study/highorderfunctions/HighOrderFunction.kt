package br.com.kotlin.study.highorderfunctions

/**
 * References:
 *  https://www.geeksforgeeks.org/kotlin-higher-order-functions/#:~:text=In%20Kotlin%2C%20a%20function%20which,Kotlin%20functions%20for%20the%20convenience.
 */
class HighOrderFunction {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val lambdaHello : (name: String) -> String = { name: String ->  "Hello, $name"}
            val lambdaHello2 = { name: String ->  "Hello, $name"}

            println(lambdaHello("Augusto"))
            println(lambdaHello2("Augusto"))
        }
    }
}