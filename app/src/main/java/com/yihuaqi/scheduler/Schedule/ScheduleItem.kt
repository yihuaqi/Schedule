package com.yihuaqi.scheduler.Schedule

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.yihuaqi.scheduler.R

/**
 * Created by yihuaqi on 12/26/17.
 */
@EpoxyModelClass(layout = R.layout.schedule_item)
abstract class ScheduleItem: EpoxyModelWithHolder<ScheduleItem.Holder>() {

    @EpoxyAttribute lateinit var text: String

    override fun bind(holder: Holder?) {
        holder?.textView?.text = text
    }

    class Holder: EpoxyHolder() {
        var textView: TextView? = null
        override fun bindView(itemView: View?) {
            textView = itemView?.findViewById(R.id.text)
        }
    }
}