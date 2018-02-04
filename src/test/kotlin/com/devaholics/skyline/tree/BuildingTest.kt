package com.devaholics.skyline.tree

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.sameInstance
import org.junit.Test

/**
 * Tests for [Building].
 *
 * @author Fabian Reißmann
 * @since 1/25/18
 */
class BuildingTest {

    companion object {
        private val EMPTY = Building(0, 0, 0)

        private val ZERO_TO_ONE = Building(0, 1, 1)
        private val ZERO_TO_TWO = Building(0, 2, 1)
        private val ZERO_TO_THREE = Building(0, 3, 1)
        private val ZERO_TO_TEN = Building(0, 10, 1)

        private val ONE_TO_FOUR = Building(1, 4, 1)

        private val TWO_TO_THREE = Building(2, 3, 1)
        private val TWO_TO_FOUR = Building(2, 4, 1)

        private val THREE_TO_SIX = Building(3, 6, 1)
        private val THREE_TO_FOUR = Building(3, 4, 1)
    }


    // --------------------------------------------------------------------------


    @Test
    fun withHeight_onlyChangesHeight() {
        val withHeight = EMPTY.withHeight(5F)

        assertThat(withHeight.start, `is`(0F))
        assertThat(withHeight.end, `is`(0F))
        assertThat(withHeight.height, `is`(5F))
    }

    @Test
    fun withHeight_baseBuildingIsNotModified() {
        @Suppress("UNUSED_VARIABLE") // Justification - Indicates that this variable is intentionally ignored
        val ignored = EMPTY.withHeight(5F)

        assertThat(EMPTY.height, `is`(0F))
    }

    @Test
    fun withHeight_returnsNewInstance() {
        val withHeight = EMPTY.withHeight(5F)

        assertThat(withHeight, `is`(not(sameInstance(EMPTY))))
    }


    // --------------------------------------------------------------------------


    @Test
    fun isEmpty_withHeightZero_true() {
        val building = ZERO_TO_ONE.withHeight(0)

        val isEmpty = building.isEmpty()

        assertThat(isEmpty, `is`(true))
    }

    @Test
    fun isEmpty_withHeightNegative_true() {
        val building = ZERO_TO_ONE.withHeight(-1)

        val isEmpty = building.isEmpty()

        assertThat(isEmpty, `is`(true))
    }

    @Test
    fun isEmpty_withStartEqualsEnd_true() {
        val isEmpty = Building(1, 1, 2).isEmpty()

        assertThat(isEmpty, `is`(true))
    }

    @Test
    fun isEmpty_withZeroToOneHeightOne_false() {
        val isEmpty = ZERO_TO_ONE.isEmpty()

        assertThat(isEmpty, `is`(false))
    }


    // --------------------------------------------------------------------------


    @Test
    fun isNotEmpty_withEmpty_false() {
        val isNotEmpty = EMPTY.isNotEmpty()

        assertThat(isNotEmpty, `is`(false))
    }

    @Test
    fun isNotEmpty_withCorrectBuilding_true() {
        val isNotEmpty = ZERO_TO_ONE.isNotEmpty()

        assertThat(isNotEmpty, `is`(true))
    }


    // --------------------------------------------------------------------------


    @Test
    fun isLeftOf_withZeroToOneLeftOfTwoToThree_true() {
        val isLeftOf = ZERO_TO_ONE.isLeftOf(TWO_TO_THREE)

        assertThat(isLeftOf, `is`(true))
    }

    @Test
    fun isLeftOf_withTwoToThreeLeftOfZeroToOne_false() {
        val isLeftOf = TWO_TO_THREE.isLeftOf(ZERO_TO_ONE)

        assertThat(isLeftOf, `is`(false))
    }


    // --------------------------------------------------------------------------


    @Test
    fun isRightOf_withTwoToThreeRightOfZeroToOne_true() {
        val isRightOf = TWO_TO_THREE.isRightOf(ZERO_TO_ONE)

        assertThat(isRightOf, `is`(true))
    }

    @Test
    fun isRightOf_withZeroToOneRightOfTwoToThree_false() {
        val isRightOf = ZERO_TO_ONE.isRightOf(TWO_TO_THREE)

        assertThat(isRightOf, `is`(false))
    }


    // --------------------------------------------------------------------------


    @Test
    fun isLeftAdjacentOf_withThisTouchingThat_true() {
        val isLeftAdjacentOf = ZERO_TO_THREE.isLeftAdjacentOf(THREE_TO_FOUR)

        assertThat(isLeftAdjacentOf, `is`(true))
    }

    @Test
    fun isLeftAdjacentOf_withThisFarLeftOfThat_false() {
        val isLeftAdjacentOf = ZERO_TO_ONE.isLeftAdjacentOf(THREE_TO_FOUR)

        assertThat(isLeftAdjacentOf, `is`(false))
    }

    @Test
    fun isLeftAdjacentOf_withThisFarRightOfThat_false() {
        val isLeftAdjacentOf = THREE_TO_FOUR.isLeftAdjacentOf(ZERO_TO_ONE)

        assertThat(isLeftAdjacentOf, `is`(false))
    }

    // --------------------------------------------------------------------------


    @Test
    fun isSameHeight_withDifferentHeights_false() {
        val isSameHeight = EMPTY.withHeight(1F).isSameHeight(EMPTY.withHeight(2F))

        assertThat(isSameHeight, `is`(false))
    }

    @Test
    fun isSameHeight_withSameHeights_true() {
        val isSameHeight = EMPTY.isSameHeight(EMPTY)

        assertThat(isSameHeight, `is`(true))
    }


    // --------------------------------------------------------------------------


    @Test
    fun leftPartOfBoth_withEmpty_returnsEmpty() {
        val leftPartOfBoth = ZERO_TO_ONE.leftPartOfBoth(EMPTY)

        assertThat(leftPartOfBoth, `is`(EMPTY))
    }

    @Test
    fun leftPartOfBoth_calledOnEmpty_returnsEmpty() {
        val leftPartOfBoth = EMPTY.leftPartOfBoth(ZERO_TO_ONE)

        assertThat(leftPartOfBoth, `is`(EMPTY))
    }

    @Test
    fun leftPartOfBoth__withThisIsMoreLeft() {
        val leftPartOfBoth = ZERO_TO_THREE.leftPartOfBoth(TWO_TO_FOUR)

        assertThat(leftPartOfBoth, `is`(ZERO_TO_TWO))
    }

    @Test
    fun leftPartOfBoth__withThatIsMoreLeft() {
        val leftPartOfBoth = TWO_TO_FOUR.leftPartOfBoth(ZERO_TO_THREE)

        assertThat(leftPartOfBoth, `is`(ZERO_TO_TWO))
    }


    // --------------------------------------------------------------------------


    @Test
    fun rightPartOfBoth_withEmpty_returnsEmpty() {
        val rightPartOfBoth = ZERO_TO_ONE.rightPartOfBoth(EMPTY)

        assertThat(rightPartOfBoth, `is`(EMPTY))
    }

    @Test
    fun rightPartOfBoth_calledOnEmpty_returnsEmpty() {
        val rightPartOfBoth = EMPTY.rightPartOfBoth(ZERO_TO_ONE)

        assertThat(rightPartOfBoth, `is`(EMPTY))
    }

    @Test
    fun rightPartOfBoth__withThisIsMoreRight() {
        val rightPartOfBoth = ZERO_TO_THREE.rightPartOfBoth(TWO_TO_FOUR)

        assertThat(rightPartOfBoth, `is`(THREE_TO_FOUR))
    }

    @Test
    fun rightPartOfBoth__withThatIsMoreRight() {
        val rightPartOfBoth = TWO_TO_FOUR.rightPartOfBoth(ZERO_TO_THREE)

        assertThat(rightPartOfBoth, `is`(THREE_TO_FOUR))
    }


    // --------------------------------------------------------------------------


    @Test
    fun intersect_withAllLeft_returnsEmpty() {
        val intersect = ZERO_TO_ONE.intersect(THREE_TO_FOUR)

        assertThat(intersect, `is`(EMPTY))
    }

    @Test
    fun intersect_withAllRight_returnsEmpty() {
        val intersect = THREE_TO_FOUR.intersect(ZERO_TO_ONE)

        assertThat(intersect, `is`(EMPTY))
    }

    @Test
    fun intersect_overlappingOnBothSidesWithSmallerOnBothSides_returnsThat() {
        val intersect = ZERO_TO_TEN.intersect(THREE_TO_FOUR.withHeight(2F))

        assertThat(intersect, `is`(THREE_TO_FOUR.withHeight(2F)))
    }

    @Test
    fun intersect_overlappingOnBothSidesWithHigherOnBothSides_returnsThat() {
        val intersect = ZERO_TO_TEN.withHeight(3F).intersect(THREE_TO_FOUR)

        assertThat(intersect, `is`(THREE_TO_FOUR.withHeight(3F)))
    }

    @Test
    fun intersect_insideSmaller() {
        val intersect = TWO_TO_THREE.withHeight(2F).intersect(ONE_TO_FOUR)

        assertThat(intersect, `is`(TWO_TO_THREE.withHeight(2F)))
    }

    @Test
    fun intersect_insideBigger() {
        val intersect = TWO_TO_THREE.intersect(ONE_TO_FOUR.withHeight(2F))

        assertThat(intersect, `is`(TWO_TO_THREE.withHeight(2F)))
    }

    @Test
    fun intersect_overlappingLeftWithOverlappingHigher() {
        val intersect = ZERO_TO_THREE.withHeight(2F).intersect(TWO_TO_FOUR)

        assertThat(intersect, `is`(TWO_TO_THREE.withHeight(2F)))
    }

    @Test
    fun intersect_overlappingLeftWithOverlappingSmaller() {
        val intersect = ZERO_TO_THREE.intersect(TWO_TO_FOUR.withHeight(3F))

        assertThat(intersect, `is`(TWO_TO_THREE.withHeight(3F)))
    }

    @Test
    fun intersect_overlappingRightWithOverlappingHigher() {
        val intersect = THREE_TO_SIX.withHeight(2F).intersect(TWO_TO_FOUR)

        assertThat(intersect, `is`(THREE_TO_FOUR.withHeight(2F)))
    }

    @Test
    fun intersect_overlappingRightWithOverlappingSmaller() {
        val intersect = THREE_TO_SIX.intersect(TWO_TO_FOUR.withHeight(3F))

        assertThat(intersect, `is`(THREE_TO_FOUR.withHeight(3F)))
    }

    @Test
    fun intersect_returnsHigher() {
        val intersect = THREE_TO_SIX.intersect(TWO_TO_FOUR.withHeight(100F))

        assertThat(intersect, `is`(THREE_TO_FOUR.withHeight(100F)))
    }

}
