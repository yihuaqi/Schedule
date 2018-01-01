package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 1/1/18.
 */
class WorkDay(val name: String) {
    companion object {
        val Monday = WorkDay("周一")
        val Tuesday = WorkDay("周二")
        val Wendsday = WorkDay("周三")
        val Thursday = WorkDay("周四")
        val Friday = WorkDay("周五")
    }
}
