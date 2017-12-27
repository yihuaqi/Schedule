package com.yihuaqi.scheduler.Schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        recyclerView.layoutManager = GridLayoutManager(context, 6)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

        })
        recyclerView.setControllerAndBuildModels(ScheduleController())
    }
}