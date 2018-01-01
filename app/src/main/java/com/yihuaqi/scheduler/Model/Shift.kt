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

fun Shift.Companion.groupA(): List<Shift> {
    return arrayListOf(Shift("会诊"))
}

fun Shift.Companion.groupB(): List<Shift> {
    return arrayListOf(
            Shift("胃肠造影"),
            Shift("CT-1"),
            Shift("CT-2"),
            Shift("CT-3"),
            Shift("CT-4"),
            Shift("MR审1"),
            Shift("CT-5"),
            Shift("CT-6"),
            Shift("CT-7"),
            Shift("MR审2"),
            Shift("CT-8"),
            Shift("CT-9"),
            Shift("CT-10"),
            Shift("CT-11")
    )
}



fun Shift.makeArrangement(staffs: MutableList<Staff>): Arrangement {
    return Arrangement(staffs.removeAt(0), this)
}