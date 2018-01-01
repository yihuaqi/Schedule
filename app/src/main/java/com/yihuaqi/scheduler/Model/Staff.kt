package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Staff(val name: String) {
    companion object {

    }

    override fun toString(): String {
        return "Staff[$name]"
    }
}

fun Staff.Companion.goodShiftOrder(): List<Staff> {
    return arrayListOf(
            Staff("周"),
            Staff("马"),
            Staff("王"),
            Staff("曹"),
            Staff("单"),
            Staff("高"),
            Staff("张"),
            Staff("史"),
            Staff("麦"),
            Staff("唐"),
            Staff("玲"),
            Staff("齐")
    )
}

fun Staff.Companion.badShiftOrder(): List<Staff> {
    return arrayListOf(
            Staff("齐"),
            Staff("曹"),
            Staff("麦"),
            Staff("高"),
            Staff("玲"),
            Staff("王"),
            Staff("朱"),
            Staff("张"),
            Staff("史"),
            Staff("马"),
            Staff("单"),
            Staff("唐"),
            Staff("汪"),
            Staff("周")
    )
}