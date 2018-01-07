package com.yihuaqi.scheduler.Model

import android.util.Log
import java.util.*

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger {
    fun calculate(workDay: WorkDay): List<Arrangement> {
        val result = LinkedList<Arrangement>()

        val groupB = step1(workDay)

        val groupAStaff = Staff.shuffledGroupAOrder.nextAvailableStaff(Shift.HUI_ZHEN, workDay)

        swapArrangment(groupB, Arrangement(groupAStaff, Shift.HUI_ZHEN, workDay), {shift, workday -> Staff.shuffledBackupOrder.nextAvailableStaff(shift, workday)})

        result.addAll(groupB)
//
//        val prevGroupAArrangement = groupB.find { it.staff == groupAStaff }
//        groupB.remove(prevGroupAArrangement)
//        val groupA = Arrangement(groupAStaff, Shift.groupA(), workDay)
//        result.add(groupA)
//        result.addAll(groupB)
//
//        if (workDay != WorkDay.Tuesday) {
//            prevGroupAArrangement?.shift?.let {
//                if (it.mustAvailable) {
//                    val nextBackup = Staff.shuffledBackupOrder.nextAvailableStaff(workDay, it)
//                    result.add(Arrangement(nextBackup, prevGroupAArrangement.shift, workDay))
//                }
//            }
//        } else {
//            val prevSunArrangment = groupB.find { it.shift == Shift.MR_2}
//            result.remove(prevSunArrangment)
//            result.add(Arrangement(Staff.SUN, Shift.MR_2, workDay))
//
//            prevGroupAArrangement?.shift?.let {
//                if (it.mustAvailable) {
//                    val nextBackup = prevSunArrangment?.staff ?: Staff.shuffledBackupOrder.nextAvailableStaff(workDay, it)
//                    result.add(Arrangement(nextBackup, prevGroupAArrangement.shift, workDay))
//                }
//            }
//        }

        return result
    }

    private fun step1(workDay: WorkDay): MutableList<Arrangement> {
        return Shift.groupB().mapIndexed { index, shift ->
            Arrangement(Staff.shuffledGroupBOrder[index], shift, workDay)
        }.toMutableList()
    }

    private fun swapArrangment(arrangements: MutableList<Arrangement>, swapArrangement: Arrangement, backup: (Shift, WorkDay) -> Staff?) {
        val prevArrangement = arrangements.find { it.staff == swapArrangement.staff && it.workDay == swapArrangement.workDay }
        arrangements.remove(prevArrangement)
        arrangements.add(swapArrangement)
        if (swapArrangement.workDay != WorkDay.Tuesday) {
            prevArrangement?.shift?.let {
                if (it.mustAvailable) {
                    val nextBackup = backup(it, swapArrangement.workDay)
                    arrangements.add(Arrangement(nextBackup, it, swapArrangement.workDay))
                }
            }
        } else {
            val prevSunArrangement = arrangements.find { it.shift == Shift.MR_2 }
            arrangements.remove(prevSunArrangement)
            arrangements.add(Arrangement(Staff.SUN, Shift.MR_2, swapArrangement.workDay))
            prevArrangement?.shift?.let {
                if (it.mustAvailable) {
                    val nextBackup = prevSunArrangement?.staff ?: backup(it, swapArrangement.workDay)
                    arrangements.add(Arrangement(nextBackup, it, swapArrangement.workDay))
                }
            }
        }
    }

    fun test() {
        calculate(WorkDay.Tuesday).forEach { Log.d("Arranger", it.toString()) }
    }

}