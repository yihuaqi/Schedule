package com.yihuaqi.scheduler.Schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.yihuaqi.scheduler.Model.*
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
                val options = Staff.ALL.filter {staff ->
                    arrangements.find { arrangement ->
                        arrangement.staff == staff
                    } == null
                }.toMutableList()
                options.add(0, null)

                Shift.ALL.forEachIndexed { index, shift ->
                    val arrangement = arrangements.find {arrangement ->
                        arrangement.shift == shift && arrangement.workDay == workDay
                    }!!
                    arrangement.toItem(index, (index + workDay.offset) % 2 == 0)
                            .clickListener({ v ->
                                Log.d("ScheduleFragment", "onClick: $arrangement")
                                AlertDialog.Builder(context)
                                        .setTitle("替换人员")
                                        .setItems(options.map { it.name() }.toTypedArray(), { dialogInterface, i ->
                                            val index = arrangements.indexOf(arrangement)
                                            arrangements[index] = arrangement.copy(staff = options[i])
                                            controller.requestModelBuild()
                                        })
                                        .show()
                            })
                        .addTo(controller)
                }
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
    return ScheduleItem_().id(this.staff?.id?: -(id + 1)).color(color).text(this?.staff.name())
}