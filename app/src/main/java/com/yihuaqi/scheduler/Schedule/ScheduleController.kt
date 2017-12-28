package com.yihuaqi.scheduler.Schedule

import com.airbnb.epoxy.EpoxyController
import com.yihuaqi.scheduler.Model.*

/**
 * Created by yihuaqi on 12/26/17.
 */
class ScheduleController : EpoxyController() {
    val id = 0
        get() = field++


    override fun buildModels() {
        ScheduleItem_().id(id).text("   ").addTo(this)
        (1..5).forEach { it.addTo(this) }
        Arranger(Shift.defaultShift(), Staff.goodShiftOrder().toMutableList(), Staff.badShiftOrder().toMutableList()).test()
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

fun Arrangement.addTo(controller: ScheduleController) {
    ScheduleItem_().id(controller.id).text("Staff[${this.staff.name}] Shift[${this.shift.name}]").addTo(controller)
}