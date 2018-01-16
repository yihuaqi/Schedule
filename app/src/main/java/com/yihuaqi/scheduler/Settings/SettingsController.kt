package com.yihuaqi.scheduler.Settings

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.yihuaqi.scheduler.Model.CoreData
import com.yihuaqi.scheduler.Model.Staff
import com.yihuaqi.scheduler.Model.WorkDay

/**
 * Created by yihuaqi on 1/1/18.
 */
class SettingsController(val context: Context): EpoxyController() {

    override fun buildModels() {
        showDialog(0, "Group A Staff", Staff.getShuffledGroupAOrder(WorkDay.Monday), { CoreData.groupAIndex }, { i: Int -> CoreData.groupAIndex = i })
        showDialog(1, "Group B Staff", Staff.getShuffledGroupBOrder(WorkDay.Monday), { CoreData.groupBIndex }, { i: Int -> CoreData.groupBIndex = i })
        showDialog(2, "Group Backup Staff", Staff.getShuffledBackupOrder(WorkDay.Monday), { CoreData.backupIndex }, { i: Int -> CoreData.backupIndex = i })
    }

    fun showDialog(id: Int, title: String, groups: List<Staff>, index: () -> Int, saveIndex: (Int) -> Unit) {
        SettingEditItem_().id(id)
                .title(title)
                .text(groups[index()].name)
                .listener(View.OnClickListener {
                    AlertDialog.Builder(context)
                            .setTitle(title)
                            .setItems(groups.map { it.name }.toTypedArray(), { dialogInterface, i ->
                                Log.d("Schedule", "selected: ${groups[i].name}")
                                saveIndex(i)
                            })
                            .show()

                })
                .addTo(this)
    }
}