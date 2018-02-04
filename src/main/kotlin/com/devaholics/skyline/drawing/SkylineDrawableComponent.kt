package com.devaholics.skyline.drawing

import com.devaholics.skyline.tree.Building
import com.devaholics.skyline.tree.SkylineNode
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Line2D
import javax.swing.JComponent


/**
 *  Used for drawing the
 *
 * @author Fabian Reißmann
 * @since 1/21/18
 */
class SkylineDrawableComponent constructor(private val skyline: SkylineNode) : JComponent() {

    // TODO: Think about making this values parameter of a constructor, to improve flexibility
    companion object {
        private const val ZOOM = 20F
        private const val xOffset = ZOOM
        private const val yOffset = ZOOM * 17
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        drawSkyline(g as Graphics2D)
    }

    private fun drawSkyline(g2d: Graphics2D) {
        val buildings = skyline.collapsedList()

        if (buildings.isEmpty()) {
            return
        }

        buildings.forEachIndexed { i, it ->
            if (isFirst(i)) {
                drawLeftSide(g2d, it)
            } else {
                if (isLast(i, buildings)) {
                    drawRightSide(g2d, it)
                }

                val previous = buildings[i - 1]
                if (previous.isLeftAdjacentOf(it)) {
                    drawLeftSideConnection(g2d, previous, it)
                }
            }

            g2d.draw(topSide(it))
        }
    }

    private fun isFirst(i: Int) = i == 0
    private fun isLast(i: Int, buildings: List<Building>) = i == buildings.lastIndex

    private fun drawLeftSide(g2d: Graphics2D, it: Building) {
        g2d.draw(leftSide(it))
    }

    private fun drawRightSide(g2d: Graphics2D, it: Building) {
        g2d.draw(rightSide(it))
    }

    private fun drawLeftSideConnection(g2d: Graphics2D, previous: Building, it: Building) {
        g2d.draw(drawLeftSideConnectionOfIt(previous, it))
    }

    private fun drawLeftSideConnectionOfIt(previous: Building, it: Building): Line2D.Float {
        return scaledLine(previous.end, previous.height, it.start, it.height)
    }

    private fun leftSide(building: Building) = scaledLine(building.start, 0F, building.start, building.height)
    private fun topSide(building: Building) = scaledLine(building.start, building.height, building.end, building.height)
    private fun rightSide(building: Building) = scaledLine(building.end, 0F, building.end, building.height)

    /**
     * This method scales the line a little to better use the full JFrame space.
     */
    private fun scaledLine(x1: Float, y1: Float, x2: Float, y2: Float): Line2D.Float {
        return Line2D.Float(
                x1 * ZOOM + xOffset,
                y1 * ZOOM * (-1).toFloat() + yOffset,
                x2 * ZOOM + xOffset,
                y2 * ZOOM * (-1).toFloat() + yOffset)
    }
}
