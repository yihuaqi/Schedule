package com.yihuaqi.scheduler.Schedule

import com.airbnb.epoxy.EpoxyController
import com.yihuaqi.scheduler.Model.Shift
import com.yihuaqi.scheduler.Model.Staff
import com.yihuaqi.scheduler.Model.defaultMember
import com.yihuaqi.scheduler.Model.defaultShift

/**
 * Created by yihuaqi on 12/26/17.
 */
class ScheduleController : EpoxyController() {
    val id = 0
        get() = field++


    override fun buildModels() {
        ScheduleItem_().id(id).text("   ").addTo(this)
        (1..5).forEach { it.addTo(this) }
        Shift.defaultShift().forEachIndexed { index, shift ->
            shift.addTo(this)
            (1..5).forEach { Staff.defaultMember()[index].addTo(this) }
        }
    }
}

fun Int.addTo(controller: ScheduleController) {
    ScheduleItem_().id(controller.id).text("Day $this").addTo(controller)
}

fun Staff.addTo(controller: ScheduleController) {
    ScheduleItem_().id(controller.id).text(this.name).addTo(controller)
}

fun Shift.addTo(controller: ScheduleController) {
    ScheduleItem_().id(controller.id).text(this.name).addTo(controller)
}