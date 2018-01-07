package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arrangement(val staff: Staff?, var shift: Shift, var workDay: WorkDay) {

    override fun toString(): String {
        return "${staff.toString()} ${shift.toString()} ${workDay.toString()}"
    }
}