package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 3/12/18.
 */
data class Leave(val staff: Staff, val workDay: WorkDay) {
    override fun toString(): String {
        return "${workDay.name}: ${staff.name}"
    }
}