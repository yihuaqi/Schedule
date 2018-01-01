package com.yihuaqi.scheduler

import android.app.Application
import com.yihuaqi.scheduler.Model.CoreData

/**
 * Created by yihuaqi on 1/1/18.
 */
class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        CoreData.init(this)
    }
}