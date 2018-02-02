package com.devaholics.skyline.tree

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import java.util.Random

/**
 * Tests for [SkylineNode].
 *
 * @author Fabian Reißmann
 * @since 1/25/18
 */
class SkylineNodeTest {

    companion object {
        private val B1 = Building(1, 4, 1)
        private val B2 = Building(2, 3, 2)
        private val B3 = Building(7, 8, 1.5)
        private val B4 = Building(5.5, 6.5, 1)
        private val B5 = Building(5, 7, 3)
        private val B6 = Building(3.5, 6, 1.5)
        private val B7 = Building(6, 9, 0.5)

        private val BUILDINGS = listOf(B1, B2, B3, B4, B5, B6, B7)

        private val EXPECTED_SKY_LINE = listOf(
                Building(1, 2, 1),
                Building(2, 3, 2),
                Building(3, 3.5, 1),
                Building(3.5, 5, 1.5),
                Building(5, 7, 3),
                Building(7, 8, 1.5),
                Building(8, 9, 0.5))
    }

    /**
     * Tests that the following pattern is the result of the  of the -7- buildings.
     * For each combination of the buildings.
     *
     *   ^
     * 4 |
     *   |
     * 3 |         ####
     *   |         ####
     * 2 |   ##    ####
     *   |   ## ##########
     * 1 | ###############
     *   | #################
     *   + - - - - - - - - - - >
     *     1 2 3 4 5 6 7 8 9 0
     */
    @Suppress("MemberVisibilityCanBePrivate") // Justification - Test must not be private
    @Test
    fun fullTreeExample_addedInOrder() {
        val tree = SkylineNode()
                .addBuilding(B1)
                .addBuilding(B2)
                .addBuilding(B3)
                .addBuilding(B4)
                .addBuilding(B5)
                .addBuilding(B6)
                .addBuilding(B7)

        assertSkyline(tree)
    }

    /**
     * Creates a skyline x-times with randomized building insertion order.
     *
     * For expected skyline see [fullTreeExample_addedInOrder] description above.
     *
     * Decrease this number for weaker machines (Test took ~600ms with Intel(R) Core(TM) i7-4770K CPU @ 3.50GHz).
     */
    @Test
    fun fullTreeExample_xTimes_withRandomAdditionOrder() {
        for (i in 0..200000) {
            assertSkyline(createRandomAddedBuildings())
        }
    }

    private fun assertSkyline(skyline: SkylineNode) {
        assertThat(skyline.collapsedList(), `is`(EXPECTED_SKY_LINE))
    }

    private fun createRandomAddedBuildings(): SkylineNode {
        val random = Random()

        val tree = SkylineNode()
        val remainingBuildings = BUILDINGS.toMutableList()

        while (remainingBuildings.isNotEmpty()) {
            val building = remainingBuildings.removeAt(random.nextInt(remainingBuildings.size))

            tree.addBuilding(building)
        }
        return tree
    }

    /**
     * Since randomized addition order may not test all possible insertion-orders.
     *
     * Calculate all possible permutations and use them check the resulting skyline.
     *
     * 7! = 5040
     */
    @Test
    fun fullTreeWithAllPermutations() {
        // TODO: Add test which tests all possible permutations of B1 - B7 add order
    }
}
