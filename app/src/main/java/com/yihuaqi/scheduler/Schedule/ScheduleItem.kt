package com.yihuaqi.scheduler.Schedule

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.yihuaqi.scheduler.R
import org.jetbrains.anko.backgroundColor

/**
 * Created by yihuaqi on 12/26/17.
 */
@EpoxyModelClass(layout = R.layout.schedule_item)
abstract class ScheduleItem: EpoxyModelWithHolder<ScheduleItem.Holder>() {

    @EpoxyAttribute lateinit var text: String
    @EpoxyAttribute var color: Boolean = false

    override fun bind(holder: Holder?) {
        holder?.textView?.text = text
        holder?.rootView?.backgroundColor = if (color) Color.LTGRAY else Color.WHITE
    }

    class Holder: EpoxyHolder() {
        var textView: TextView? = null
        var rootView: View? = null
        override fun bindView(itemView: View?) {
            textView = itemView?.findViewById(R.id.text)
            rootView = itemView
        }
    }
}