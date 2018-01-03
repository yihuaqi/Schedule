package com.yihuaqi.scheduler.Model

import android.util.Log
import java.util.*

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger {
    fun calculate(workDay: WorkDay): List<Arrangement> {
        val result = LinkedList<Arrangement>()

        val groupB = step1()

        val groupAStaff = Staff.shuffledGroupAOrder.nextAvailableStaff(workDay, Shift.HUI_ZHEN)

        val prevGroupAArrangement = groupB.find { it.staff == groupAStaff }
        groupB.remove(prevGroupAArrangement)
        val groupA = Arrangement(groupAStaff, Shift.groupA())
        result.add(groupA)
        result.addAll(groupB)

        if (workDay != WorkDay.Tuesday) {
            prevGroupAArrangement?.shift?.let {
                if (it.mustAvailable) {
                    val nextBackup = Staff.shuffledBackupOrder.nextAvailableStaff(workDay, it)
                    result.add(Arrangement(nextBackup, prevGroupAArrangement.shift))
                }
            }
        } else {
            val prevSunArrangment = groupB.find { it.shift == Shift.MR_2}
            result.remove(prevSunArrangment)
            result.add(Arrangement(Staff.SUN, Shift.MR_2))

            prevGroupAArrangement?.shift?.let {
                if (it.mustAvailable) {
                    val nextBackup = prevSunArrangment?.staff ?: Staff.shuffledBackupOrder.nextAvailableStaff(workDay, it)
                    result.add(Arrangement(nextBackup, prevGroupAArrangement.shift))
                }
            }
        }
        return result
    }

    private fun step1(): MutableList<Arrangement> {
        return Shift.groupB().mapIndexed { index, shift ->
            Arrangement(Staff.shuffledGroupBOrder[index], shift)
        }.toMutableList()
    }

    fun test() {
        calculate(WorkDay.Tuesday).forEach { Log.d("Arranger", it.toString()) }
    }

}