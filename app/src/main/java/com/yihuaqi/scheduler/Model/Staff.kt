package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Staff(val name: String,
            val incapableShifts: List<Shift> = arrayListOf(),
            val incapableWorkDays: List<WorkDay> = arrayListOf(),
            val forceEmpty: List<Arrangement> = arrayListOf()) {
    companion object {
        val ZHOU = Staff("周",
                incapableShifts = arrayListOf(Shift.MR_1, Shift.MR_2),
                forceEmpty = arrayListOf(
                        Arrangement(null, Shift.HUI_ZHEN, WorkDay.Monday),
                        Arrangement(null, Shift.CHANGE_WEI, WorkDay.Monday)
                )
        )
        val MA = Staff("马")
        val WANG = Staff("王")
        val CAO = Staff("曹")
        val DAN = Staff("单")
        val GAO = Staff("高")
        val ZHANG = Staff("张")
        val SHI = Staff("史")
        val MAI = Staff("麦",
                forceEmpty = arrayListOf(
                        Arrangement(null, Shift.HUI_ZHEN, WorkDay.Wendsday),
                        Arrangement(null, Shift.CHANGE_WEI, WorkDay.Wendsday)
                ))
        val TANG = Staff("唐", incapableWorkDays = arrayListOf(WorkDay.Thursday))
        val LING = Staff("玲")
        val QI = Staff("齐")
        val ZHU = Staff("朱")
        val SUN = Staff("孙")

        private val groupAOrder = arrayListOf(
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

        private val groupBOrder = arrayListOf(
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

        private val backupOrder = arrayListOf(
                ZHOU,
                DAN,
                WANG,
                QI,
                GAO,
                CAO,
                TANG,
                MAI,
                LING,
                WANG,
                ZHU
        )

        fun getShuffledGroupAOrder(workDay: WorkDay): List<Staff> {
            return shuffle(groupAOrder, CoreData.groupAIndex + workDay.offset)
        }

        fun getShuffledGroupBOrder(workDay: WorkDay): List<Staff> {
            return shuffle(groupBOrder, CoreData.groupBIndex + workDay.offset)
        }

        fun getShuffledBackupOrder(workDay: WorkDay): List<Staff> {
            return shuffle(backupOrder, CoreData.backupIndex + workDay.offset)
        }

        fun shuffle(staffs: List<Staff>, initial: Int): List<Staff> {
            return staffs.mapIndexed { index, staff ->
                staffs[(index + initial) % staffs.size]
            }
        }
    }

    fun isAvailable(workDay: WorkDay, shift: Shift): Boolean {
        return !incapableShifts.contains(shift)
                && !incapableWorkDays.contains(workDay)

    }

    fun forceEmpty(shift: Shift, workDay: WorkDay): Boolean {
        return forceEmpty.contains(Arrangement(null, shift, workDay))
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

fun List<Staff>.nextAvailableStaff(shift: Shift, workDay: WorkDay): Staff? {
    return this.find { it.isAvailable(workDay, shift) }
}
