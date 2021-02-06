package br.com.kotlin.study.inlinefunctions

class InlineFunction {

    companion object {

        /**
         * references:
         *  https://agrawalsuneet.github.io/blogs/inline-function-kotlin/
         *  https://www.geeksforgeeks.org/kotlin-inline-functions/         *
         */
        private inline fun isMultiple(number: Int, multipleOf: Int): Boolean {
            return number % multipleOf == 0
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val tupleNumbers = arrayListOf<Int>(10, 2)
            tupleNumbers.forEach { println(isMultiple(it, 2)) }
        }
    }
}