package com.yihuaqi.scheduler.Model

import android.util.Log

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger(val shifts: List<Shift>, val shiftOrder: MutableList<Staff>) {
    fun calculate(): List<Arrangement> {
        return shifts.map {
            it.makeArrangement(shiftOrder)
        }
    }

    fun test() {
        calculate().forEach { Log.d("Arranger", it.toString()) }
    }

}