package com.yihuaqi.scheduler.Settings

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.yihuaqi.scheduler.Model.CoreData
import com.yihuaqi.scheduler.Model.Staff

/**
 * Created by yihuaqi on 1/1/18.
 */
class SettingsController(val context: Context): EpoxyController() {

    override fun buildModels() {
        val groupA = Staff.shuffledGroupAOrder
        SettingEditItem_().id(0)
                .title("Group A Staff")
                .text(groupA[CoreData.groupAIndex].name)
                .listener(View.OnClickListener {
                    AlertDialog.Builder(context)
                            .setTitle("Group A Staff")
                            .setItems(groupA.map { it.name }.toTypedArray(), DialogInterface.OnClickListener { dialogInterface, i ->
                                Log.d("Schedule", "selected: ${groupA[i].name}")
                                CoreData.groupAIndex = i
                            })
                            .show()

                })
                .addTo(this)
        val groupB = Staff.ShuffledGroupBOrder
        SettingEditItem_().id(1 )
                .title("Group B Staff")
                .text(groupB[CoreData.groupBIndex].name)
                .listener(View.OnClickListener {
                    AlertDialog.Builder(context)
                            .setTitle("Group A Staff")
                            .setItems(groupB.map { it.name }.toTypedArray(), DialogInterface.OnClickListener { dialogInterface, i ->
                                Log.d("Schedule", "selected: ${groupB[i].name}")
                                CoreData.groupBIndex = i
                            })
                            .show()

                })
                .addTo(this)
    }
}