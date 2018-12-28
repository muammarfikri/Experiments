package com.example.mf.experiments

import org.junit.Test
import kotlin.random.Random

class Tony{
     var InnerTony = 23
}
class MainActivityTest{
    fun tname () : String = Thread.currentThread().name
    private val random = java.util.Random()

    @Test
    fun apply(){
        val top = 10
        val currentTop = 5
        val calculatedTop = currentTop + (top - currentTop)
        println(calculatedTop/2)
        println((currentTop + (top - currentTop)/2 ))

    }

    @Test
    fun `multithreading test`(){
        val list = arrayListOf<Int>()
        for(i in 1..10){
            list.add(random.nextInt(500))
        }
        println(list)
}
}