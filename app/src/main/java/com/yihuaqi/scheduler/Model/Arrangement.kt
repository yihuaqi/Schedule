package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/28/17.
 */
class Arrangement(val staff: Staff, var shift: Shift) {
    override fun toString(): String {
        return "${staff.toString()} ${shift.toString()}"
    }
}