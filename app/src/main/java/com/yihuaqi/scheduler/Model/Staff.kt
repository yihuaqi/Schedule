package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Staff(val name: String) {
    companion object {
        val ZHOU = Staff("周")
        val MA = Staff("马")
        val WANG = Staff("王")
        val CAO = Staff("曹")
        val DAN = Staff("单")
        val GAO = Staff("高")
        val ZHANG = Staff("张")
        val SHI = Staff("史")
        val MAI = Staff("麦")
        val TANG = Staff("唐")
        val LING = Staff("玲")
        val QI = Staff("齐")
        val ZHU = Staff("朱")
    }

    override fun toString(): String {
        return "Staff[$name]"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Staff) {
            return name == other.name
        }
        return super.equals(other)
    }
}

fun Staff.Companion.groupAOrder(): List<Staff> {
    return arrayListOf(
            ZHOU,
            MA,
            WANG,
            CAO,
            DAN,
            GAO,
            ZHANG,
            SHI,
            MAI,
            TANG,
            LING,
            QI
    )
}

fun Staff.Companion.groupBOrder(): List<Staff> {
    return arrayListOf(
            QI,
            CAO,
            MAI,
            GAO,
            LING,
            WANG,
            ZHU,
            ZHANG,
            SHI,
            MA,
            DAN,
            TANG,
            WANG,
            ZHOU
    )
}