package com.yihuaqi.scheduler.Schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.EpoxyTouchHelper
import com.yihuaqi.scheduler.Model.Arrangement
import com.yihuaqi.scheduler.Model.Arranger
import com.yihuaqi.scheduler.Model.Shift
import com.yihuaqi.scheduler.Model.WorkDay
import com.yihuaqi.scheduler.R
import kotlinx.android.synthetic.main.fragment_schedule.*

/**
 * Created by yihuaqi on 12/25/17.
 */
class ScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recyclerViewWorkDay.layoutManager = GridLayoutManager(context, 6)
        recyclerViewWorkDay.buildModelsWith { controller ->
            ScheduleItem_().id(-1).text("").color(true).addTo(controller)
            WorkDay.ALL.forEach {
                it.toItem(it.offset).addTo(controller)
            }
        }
        recyclerViewShift.buildModelsWith { controller ->
            Shift.ALL.forEachIndexed { index, shift ->
                shift.toItem(index).addTo(controller)
            }
        }
        val workdayMap = mapOf<WorkDay, EpoxyRecyclerView>(
                WorkDay.Monday to recyclerViewMonday,
                WorkDay.Tuesday to recyclerViewTuesday,
                WorkDay.Wendsday to recyclerViewWendsday,
                WorkDay.Thursday to recyclerViewThursday,
                WorkDay.Friday to recyclerViewFriday
                )
        val arranger = Arranger()
        workdayMap.forEach { (workDay, epoxyRecyclerView) ->
            val arrangements = arranger.calculate(workDay)
            epoxyRecyclerView.buildModelsWith { controller ->
                Shift.ALL.forEachIndexed { index, shift ->
                    arrangements.find {arrangement ->
                        arrangement.shift == shift && arrangement.workDay == workDay
                    }!!.toItem(index, (index + workDay.offset) % 2 == 0).addTo(controller)
                }
                EpoxyTouchHelper.initDragging(controller)
                        .withRecyclerView(epoxyRecyclerView)
                        .forVerticalList()
                        .withTarget(ScheduleItem::class.java)
                        .andCallbacks(object : EpoxyTouchHelper.DragCallbacks<ScheduleItem>() {
                            override fun onModelMoved(fromPosition: Int, toPosition: Int, modelBeingMoved: ScheduleItem?, itemView: View?) {
                                Log.d("ScheduleFragment", "onModelMoved")
                            }

                        })
            }
        }
    }

    /** Easily add models to an EpoxyRecyclerView, the same way you would in a buildModels method of EpoxyController. */
    fun EpoxyRecyclerView.withModels(buildModelsCallback: EpoxyController.() -> Unit) {
        setControllerAndBuildModels(object : EpoxyController() {
            override fun buildModels() {
                buildModelsCallback()
            }
        })
    }
}

fun WorkDay.toItem(id: Int): ScheduleItem_ {
    return ScheduleItem_().id(id).text(this.name)
}

fun Shift.toItem(id: Int): ScheduleItem_ {
    return ScheduleItem_().id(id).text(this.name)
}

fun Arrangement.toItem(id: Int, color: Boolean): ScheduleItem_ {
    return ScheduleItem_().id(id).color(color).text(this?.staff?.name ?: "空")
}