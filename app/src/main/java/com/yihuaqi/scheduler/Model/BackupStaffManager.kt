package com.yihuaqi.scheduler.Model

import android.util.Log

/**
 * Created by yihuaqi on 2/13/18.
 */
class BackupStaffManager {
    companion object {
        val defaultOrder = arrayListOf(
                Staff.ZHOU,
                Staff.DAN,
                Staff.WANG_2,
                Staff.QI,
                Staff.GAO,
                Staff.CAO,
                Staff.TANG,
                Staff.MAI,
                Staff.LING,
                Staff.WANG,
                Staff.ZHU
        )
    }


    var curIndex = 0
    var priorityStaff: Staff? = null
    var backupResult: MutableList<Arrangement> = mutableListOf()

    fun setStartIndex(index: Int) {
        curIndex = index
    }

    fun pop(arrangements: List<Arrangement>, shift: Shift, workDay: WorkDay): Staff {
        while(true) {
            val staff = nextStaff()
            Log.d(Arranger.TAG, "Backup staff: $staff ${canBackup(staff, arrangements, shift, workDay)}")
            if (canBackup(staff, arrangements, shift, workDay)) {
                backupResult.add(Arrangement(staff, shift, workDay, true))
                return staff
            }
        }
    }

    fun nextStaff(): Staff {
        return priorityStaff?.let {
            val result = it
            priorityStaff = null
            result
        } ?: defaultOrder[curIndex++ % defaultOrder.size]
    }

    fun canBackup(staff: Staff, arrangements: List<Arrangement>, shift: Shift, workDay: WorkDay): Boolean {
        return !(arrangements.find { it.staff == staff }?.shift?.mustAvailable ?: false)
                && staff.isAvailable(shift, workDay)
    }

    fun setPriority(staff: Staff? ) {
        priorityStaff = staff
    }

}