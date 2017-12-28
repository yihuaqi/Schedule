package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Staff(val id: Int, val name: String) {
    companion object {

    }

    override fun toString(): String {
        return "Staff[$name]"
    }
}

fun Staff.Companion.defaultMember(): List<Staff> {
    return defaultNames().mapIndexed { index, name -> Staff(index, name) }
}

fun Staff.Companion.defaultNames(): List<String> {
    return (1..15).map { "staff$it" }
}

fun Staff.Companion.goodShiftOrder(): List<Staff> {
    return defaultMember()
}

fun Staff.Companion.badShiftOrder(): List<Staff> {
    return defaultMember().reversed()
}