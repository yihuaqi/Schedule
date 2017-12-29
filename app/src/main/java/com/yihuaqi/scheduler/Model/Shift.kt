package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Shift(val name: String) {
    companion object {

    }

    override fun toString(): String {
        return "Shift[$name]"
    }
}

fun Shift.Companion.defaultShift(): List<Shift> {
    return defaultShiftName().mapIndexed { index, s ->
        Shift(s)
    }
}

fun Shift.Companion.defaultShiftName(): List<String> {
    return (1..15).map { "Shift$it" }
}

fun Shift.makeArrangement(staffs: MutableList<Staff>): Arrangement {
    return Arrangement(staffs.removeAt(0), this)
}