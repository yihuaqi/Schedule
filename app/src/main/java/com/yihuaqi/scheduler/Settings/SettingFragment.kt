package com.yihuaqi.scheduler.Settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yihuaqi.scheduler.R
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * Created by yihuaqi on 12/26/17.
 */
class SettingFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.setControllerAndBuildModels(SettingsController(context))
    }
}