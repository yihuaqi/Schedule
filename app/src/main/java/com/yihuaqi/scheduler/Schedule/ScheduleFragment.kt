package com.yihuaqi.scheduler.Schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        initWorkDays()
        initShifts()

        val arrangementsMap = getArrangementsMap()

        initArrangements(arrangementsMap)


    }

    fun initWorkDays() {
        recyclerViewWorkDay.layoutManager = GridLayoutManager(context, 6)
        recyclerViewWorkDay.buildModelsWith { controller ->
            ScheduleItem_().id(-1).text("").color(true).addTo(controller)
            WorkDay.ALL.forEach {
                it.toItem(it.offset).addTo(controller)
            }
        }
    }

    fun initShifts() {
        recyclerViewShift.buildModelsWith { controller ->
            Shift.ALL.forEachIndexed { index, shift ->
                shift.toItem(index).addTo(controller)
            }
        }
    }

    fun initArrangements(arrangementsMap: MutableMap<WorkDay, MutableList<Arrangement>>) {
        getWorkdayMap().forEach { (workDay, epoxyRecyclerView) ->
            val arrangements = arrangementsMap[workDay]!!
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
                                val fromShift = Shift.ALL[fromPosition]
                                val toShift = Shift.ALL[toPosition]

                                val fromArrangement = arrangements.find { it.shift == fromShift }!!
                                val toArrangement = arrangements.find { it.shift == toShift }!!
                                fromArrangement.shift = toShift
                                toArrangement.shift = fromShift
                            }
                        })
            }
        }
    }

    fun getArrangementsMap(): MutableMap<WorkDay, MutableList<Arrangement>> {
        val arranger = Arranger()
        return WorkDay.ALL.map {
            it to arranger.calculate(it).toMutableList()
        }.toMap().toMutableMap()
    }

    fun getWorkdayMap(): Map<WorkDay, EpoxyRecyclerView> {
        return mapOf<WorkDay, EpoxyRecyclerView>(
                WorkDay.Monday to recyclerViewMonday,
                WorkDay.Tuesday to recyclerViewTuesday,
                WorkDay.Wendsday to recyclerViewWendsday,
                WorkDay.Thursday to recyclerViewThursday,
                WorkDay.Friday to recyclerViewFriday
        )
    }
}

fun WorkDay.toItem(id: Int): ScheduleItem_ {
    return ScheduleItem_().id(id).text(this.name)
}

fun Shift.toItem(id: Int): ScheduleItem_ {
    return ScheduleItem_().id(id).text(this.name)
}

fun Arrangement.toItem(id: Int, color: Boolean): ScheduleItem_ {
    return ScheduleItem_().id(this.staff?.id?: -(id + 1)).color(color).text(this?.staff?.name ?: "空")
}