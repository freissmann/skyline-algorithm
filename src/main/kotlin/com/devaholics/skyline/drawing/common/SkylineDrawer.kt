package com.devaholics.skyline.drawing.common

import com.devaholics.skyline.tree.Building
import com.devaholics.skyline.tree.SkylineNode

/**
 * Hold the full algorithm for drawing [SkylineNode]s.
 *
 * Must not hav any dependencies to the actual drawing mechanism.
 *
 * @author Fabian Reißmann
 * @since 2/4/18
 */
class SkylineDrawer constructor(private val drawer: Drawer) {

    /**
     * Draws the [skyline] by using the given [drawer].
     */
    fun drawSkyline(skyline: SkylineNode) {
        val buildings = skyline.collapsedList()

        if (buildings.isEmpty()) {
            return
        }

        buildings.forEachIndexed { i, it ->
            if (isFirst(i)) {
                drawLeftSide(it)
            } else {
                if (isLast(i, buildings)) {
                    drawRightSide(it)
                }

                val previous = buildings[i - 1]
                if (previous.isLeftAdjacentOf(it)) {
                    drawLeftSideConnectionOfIt(previous, it)
                }
            }

            // Always draw the top
            drawTopSide(it)
        }
    }

    private fun isFirst(i: Int) = i == 0
    private fun isLast(i: Int, buildings: List<Building>) = i == buildings.lastIndex

    private fun drawLeftSideConnectionOfIt(previous: Building, it: Building) {
        drawer.drawLine(previous.end, previous.height, it.start, it.height)
    }

    private fun drawLeftSide(building: Building) {
        drawer.drawLine(building.start, 0F, building.start, building.height)
    }

    private fun drawTopSide(building: Building) {
        drawer.drawLine(building.start, building.height, building.end, building.height)
    }

    private fun drawRightSide(building: Building) {
        drawer.drawLine(building.end, 0F, building.end, building.height)
    }

}
