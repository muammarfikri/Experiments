package com.example.mf.experiments

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
    @Test
    fun obs(){
        Observable.just("2","1")
            .subscribeOn(Schedulers.computation())
            .flatMap {
                Thread.sleep(Random.nextInt()%3 * 100L)
                Observable.just(it).subscribeOn(Schedulers.io())
            }
            .observeOn(Schedulers.single())
            .subscribe{
                println("$it ${Thread.currentThread().name}")
            }
        Thread.sleep(2000)
    }
}