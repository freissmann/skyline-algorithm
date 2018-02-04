package com.devaholics.skyline.drawing

import com.devaholics.skyline.drawing.common.Drawer
import com.devaholics.skyline.drawing.common.SkylineDrawer
import com.devaholics.skyline.tree.SkylineNode
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Line2D
import javax.swing.JComponent
import javax.swing.JFrame

/**
 * Used for showing the skyline in a [JFrame].
 *
 * It knows how to draw the given [skyline].
 *
 * @author Fabian Reißmann
 * @since 2/4/18
 */
class SkylineJFrame(private val skyline: SkylineNode) {

    // These values are used to define JFrame size and the position where the skyline will be drawn
    companion object {
        private const val ZOOM = 20F
        private const val xOffset = ZOOM
        private const val yOffset = ZOOM * 7

        private const val WIDTH = 250
        private const val HEIGHT = 200
    }

    fun show() {
        val frame = JFrame()

        frame.setSize(WIDTH, HEIGHT)

        // Center the JFrame
        frame.setLocationRelativeTo(null)

        frame.isVisible = true

        frame.add(SwingComponent(skyline))

        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(we: WindowEvent?) {
                System.exit(0)
            }
        })
    }

    private class SwingComponent(private val skyline: SkylineNode) : JComponent() {

        public override fun paintComponent(g: Graphics) {
            super.paintComponent(g)

            SkylineDrawer(SwingComponentDrawer(g as Graphics2D))
                    .drawSkyline(skyline)
        }
    }

    private class SwingComponentDrawer constructor(private val graphics2D: Graphics2D) : Drawer {

        override fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
            graphics2D.draw(Line2D.Float(
                    x1 * ZOOM + xOffset,
                    y1 * ZOOM * (-1).toFloat() + yOffset,
                    x2 * ZOOM + xOffset,
                    y2 * ZOOM * (-1).toFloat() + yOffset
            ))
        }
    }

}
