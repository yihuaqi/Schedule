package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
sealed class Shift(val name: String) {
    companion object
}

class GoodShift(n: String): Shift(n) {

}

class BadShift(n: String): Shift(n) {

}

fun Shift.Companion.defaultShift(): List<Shift> {
    return defaultShiftName().mapIndexed { index, s ->
        if (index % 2 == 0) {
            GoodShift(s)
        } else {
            BadShift(s)
        }
    }
}

fun Shift.Companion.defaultShiftName(): List<String> {
    return (1..15).map { "Shift$it" }
}