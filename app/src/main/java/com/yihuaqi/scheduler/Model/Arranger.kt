package com.yihuaqi.scheduler.Model

import android.util.Log

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger {

    val TAG = "Arranger"

    fun calculate(workDay: WorkDay): List<Arrangement> {

        Log.d(TAG, "Start calculating $workDay =============")

        Log.d(TAG, "Group A Order: ")
        Staff.getShuffledGroupAOrder(workDay).forEach {
            Log.d(TAG, it.toString())
        }

        Log.d(TAG, "Group B Order: ")
        Staff.getShuffledGroupBOrder(workDay).forEach {
            Log.d(TAG, it.toString())
        }

        Log.d(TAG, "Backup Order: ")
        Staff.getShuffledBackupOrder(workDay).forEach {
            Log.d(TAG, it.toString())
        }

        val result = step1(workDay)

//        val groupAStaff = Staff.shuffledGroupAOrder.nextAvailableStaff(Shift.HUI_ZHEN, workDay)
//
//        swapArrangement(result, Arrangement(groupAStaff, Shift.HUI_ZHEN, workDay), nextAvailableStaff)

        val backupStaffs = Staff.getShuffledBackupOrder(workDay).toMutableList()
        val backup = { arrangements: List<Arrangement>, shift: Shift, workDay: WorkDay ->
            val staff = backupStaffs.find { backupStaff ->
                backupStaff.isAvailable(workDay, shift)
                        && arrangements.find { it.staff == backupStaff }?.shift?.isCT() ?: false
            }
            backupStaffs.remove(staff)
            staff
        }

        step2(result, workDay, backup)

        step3(result, workDay, backup)

        return result
    }

    fun step2(arrangements: MutableList<Arrangement>, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        Log.d(TAG, "stepB: assign HUI_ZHENG")
        val before = fillInArrangement(arrangements, Arrangement(Staff.getShuffledGroupAOrder(workDay).nextAvailableStaff(Shift.HUI_ZHEN, workDay), Shift.HUI_ZHEN, workDay))
        before?.let {
            if (it.shift == Shift.MR_2 && workDay == WorkDay.Tuesday) {
                arrangements.add(Arrangement(Staff.SUN, it.shift, workDay))
            } else {
                arrangements.add(Arrangement(backup(arrangements, it.shift, it.workDay), it.shift, workDay))
            }
        }
        Log.d(TAG, "stepB finished")
    }

    fun fillInArrangement(arrangements: MutableList<Arrangement>, arrangement: Arrangement): Arrangement? {
        Log.d(TAG, "fillInArrangement start")
        val prevArrangement = removeArrangementForStaff(arrangements, arrangement.staff!!)
        arrangements.add(arrangement)
        Log.d(TAG, "add $arrangement")
        Log.d(TAG, "fillInArrangement end")
        return prevArrangement
    }

    fun removeArrangementForStaff(arrangements: MutableList<Arrangement>, staff: Staff): Arrangement? {
        val prevArrangement = arrangements.find { it.staff == staff }
        arrangements.remove(prevArrangement)
        Log.d(TAG, "remove $prevArrangement")
        return prevArrangement
    }


    private fun step1(workDay: WorkDay): MutableList<Arrangement> {
        Log.d(TAG, "step1: assign groupB")
        return Shift.groupB().mapIndexed { index, shift ->
            val a = Arrangement(Staff.getShuffledGroupBOrder(workDay)[index], shift, workDay)
            Log.d(TAG, "step1: $a")
            a
        }.toMutableList()
    }

    private fun removeArrangement(arrangements: MutableList<Arrangement>, staff: Staff, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        val prevArrangement = arrangements.find { it.staff == staff }
        arrangements.remove(prevArrangement)
        Log.d(TAG, "removeArrangement remove $prevArrangement because of $staff and $workDay")
        prevArrangement?.shift?.let {
            if (it.mustAvailable) {
                val nextBackup = backup(arrangements, it, workDay)
                val a = Arrangement(nextBackup, it, workDay)
                arrangements.add(a)
                Log.d(TAG, "removeArrangement add $a because mustAvailable")
            }
        }
    }

    private fun step3(arrangements: MutableList<Arrangement>, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        Log.d(TAG, "Step 3 start: ")
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
        Log.d(TAG, "Step 3 end: ")
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
        calculate(WorkDay.Monday).forEach { Log.d(TAG, it.toString()) }
    }

}