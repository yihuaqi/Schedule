package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/28/17.
 */
data class Arrangement(val staff: Staff?, var shift: Shift, var workDay: WorkDay) {

    override fun toString(): String {
        return "${staff.toString()} ${shift.toString()} ${workDay.toString()}"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Arrangement) {
            staff == other.staff && shift == other.shift && workDay == other.workDay
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return (staff?.hashCode() ?: 0) + shift.hashCode() + workDay.hashCode()
    }
}
