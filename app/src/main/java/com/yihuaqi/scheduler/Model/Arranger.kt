package com.yihuaqi.scheduler.Model

import android.util.Log
import java.util.*

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger() {
    fun calculate(): List<Arrangement> {
        val result = LinkedList<Arrangement>()
        val groupB = step1()
        val groupAStaff = Staff.groupAOrder()[0]
        val prevGroupAArrangement = groupB.find { it.staff == groupAStaff }
        groupB.remove(prevGroupAArrangement)
        val groupA = Arrangement(groupAStaff, Shift.groupA())
        result.add(groupA)
        result.addAll(groupB)
        prevGroupAArrangement?.shift?.let {
            if (it.mustAvailable) {
                result.add(Arrangement(Staff.groupAOrder()[1], prevGroupAArrangement.shift))
            }
        }
        return result
    }

    private fun step1(): MutableList<Arrangement> {
        return Shift.groupB().mapIndexed { index, shift ->
            Arrangement(Staff.groupBOrder()[index], shift)
        }.toMutableList()
    }

    fun test() {
        calculate().forEach { Log.d("Arranger", it.toString()) }
    }

}