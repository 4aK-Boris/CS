package dmitriy.losev.cs.steam.input

import kotlinx.serialization.Serializable

// Data classes для сериализации
@Serializable
data class InputTick(
    val tick: Int,
    val time: Double,
    val w: Boolean,
    val a: Boolean,
    val s: Boolean,
    val d: Boolean,
    val space: Boolean,
    val ctrl: Boolean,
    val shift: Boolean,
    val mouse1: Boolean,
    val mouse2: Boolean,
    val mouse3: Boolean,
    val mouseX: Int,
    val mouseY: Int,
    val r: Boolean,
    val e: Boolean,
    val q: Boolean,
    val g: Boolean,
    val b: Boolean,
    val slot1: Boolean,
    val slot2: Boolean,
    val slot3: Boolean,
    val slot4: Boolean,
    val slot5: Boolean,
    val tab: Boolean,
    val esc: Boolean,
    val f: Boolean
) {

    private fun Boolean.toInt() = if (this) 1 else 0

    override fun toString(): String {
        return "$tick,$time," +
                "${w.toInt()},${a.toInt()},${s.toInt()},${d.toInt()}," +
                "${space.toInt()},${ctrl.toInt()},${shift.toInt()}," +
                "${mouse1.toInt()},${mouse2.toInt()},${mouse3.toInt()}," +
                "$mouseX,$mouseY," +
                "${r.toInt()},${e.toInt()},${q.toInt()},${g.toInt()},${b.toInt()}," +
                "${slot1.toInt()},${slot2.toInt()},${slot3.toInt()},${slot4.toInt()},${slot5.toInt()}," +
                "${tab.toInt()},${esc.toInt()},${f.toInt()}"
    }

    companion object {

        private fun String.toBool(): Boolean {
            return toInt() == 1
        }

        fun toInputTick(line: String): InputTick {
            val values = line.split(',')
            return InputTick(
                tick = values[0].toInt(),
                time = values[1].toDouble(),
                w = values[2].toBool(),
                a = values[3].toBool(),
                s = values[4].toBool(),
                d = values[5].toBool(),
                space = values[6].toBool(),
                ctrl = values[7].toBool(),
                shift = values[8].toBool(),
                mouse1 = values[9].toBool(),
                mouse2 = values[10].toBool(),
                mouse3 = values[11].toBool(),
                mouseX = values[12].toInt(),
                mouseY = values[13].toInt(),
                r = values[14].toBool(),
                e = values[15].toBool(),
                q = values[16].toBool(),
                g = values[17].toBool(),
                b = values[18].toBool(),
                slot1 = values[19].toBool(),
                slot2 = values[20].toBool(),
                slot3 = values[21].toBool(),
                slot4 = values[22].toBool(),
                slot5 = values[23].toBool(),
                tab = values[24].toBool(),
                esc = values[25].toBool(),
                f = values[26].toBool()
            )
        }
    }
}