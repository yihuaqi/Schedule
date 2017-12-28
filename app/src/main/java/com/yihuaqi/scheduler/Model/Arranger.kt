package com.yihuaqi.scheduler.Model

import android.util.Log

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger(val shifts: List<Shift>, val goodShiftOrder: MutableList<Staff>, val badShiftOrder: MutableList<Staff>) {
    fun calculate(): List<Arrangement> {
        return shifts.map {
            when (it) {
                is GoodShift -> {
                    it.makeArrangement(goodShiftOrder)
                }
                is BadShift -> {
                    it.makeArrangement(badShiftOrder)
                }
            }
        }
    }

    fun test() {
        calculate().forEach { Log.d("Arranger", it.toString()) }
    }

}