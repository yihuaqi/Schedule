package com.yihuaqi.scheduler.Model

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by yihuaqi on 1/1/18.
 */
object CoreData {
    val spName = "schedule"
    lateinit var sp: SharedPreferences
    val KEY_GROUP_A_INDEX = "group_a_index"
    val KEY_GROUP_B_INDEX = "group_b_index"
    val KEY_BACKUP_GROUP_INDEX = "backup_group_index"
    fun init(context: Context) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    var groupAIndex: Int
    get() {
        return sp.getInt(KEY_GROUP_A_INDEX, 0)
    }
    set(value) {
        sp.edit().putInt(KEY_GROUP_A_INDEX, value).apply()
    }

    var groupBIndex: Int
    get() {
        return sp.getInt(KEY_GROUP_B_INDEX, 0)
    }
    set(value) {
        sp.edit().putInt(KEY_GROUP_B_INDEX, value).apply()
    }

    var backupIndex: Int
    get() {
        return sp.getInt(KEY_BACKUP_GROUP_INDEX, 0)
    }
    set(value) {
        sp.edit().putInt(KEY_BACKUP_GROUP_INDEX, value).apply()
    }

}
