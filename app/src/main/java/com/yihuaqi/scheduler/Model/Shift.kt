package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Shift(val name: String) {
    companion object
}

fun Shift.Companion.defaultShift(): List<Shift> {
    return defaultShiftName().map { Shift(it) }
}

fun Shift.Companion.defaultShiftName(): List<String> {
    return (1..15).map { "Shift$it" }
}