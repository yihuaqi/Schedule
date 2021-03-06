package com.yihuaqi.scheduler

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.yihuaqi.scheduler.Magic.CoroutineTest
import com.yihuaqi.scheduler.Schedule.ScheduleFragment
import com.yihuaqi.scheduler.Settings.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_schedule -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, ScheduleFragment())
                        .commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, SettingFragment())
                        .commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_magic -> {
                CoroutineTest.test()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_schedule
    }
}
