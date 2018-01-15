package com.yihuaqi.scheduler.Model

import android.util.Log

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger {

    fun calculate(workDay: WorkDay): List<Arrangement> {

        val result = step1(workDay)

//        val groupAStaff = Staff.shuffledGroupAOrder.nextAvailableStaff(Shift.HUI_ZHEN, workDay)
//
//        swapArrangement(result, Arrangement(groupAStaff, Shift.HUI_ZHEN, workDay), nextAvailableStaff)

        val backupStaffs = Staff.shuffledBackupOrder.toMutableList()
        val backup = { arrangements: List<Arrangement>, shift: Shift, workDay: WorkDay ->
            val staff = backupStaffs.find { backupStaff ->
                backupStaff.isAvailable(workDay, shift)
                        && arrangements.find { it.staff == backupStaff }!!.shift.isCT()
            }
            backupStaffs.remove(staff)
            staff
        }

        step2(result, workDay, backup)

        step3(result, workDay, backup)

        return result
    }

    fun step2(arrangements: MutableList<Arrangement>, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        Log.d("Arranger", "stepB: assign HUI_ZHENG")
        val before = fillInArrangement(arrangements, Arrangement(Staff.shuffledGroupAOrder.nextAvailableStaff(Shift.HUI_ZHEN, workDay), Shift.HUI_ZHEN, workDay))
        before?.let {
            if (it.shift == Shift.MR_2 && workDay == WorkDay.Tuesday) {
                arrangements.add(Arrangement(Staff.SUN, it.shift, workDay))
            } else {
                arrangements.add(Arrangement(backup(arrangements, it.shift, it.workDay), it.shift, workDay))
            }
        }
        Log.d("Arranger", "stepB finished")
    }

    fun fillInArrangement(arrangements: MutableList<Arrangement>, arrangement: Arrangement): Arrangement? {
        Log.d("Arranger", "fillInArrangement start")
        val prevArrangement = removeArrangementForStaff(arrangements, arrangement.staff!!)
        arrangements.add(arrangement)
        Log.d("Arranger", "add $arrangement")
        Log.d("Arranger", "fillInArrangement end")
        return prevArrangement
    }

    fun removeArrangementForStaff(arrangements: MutableList<Arrangement>, staff: Staff): Arrangement? {
        val prevArrangement = arrangements.find { it.staff == staff }
        arrangements.remove(prevArrangement)
        Log.d("Arranger", "remove $prevArrangement")
        return prevArrangement
    }


    private fun step1(workDay: WorkDay): MutableList<Arrangement> {
        Log.d("Arranger", "step1: assign groupB")
        return Shift.groupB().mapIndexed { index, shift ->
            val a = Arrangement(Staff.shuffledGroupBOrder[index], shift, workDay)
            Log.d("Arranger", "step1: $a")
            a
        }.toMutableList()
    }

    private fun removeArrangement(arrangements: MutableList<Arrangement>, staff: Staff, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        val prevArrangement = arrangements.find { it.staff == staff }
        arrangements.remove(prevArrangement)
        Log.d("Arranger", "removeArrangement remove $prevArrangement because of $staff and $workDay")
        prevArrangement?.shift?.let {
            if (it.mustAvailable) {
                val nextBackup = backup(arrangements, it, workDay)
                val a = Arrangement(nextBackup, it, workDay)
                arrangements.add(a)
                Log.d("Arranger", "removeArrangement add $a because mustAvailable")
            }
        }
    }

    private fun step3(arrangements: MutableList<Arrangement>, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        Log.d("Arranger", "Step 3 start: ")
        when(workDay) {
            WorkDay.Monday -> {
                forceCT(arrangements, Staff.ZHOU, workDay, backup)
            }
            WorkDay.Wendsday -> {
                forceCT(arrangements, Staff.MAI, workDay, backup)
            }
            WorkDay.Thursday -> {
                removeArrangement(arrangements, Staff.TANG, workDay, backup)
            }
        }
        Log.d("Arranger", "Step 3 end: ")
    }

    fun forceCT(arrangements: MutableList<Arrangement>, who: Staff, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        val whoArrangement = arrangements.find { it.staff == who }
        if (!whoArrangement!!.shift.isCT()) {
            val removed = removeArrangementForStaff(arrangements, who)!!
            fillInArrangement(arrangements, Arrangement(backup(arrangements, removed.shift, workDay), removed.shift, workDay))
            fillInArrangement(arrangements, Arrangement(who, Shift.nextAvailableCT(arrangements, workDay)!!, workDay))
        }
    }


    fun test() {
        calculate(WorkDay.Tuesday).forEach { Log.d("Arranger", it.toString()) }
    }

}