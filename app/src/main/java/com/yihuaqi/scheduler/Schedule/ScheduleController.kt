package com.yihuaqi.scheduler.Schedule

import com.airbnb.epoxy.EpoxyController

/**
 * Created by yihuaqi on 12/26/17.
 */
class ScheduleController : EpoxyController() {
    override fun buildModels() {
        for(i in 1..10) {
            ScheduleItem_()
                    .id(i)
                    .text("test$i")
                    .addTo(this)
        }
    }
}