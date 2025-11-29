package dmitriy.losev.cs.steam.input

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import java.awt.event.KeyEvent as AwtKeyEvent

enum class KeyEvent(val nativeKeyEventCode: Int, val awtKeyCode: Int, key: String) {
    W(nativeKeyEventCode = NativeKeyEvent.VC_W, awtKeyCode = AwtKeyEvent.VK_W, key = "W"),
    A(nativeKeyEventCode = NativeKeyEvent.VC_A, awtKeyCode = AwtKeyEvent.VK_A, key = "A"),
    S(nativeKeyEventCode = NativeKeyEvent.VC_S, awtKeyCode = AwtKeyEvent.VK_S, key = "S"),
    D(nativeKeyEventCode = NativeKeyEvent.VC_D, awtKeyCode = AwtKeyEvent.VK_D, key = "D"),
    SPACE(nativeKeyEventCode = NativeKeyEvent.VC_SPACE, awtKeyCode = AwtKeyEvent.VK_SPACE, key = "Space"),
    CTRL(nativeKeyEventCode = NativeKeyEvent.VC_CONTROL, awtKeyCode = AwtKeyEvent.VK_CONTROL, key = "Ctrl"),
    SHIFT(nativeKeyEventCode = NativeKeyEvent.VC_SHIFT, awtKeyCode = AwtKeyEvent.VK_SHIFT, key = "Shift"),
    R(nativeKeyEventCode = NativeKeyEvent.VC_R, awtKeyCode = AwtKeyEvent.VK_R, key = "R"),
    E(nativeKeyEventCode = NativeKeyEvent.VC_E, awtKeyCode = AwtKeyEvent.VK_E, key = "E"),
    Q(nativeKeyEventCode = NativeKeyEvent.VC_Q, awtKeyCode = AwtKeyEvent.VK_Q, key = "Q"),
    G(nativeKeyEventCode = NativeKeyEvent.VC_G, awtKeyCode = AwtKeyEvent.VK_G, key = "G"),
    B(nativeKeyEventCode = NativeKeyEvent.VC_B, awtKeyCode = AwtKeyEvent.VK_B, key = "B"),
    SLOT_1(nativeKeyEventCode = NativeKeyEvent.VC_1, awtKeyCode = AwtKeyEvent.VK_1, key = "Slot1"),
    SLOT_2(nativeKeyEventCode = NativeKeyEvent.VC_2, awtKeyCode = AwtKeyEvent.VK_2, key = "Slot2"),
    SLOT_3(nativeKeyEventCode = NativeKeyEvent.VC_3, awtKeyCode = AwtKeyEvent.VK_3, key = "Slot3"),
    SLOT_4(nativeKeyEventCode = NativeKeyEvent.VC_4, awtKeyCode = AwtKeyEvent.VK_4, key = "Slot4"),
    SLOT_5(nativeKeyEventCode = NativeKeyEvent.VC_5, awtKeyCode = AwtKeyEvent.VK_5, key = "Slot5"),
    TAB(nativeKeyEventCode = NativeKeyEvent.VC_TAB, awtKeyCode = AwtKeyEvent.VK_TAB, key = "Tab"),
    ESC(nativeKeyEventCode = NativeKeyEvent.VC_ESCAPE, awtKeyCode = AwtKeyEvent.VK_ESCAPE, key = "Esc"),
    F(nativeKeyEventCode = NativeKeyEvent.VC_F, awtKeyCode = AwtKeyEvent.VK_F, key = "F"),
    MOUSE_BUTTON_1(nativeKeyEventCode = -1, awtKeyCode = 1, key = "MouseButton1"),
    MOUSE_BUTTON_2(nativeKeyEventCode = -2, awtKeyCode = 2, key = "MouseButton2"),
    MOUSE_BUTTON_3(nativeKeyEventCode = -3, awtKeyCode = 3, key = "MouseButton3");


    companion object {

        private val keyboardMap = entries
            .filter { it.nativeKeyEventCode >= 0 }
            .associateBy { it.nativeKeyEventCode }

        private val mouseMap = mapOf(
            1 to MOUSE_BUTTON_1,
            2 to MOUSE_BUTTON_2,
            3 to MOUSE_BUTTON_3
        )

        fun getKeyEvent(nativeKeyEvent: NativeKeyEvent): KeyEvent? {
            return keyboardMap[nativeKeyEvent.keyCode]
        }

        fun getKeyEventForMouse(nativeMouseEvent: NativeMouseEvent): KeyEvent? {
            return mouseMap[nativeMouseEvent.button]
        }
    }
}