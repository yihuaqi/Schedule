package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 2/14/18.
 */
class HuizhenStaffManager {
    companion object {
        val defaultOrder = arrayListOf(
                Staff.ZHOU,
                Staff.MA,
                Staff.WANG,
                Staff.CAO,
                Staff.DAN,
                Staff.GAO,
                Staff.ZHANG,
                Staff.SHI,
                Staff.MAI,
                Staff.TANG,
                Staff.LING,
                Staff.QI
        )
    }

    var curIndex = 0

    fun setStartIndex(index: Int) {
        curIndex = index
    }

    fun pop(shift: Shift, workDay: WorkDay): Staff {
        while(true) {
            val staff = nextStaff()
            if (canHuizhen(staff, shift, workDay)) {
                return staff
            }
        }
    }

    fun nextStaff(): Staff {
        return defaultOrder[curIndex++ % defaultOrder.size]
    }

    fun canHuizhen(staff: Staff, shift: Shift, workDay: WorkDay): Boolean {
        return staff.isAvailable(shift, workDay)
    }
}