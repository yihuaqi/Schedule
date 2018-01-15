package com.yihuaqi.scheduler.Schedule

import android.view.View
import android.widget.Button
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.yihuaqi.scheduler.R

/**
 * Created by yihuaqi on 1/15/18.
 */
@EpoxyModelClass(layout = R.layout.test_button)
abstract class TestButtonItem: EpoxyModelWithHolder<TestButtonItem.Holder>() {

    @EpoxyAttribute lateinit var text: String
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) lateinit var listener: View.OnClickListener

    override fun bind(holder: Holder?) {
        holder!!.button.text = text
        holder!!.button.setOnClickListener(listener)
    }

    class Holder: EpoxyHolder() {
        lateinit var button: Button

        override fun bindView(itemView: View?) {
            button = itemView!!.findViewById(R.id.button)!!
        }
    }
}