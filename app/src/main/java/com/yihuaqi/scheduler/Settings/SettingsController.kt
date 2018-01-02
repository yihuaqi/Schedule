package com.yihuaqi.scheduler.Settings

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.yihuaqi.scheduler.Model.CoreData
import com.yihuaqi.scheduler.Model.Staff
import kotlin.reflect.KMutableProperty

/**
 * Created by yihuaqi on 1/1/18.
 */
class SettingsController(val context: Context): EpoxyController() {

    override fun buildModels() {
        showDialog(0, "Group A Staff", Staff.shuffledGroupAOrder, CoreData::groupAIndex)
        showDialog(1, "Group B Staff", Staff.shuffledGroupBOrder, CoreData::groupBIndex)
        showDialog(2, "Group Backup Staff", Staff.shuffledBackupOrder, CoreData::backupIndex)
    }

    fun showDialog(id: Int, title: String, groups: List<Staff>, index: KMutableProperty<Int>) {
        SettingEditItem_().id(id)
                .title(title)
                .text(groups[index.getter.call()].name)
                .listener(View.OnClickListener {
                    AlertDialog.Builder(context)
                            .setTitle(title)
                            .setItems(groups.map { it.name }.toTypedArray(), { dialogInterface, i ->
                                Log.d("Schedule", "selected: ${groups[i].name}")
                                index.setter.call(i)
                            })
                            .show()

                })
                .addTo(this)
    }
}