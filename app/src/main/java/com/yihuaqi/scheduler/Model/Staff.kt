package com.yihuaqi.scheduler.Model

/**
 * Created by yihuaqi on 12/27/17.
 */
class Staff(val name: String, val incapableShifts: List<Shift> = arrayListOf(), val incapableWorkDays: List<WorkDay> = arrayListOf()) {
    companion object {
        val ZHOU = Staff("周", incapableShifts = arrayListOf(Shift.MR_1, Shift.MR_2))
        val MA = Staff("马")
        val WANG = Staff("王")
        val CAO = Staff("曹")
        val DAN = Staff("单")
        val GAO = Staff("高")
        val ZHANG = Staff("张")
        val SHI = Staff("史")
        val MAI = Staff("麦")
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

        val shuffledGroupAOrder: List<Staff>
        get() {
            return shuffle(groupAOrder, CoreData.groupAIndex)
        }

        val shuffledGroupBOrder: List<Staff>
            get() {
                return shuffle(groupBOrder, CoreData.groupBIndex)
            }

        val shuffledBackupOrder: List<Staff>
            get() {
                return shuffle(backupOrder, CoreData.backupIndex)
            }

        fun shuffle(staffs: List<Staff>, initial: Int): List<Staff> {
            return staffs.mapIndexed { index, staff ->
                staffs[(index + initial) % staffs.size]
            }
        }
    }

    fun isAvailable(workDay: WorkDay, shift: Shift): Boolean {
        return !incapableShifts.contains(shift) || !incapableWorkDays.contains(workDay)
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

fun List<Staff>.nextAvailableStaff(workDay: WorkDay, shift: Shift): Staff? {
    return this.find { it.isAvailable(workDay, shift) }
}
