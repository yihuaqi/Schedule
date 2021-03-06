package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Shift(val name: String, val mustAvailable: Boolean = false) {
    companion object {
        val CHANGE_WEI = Shift("胃肠造影", true)
        val CT_1 = Shift("CT-1")
        val CT_2 = Shift("CT-2")
        val CT_3 = Shift("CT-3")
        val CT_4 = Shift("CT-4")
        val MR_1 = Shift("MR审1", true)
        val CT_5 = Shift("CT-5")
        val CT_6 = Shift("CT-6")
        val CT_7 = Shift("CT-7")
        val MR_2 = Shift("MR审2", true)
        val CT_8 = Shift("CT-8")
        val CT_9 = Shift("CT-9")
        val CT_10 = Shift("CT-10")
        val CT_11 = Shift("CT-11")
        val CT_12 = Shift("CT-12")
        val CT_13 = Shift("CT-13")
        val HUI_ZHEN = Shift("会诊", true)

        fun nextAvailableCT(arrangements: List<Arrangement>, workDay: WorkDay): Shift? {
            return arrangements.firstOrNull { it.workDay == workDay && it.shift.isCT() }?.shift
        }

        val ALL = listOf(HUI_ZHEN, CHANGE_WEI, CT_1, CT_2, CT_3, CT_4, MR_1, CT_5, CT_6, CT_7, MR_2, CT_8, CT_9, CT_10, CT_11)

    }

    override fun toString(): String {
        return "Shift[$name]"
    }


    fun isCT(): Boolean {
        return name.contains("CT")
    }
}

fun Shift.Companion.defaultShift(): List<Shift> {
    return defaultShiftName().mapIndexed { index, s ->
        Shift(s)
    }
}

fun Shift.Companion.defaultShiftName(): List<String> {
    return (1..15).map { "Shift$it" }
}

fun Shift.Companion.groupA(): Shift {
    return HUI_ZHEN
}

fun Shift.Companion.groupB(): List<Shift> {
    return arrayListOf(
            CHANGE_WEI,
            CT_1,
            CT_2,
            CT_3,
            CT_4,
            MR_1,
            CT_5,
            CT_6,
            CT_7,
            MR_2,
            CT_8,
            CT_9,
            CT_10,
            CT_11
    )
}
