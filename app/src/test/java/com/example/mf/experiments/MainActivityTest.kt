package com.example.mf.experiments

import org.junit.Test
import kotlin.random.Random


class MainActivityTest{
    fun tname () : String = Thread.currentThread().name
    private val random = java.util.Random()

    @Test
    fun `multithreading test`(){
        val list = arrayListOf<Int>()
        for(i in 1..10){
            list.add(random.nextInt(500))
        }
        println(list)
}
}