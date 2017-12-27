package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Staff(val id: Int, val name: String) {
    companion object
}

fun Staff.Companion.defaultMember(): List<Staff> {
    return defaultNames().mapIndexed { index, name -> Staff(index, name) }
}

fun Staff.Companion.defaultNames(): List<String> {
    return (1..15).map { "staff$it" }
}
