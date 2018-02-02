package com.devaholics.skyline.tree

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.sameInstance
import org.junit.Test

/**
 * Test for [Buildings].
 *
 * @author Fabian Reißmann
 * @since 2/2/18
 */
class BuildingsTest {

    companion object {
        private val FROM_ONE_TO_TWO_HEIGHT_THREE = Building(1, 2, 3)
    }

    @Test
    fun empty_isEmpty_true() {
        val empty = Buildings.empty()

        val isEmpty = empty.isEmpty()

        assertThat(isEmpty, `is`(true))
    }

    @Test
    fun empty_returnsAlwaysSameInstance() {
        val first = Buildings.empty()
        val second = Buildings.empty()

        assertThat(first, `is`(sameInstance(second)))
    }


    // --------------------------------------------------------------------------


    @Test
    fun create_withCorrectFloatString_building() {
        val building = Buildings.create("[1.0,2.0,3.0]")

        assertThat(building, `is`(FROM_ONE_TO_TWO_HEIGHT_THREE))
    }

    @Test
    fun create_withCorrectIntString_building() {
        val building = Buildings.create("[1,2,3]")

        assertThat(building, `is`(FROM_ONE_TO_TWO_HEIGHT_THREE))
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withZeroHeight_throws() {
        Buildings.create("[1,2,0]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withNegativeStart_throws() {
        Buildings.create("[-1,2,0]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withNegativeEnd_throws() {
        Buildings.create("[1,-2,0]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withNegativeHeight_throws() {
        Buildings.create("[1,2,-1]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withStartGreaterThanEnd_throws() {
        Buildings.create("[5,1,3]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withEmptyString_throws() {
        Buildings.create("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withNonBuildingPattern_throws() {
        Buildings.create("[1,2,2,3]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withoutStart_throws() {
        Buildings.create("[,1,3]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withoutEnd_throws() {
        Buildings.create("[1,,3]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_withoutHeight_throws() {
        Buildings.create("[1,3,]")
    }
}
