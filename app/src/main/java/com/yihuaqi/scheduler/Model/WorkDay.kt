package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 1/1/18.
 */
data class WorkDay(val name: String, val offset: Int) {
    companion object {
        val Monday = WorkDay("周一", 0)
        val Tuesday = WorkDay("周二", 1)
        val Wendsday = WorkDay("周三", 2)
        val Thursday = WorkDay("周四",3 )
        val Friday = WorkDay("周五",4 )
        val ALL = listOf(Monday, Tuesday, Wendsday, Thursday, Friday)
    }

    override fun toString(): String {
        return "WorkDay[$name]"
    }
}
