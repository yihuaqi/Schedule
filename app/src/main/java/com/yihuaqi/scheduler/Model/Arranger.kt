package com.yihuaqi.scheduler.Model

import android.util.Log

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arranger {

    companion object {
        val TAG = "Arranger"
    }

    val backupStaffManager: BackupStaffManager = BackupStaffManager()

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

        backupStaffManager.setStartIndex(CoreData.backupIndex)


        if (workDay == WorkDay.Tuesday) {
            Log.d(TAG, "step1_5: assign SUN")
            val staff = result.find { it.shift == Shift.MR_2 }?.staff
            staff?.let {
                val arrangement = removeArrangementForStaff(result, staff = staff)
                backupStaffManager.setPriority(arrangement?.staff)
            }
            fillInArrangement(result, Arrangement(Staff.SUN, Shift.MR_2, workDay))
        }

        val backup = { arrangements: List<Arrangement>, shift: Shift, workDay: WorkDay ->
            backupStaffManager.pop(arrangements, shift, workDay)
        }

        step2(result, workDay, backup)

        step3(result, workDay, backup)

        step4(result, workDay)

        Log.d(TAG, "Result: ")
        result.forEach {
            Log.d(TAG, it.toString())
        }

        return result
    }

    private fun step1(workDay: WorkDay): MutableList<Arrangement> {
        Log.d(TAG, "step1: assign groupB")
        return Shift.groupB().mapIndexed { index, shift ->
            val a = Arrangement(Staff.getShuffledGroupBOrder(workDay)[index], shift, workDay)
            Log.d(TAG, "step1: $a")
            a
        }.toMutableList()
    }

    fun step2(arrangements: MutableList<Arrangement>, workDay: WorkDay, backup: (List<Arrangement>, Shift, WorkDay) -> Staff?) {
        Log.d(TAG, "step2: assign HUI_ZHENG")
        val before = fillInArrangement(arrangements, Arrangement(Staff.getShuffledGroupAOrder(workDay).nextAvailableStaff(Shift.HUI_ZHEN, workDay), Shift.HUI_ZHEN, workDay))
        before?.let {
            if (it.shift == Shift.MR_2 && workDay == WorkDay.Tuesday) {
                arrangements.add(Arrangement(Staff.SUN, it.shift, workDay))
            } else if (!it.shift.isCT()) {
                val backupStaff = backup(arrangements, before.shift, workDay)!!
                val prev =fillInArrangement(arrangements, Arrangement(backupStaff, before.shift, workDay))
                prev?.staff?.let {
                    removeArrangementForStaff(arrangements, it)
                }
            } else {

            }
        }
        arrangements.forEach {
            Log.d(TAG, it.toString())
        }
        Log.d(TAG, "step2 finished")
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
                val prev = removeArrangementForStaff(arrangements, Staff.TANG)
                prev?.let {
                    val backStaff = backup(arrangements, prev!!.shift, workDay)
                    fillInArrangement(arrangements, Arrangement(backStaff, it.shift, workDay))
                }
            }
        }
        arrangements.forEach {
            Log.d(TAG, it.toString())
        }
        Log.d(TAG, "Step 3 end: ")
    }


    fun step4(arrangements: MutableList<Arrangement>, workDay: WorkDay) {
        Shift.ALL.forEach { shift ->
            if (arrangements.find { it.shift == shift } == null) {
                arrangements.add(Arrangement(null, shift, workDay))
            }
        }
    }

    fun fillInArrangement(arrangements: MutableList<Arrangement>, arrangement: Arrangement): Arrangement? {
        Log.d(TAG, "fillInArrangement start")
        if (arrangement.staff!!.forceEmpty(arrangement.shift, arrangement.workDay)) {
            return null
        }
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