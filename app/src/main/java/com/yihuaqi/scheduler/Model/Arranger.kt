package com.yihuaqi.scheduler.Model

import android.util.Log

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger {
    fun calculate(workDay: WorkDay): List<Arrangement> {

        val result = step1(workDay)

        val groupAStaff = Staff.shuffledGroupAOrder.nextAvailableStaff(Shift.HUI_ZHEN, workDay)

        swapArrangement(result, Arrangement(groupAStaff, Shift.HUI_ZHEN, workDay), nextAvailableStaff)

        forceArrangement(result, workDay)

        return result
    }

    val nextAvailableStaff = { shift: Shift, workday: WorkDay ->
        Staff.shuffledBackupOrder.nextAvailableStaff(shift, workday)

    }

    private fun step1(workDay: WorkDay): MutableList<Arrangement> {
        return Shift.groupB().mapIndexed { index, shift ->
            val a = Arrangement(Staff.shuffledGroupBOrder[index], shift, workDay)
            Log.d("Arranger", "step1: $a")
            a
        }.toMutableList()
    }

    private fun swapArrangement(arrangements: MutableList<Arrangement>, swapArrangement: Arrangement, backup: (Shift, WorkDay) -> Staff?) {
        val prevArrangement = arrangements.find { it.staff == swapArrangement.staff }
        arrangements.remove(prevArrangement)
        Log.d("Arranger", "swapArrangement remove $prevArrangement")
        arrangements.add(swapArrangement)
        Log.d("Arranger", "swapArrangement add $swapArrangement")
        if (swapArrangement.workDay != WorkDay.Tuesday) {
            prevArrangement?.shift?.let {
                if (it.mustAvailable) {
                    val nextBackup = backup(it, swapArrangement.workDay)
                    val a = Arrangement(nextBackup, it, swapArrangement.workDay)
                    arrangements.add(a)
                    Log.d("Arranger", "swapArrangement add $a because mustAvailable")
                }
            }
        } else {
            val prevSunArrangement = arrangements.find { it.shift == Shift.MR_2 }
            arrangements.remove(prevSunArrangement)
            Log.d("Arranger", "swapArrangement remove $prevSunArrangement because of ${swapArrangement.workDay}")
            val a = Arrangement(Staff.SUN, Shift.MR_2, swapArrangement.workDay)
            arrangements.add(a)
            Log.d("Arranger", "swapArrangement add $a because of ${swapArrangement.workDay}")
            prevArrangement?.shift?.let {
                if (it.mustAvailable) {
                    val nextBackup = prevSunArrangement?.staff ?: backup(it, swapArrangement.workDay)
                    val a2 = Arrangement(nextBackup, it, swapArrangement.workDay)
                    arrangements.add(a2)
                    Log.d("Arranger", "swapArrangement add $a2 because of ${swapArrangement.workDay} and mustAvailabe")
                }
            }
        }
    }

    private fun removeArrangement(arrangements: MutableList<Arrangement>, staff: Staff, workDay: WorkDay, backup: (Shift, WorkDay) -> Staff?) {
        val prevArrangement = arrangements.find { it.staff == staff }
        arrangements.remove(prevArrangement)
        Log.d("Arranger", "removeArrangement remove $prevArrangement because of $staff and $workDay")
        prevArrangement?.shift?.let {
            if (it.mustAvailable) {
                val nextBackup = backup(it, workDay)
                val a = Arrangement(nextBackup, it, workDay)
                arrangements.add(a)
                Log.d("Arranger", "removeArrangement add $a because mustAvailable")
            }
        }
    }

    private fun forceArrangement(arrangements: MutableList<Arrangement>, workDay: WorkDay) {
        when(workDay) {
            WorkDay.Monday -> {
                Shift.nextAvailableCT(arrangements, workDay)?.let {
                    swapArrangement(arrangements, Arrangement(Staff.ZHOU, it, workDay), nextAvailableStaff)
                }
            }
            WorkDay.Wendsday -> {
                Shift.nextAvailableCT(arrangements, workDay)?.let {
                    swapArrangement(arrangements, Arrangement(Staff.MAI, it, workDay), nextAvailableStaff)
                }
            }
            WorkDay.Thursday -> {
                removeArrangement(arrangements, Staff.TANG, workDay, nextAvailableStaff)
            }
        }
    }


    fun test() {
        calculate(WorkDay.Tuesday).forEach { Log.d("Arranger", it.toString()) }
    }

}