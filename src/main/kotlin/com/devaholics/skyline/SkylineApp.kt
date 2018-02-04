package com.devaholics.skyline

import com.devaholics.skyline.drawing.SkylineDrawableComponent
import com.devaholics.skyline.tree.Building
import com.devaholics.skyline.tree.Buildings
import com.devaholics.skyline.tree.SkylineNode
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

/**
 * TODO: comment
 *
 * @author Fabian Reißmann
 * @since 2/3/18
 */

object SkylineApp {

    private val B1 = Building(1, 4, 1)
    private val B2 = Building(2, 3, 2)
    private val B3 = Building(7, 8, 1.5)
    private val B4 = Building(5.5, 6.5, 1)
    private val B5 = Building(5, 7, 3)
    private val B6 = Building(3.5, 6, 1.5)
    private val B7 = Building(6, 9, 0.5)


    @JvmStatic
    fun main(args: Array<String>) {

        val skyline = SkylineNode()
                .addBuilding(Buildings.create("[1.0,4,1.0]"))
                .addBuilding(B2)
                .addBuilding(B3)
                .addBuilding(B4)
                .addBuilding(B5)
                .addBuilding(B6)
                .addBuilding(B7)

        val frame = JFrame()
        frame.setSize(800, 400)
        frame.isVisible = true

        frame.add(SkylineDrawableComponent(skyline))

        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(we: WindowEvent?) {
                System.exit(0)
            }
        })

    }

}
