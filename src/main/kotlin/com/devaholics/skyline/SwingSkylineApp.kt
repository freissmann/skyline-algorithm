package com.devaholics.skyline

import com.devaholics.skyline.drawing.SkylineJFrame
import com.devaholics.skyline.tree.Building
import com.devaholics.skyline.tree.SkylineNode

/**
 * App for running the
 *
 * @author Fabian Reißmann
 * @since 2/3/18
 */

object SwingSkylineApp {

    private val B1 = Building(1, 4, 1)
    private val B2 = Building(2, 3, 2)
    private val B3 = Building(7, 8, 1.5)
    private val B4 = Building(5.5, 6.5, 1)
    private val B5 = Building(5, 7, 3)
    private val B6 = Building(3.5, 6, 1.5)
    private val B7 = Building(6, 9, 0.5)

    @JvmStatic
    fun main(args: Array<String>) {
        val skyline = createSkyline()

        SkylineJFrame(skyline)
                .show()
    }

    private fun createSkyline(): SkylineNode {
        return SkylineNode()
                .addBuilding(B1)
                .addBuilding(B2)
                .addBuilding(B3)
                .addBuilding(B4)
                .addBuilding(B5)
                .addBuilding(B6)
                .addBuilding(B7)
    }

}
