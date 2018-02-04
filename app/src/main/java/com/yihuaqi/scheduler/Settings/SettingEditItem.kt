package com.yihuaqi.scheduler.Settings

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.yihuaqi.scheduler.R

/**
 * Created by yihuaqi on 1/1/18.
 */
@EpoxyModelClass(layout = R.layout.settings_edit_item)
abstract class SettingEditItem: EpoxyModelWithHolder<SettingEditItem.Holder>() {

    @EpoxyAttribute lateinit var title: String
    @EpoxyAttribute lateinit var text: String
    @EpoxyAttribute lateinit var listener: View.OnClickListener

    override fun bind(holder: Holder?) {
        holder!!.title.text = title
        holder.text.text = text
        holder.root.setOnClickListener(listener)
    }

    class Holder: EpoxyHolder() {
        lateinit var title: TextView
        lateinit var text: TextView
        lateinit var root: View
        override fun bindView(itemView: View?) {
            title = itemView!!.findViewById(R.id.title)!!
            text = itemView.findViewById(R.id.text)
            root = itemView
        }
    }
}