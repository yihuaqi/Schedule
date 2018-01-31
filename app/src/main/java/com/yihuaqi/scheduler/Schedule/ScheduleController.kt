package com.yihuaqi.scheduler.Schedule

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.yihuaqi.scheduler.Model.Arrangement
import com.yihuaqi.scheduler.Model.Arranger
import com.yihuaqi.scheduler.Model.Shift
import com.yihuaqi.scheduler.Model.WorkDay

/**
 * Created by yihuaqi on 12/26/17.
 */
class ScheduleController : EpoxyController() {
    val id = 0
        get() = field++


    override fun buildModels() {
        ScheduleItem_().id(id).text("   ").addTo(this)
        WorkDay.ALL.forEach { it.addTo(this) }
        val arranger = Arranger()
        val allArrangements = WorkDay.ALL.map { arranger.calculate(it) }.reduceRight { list, acc ->
            list + acc
        }
        Shift.ALL.forEachIndexed { shiftIndex, shift ->
            shift.addTo(this)
            WorkDay.ALL.forEachIndexed { workDayIndex, workDay ->
                allArrangements.find { arrangement ->
                    arrangement.shift == shift && arrangement.workDay == workDay
                }.addTo((shiftIndex+workDayIndex) % 2 == 0, this)
            }
        }
        TestButtonItem_().id(id).text("Test").listener(View.OnClickListener {
            Arranger().test()
        }).addTo(this)
    }
}

fun Shift.addTo(controller: ScheduleController) {
    ScheduleItem_().id(controller.id).text(this.name).addTo(controller)
}

fun WorkDay.addTo(controller: ScheduleController) {
    ScheduleItem_().id(controller.id).text(this.name).addTo(controller)
}

fun Arrangement?.addTo(color: Boolean, controller: ScheduleController) {
    ScheduleItem_().id(controller.id).color(color).text(this?.staff?.name ?: "None").addTo(controller)
}