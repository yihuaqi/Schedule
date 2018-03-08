package com.yihuaqi.scheduler.Schedule

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.yihuaqi.scheduler.R
import org.jetbrains.anko.textColor

/**
 * Created by yihuaqi on 3/8/18.
 */
@EpoxyModelClass(layout = R.layout.backup_item)
abstract class BackupItem: EpoxyModelWithHolder<BackupItem.Holder>()  {
    @EpoxyAttribute
    lateinit var text: String
    @EpoxyAttribute
    var color: Int = Color.BLACK
    @EpoxyAttribute
    var clickListener: View.OnClickListener? = null

    override fun bind(holder: Holder?) {
        holder?.textView?.text = text
        holder?.textView?.textColor = color
        holder?.rootView?.setOnClickListener(clickListener)
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