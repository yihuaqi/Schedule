package com.yihuaqi.scheduler.Settings

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.yihuaqi.scheduler.Model.*

/**
 * Created by yihuaqi on 1/1/18.
 */
class SettingsController(val context: Context): EpoxyController() {

    override fun buildModels() {
        showDialog(0, "周一会诊", Staff.groupAOrder, { CoreData.groupAIndex }, { i: Int -> CoreData.groupAIndex = i })
        showDialog(1, "周一胃肠造影", Staff.groupBOrder, { CoreData.groupBIndex }, { i: Int -> CoreData.groupBIndex = i })
        showDialog(2, "替班顺序", BackupStaffManager.defaultOrder, { CoreData.backupIndex }, { i: Int -> CoreData.backupIndex = i })
        showLeaveDialog()
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
                                requestModelBuild()
                            })
                            .show()

                })
                .addTo(this)
    }

    fun showLeaveDialog() {
        val leaves = CoreData.leaves.toMutableList()

        SettingEditItem_().id(3).title("管理休假").text("点击查看").listener(View.OnClickListener {
            AlertDialog.Builder(context)
                    .setTitle("管理休假")
                    .setItems(leaves.map { it.toString() }.toTypedArray(),
                            { dialogInterface, i ->
                                leaves.removeAt(i)
                                CoreData.leaves = leaves
                            }

            ).show()
        }).addTo(this)


        SettingEditItem_().id(4).title("添加休假").text("点击添加").listener(View.OnClickListener {
            AlertDialog.Builder(context)
                    .setTitle("选择工作日")
                    .setItems(WorkDay.ALL.map { it.name }.toTypedArray(), { dialogInterface, i ->
                        val workDay = WorkDay.ALL[i]
                        AlertDialog.Builder(context)
                                .setTitle("选择人员")
                                .setItems(Staff.ALL.map { it.name() }.toTypedArray(), {  dialogInterface, i2 ->
                                    val staff = Staff.ALL[i2]
                                    leaves.add(Leave(staff!!, workDay))
                                    CoreData.leaves = leaves
                                }).show()
                    }).show()
        }).addTo(this)
    }
}