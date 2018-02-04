package com.devaholics.skyline.tree

/**
 * Represents one building. A building is defined by a start and end x-coordinate with a height.
 *
 * All values _must_ be positive.
 *
 * Instances of [Building] are _immutable_.
 *
 * @author Fabian Reißmann
 * @since 1/21/18
 */
data class Building constructor(val start: Float,
                                val end: Float,
                                val height: Float) {

    /**
     * Convenient constructor for creating buildings easier.
     *
     * @param start  the start x-coordinate of the building
     * @param end    the end x-coordinate of the building
     * @param height the height of the building
     */
    constructor(start: Number, end: Number, height: Number) : this(start.toFloat(), end.toFloat(), height.toFloat())

    /**
     * Creates a [Building][copy] with the given height.
     *
     * @return copy of this with height set
     */
    fun withHeight(height: Number): Building {
        return copy(height = height.toFloat())
    }

    /**
     * Checks whether or not the given building is empty.
     *
     * A building is empty, if the end == start or the height is less or equal to zero.
     *
     * @return true, if the building is empty; false, otherwise
     */
    fun isEmpty(): Boolean {
        return end == start
                || height <= 0F
    }

    /**
     * Convenient method for checking whether a building is NOT empty.
     * See [Building][isEmpty] for description, when a building is empty.
     *
     * @return true, if the building is NOT empty; false, otherwise
     */
    fun isNotEmpty(): Boolean {
        return !isEmpty()
    }

    /**
     * Checks if _this_ is all left of _that_.
     *
     * @return true, if they are at most touching; false, if they overlap
     */
    fun isLeftOf(that: Building): Boolean {
        return this.end <= that.start
    }

    /**
     * Checks if _this_ is all right of _that_.
     *
     * @return true, if they too are at most touching; false, if they overlap
     */
    fun isRightOf(that: Building): Boolean {
        return this.start >= that.end
    }

    /**
     * Checks if _this_ is touching _that_ on _that's_ left side.
     *
     * @return true, if they are only touching; false, if they overlap or are separated
     */
    fun isLeftAdjacentOf(that: Building): Boolean {
        return this.end == that.start
    }

    /**
     * Checks if _this_ and _that_ have the same height.
     *
     * @return true, if they have the same height; false, otherwise
     */
    fun isSameHeight(that: Building): Boolean {
        return this.height == that.height
    }

    /**
     * Returns the overlapping non intersected part of both buildings.
     *
     *  For instance:
     *  A with '*' from (2 -> 4.5 with height 1.5)
     *  B with '+' from (4 ->   7 with height 2.5)
     *
     *  '#' is where both buildings are
     *  '-' is the left part of both  (  2 ->   4 with height 1.5)
     *  '=' is the right part of both (4.5 ->   7 with height 2.5)
     *  '$' is the intersecting part  (  4 -> 4.5 with height 2.5) <-- takes the higher of both
     *
     *       ----$=====
     *
     *   ^
     * 3 |
     *   |       ++++++
     * 2 |       ++++++
     *   |   ****#++B++
     * 1 |   *A**#+++++
     *   |   ****#+++++
     *   + - - - - - - - >
     *     1 2 3 4 5 6 7
     *
     * @return the most left overlapping of both
     */
    fun leftPartOfBoth(that: Building): Building {
        if (this.isEmpty() || that.isEmpty()) {
            return Buildings.empty()
        }

        val leftPartHeight = if (that.start < this.start) {
            that.height
        } else {
            this.height
        }

        val start = Math.min(this.start, that.start)
        val end = Math.max(this.start, that.start)

        return orEmpty(start, end, leftPartHeight)
    }

    /**
     * Returns the overlapping non intersected part of both buildings.
     * See [Building][leftPartOfBoth] for visual example.
     *
     * @return the most right overlapping of both
     */
    fun rightPartOfBoth(that: Building): Building {
        if (this.isEmpty() || that.isEmpty()) {
            return Buildings.empty()
        }

        val rightPartHeight = if (that.end > this.end) {
            that.height
        } else {
            this.height
        }

        val start = Math.min(this.end, that.end)
        val end = Math.max(this.end, that.end)

        return orEmpty(start, end, rightPartHeight)
    }

    /**
     * Returns the intersected part of both buildings, but with the higher height.
     * See [Building][leftPartOfBoth] for visual example.
     *
     * @return the part were both buildings are located
     */
    fun intersect(that: Building): Building {
        if (this.isLeftOf(that) || this.isRightOf(that)) {
            return Buildings.empty()
        }

        val isOverlappingLeft = this.isOverlappingLeftOf(that)
        val isOverlappingRight = this.isOverlappingRightOf(that)

        val maxHeight = Math.max(this.height, that.height)

        return if (isOverlappingLeft && isOverlappingRight) {
            // [this] is overlapping on both sides of [that]
            orEmpty(that.start, that.end, maxHeight)
        } else if (isOverlappingLeft) {
            // [this] is only overlapping on the left of [that]
            orEmpty(that.start, this.end, maxHeight)
        } else if (isOverlappingRight) {
            // [this] is only overlapping on the right of [that]
            orEmpty(this.start, that.end, maxHeight)
        } else {
            // [this] building is fully inside of [that] building
            orEmpty(this.start, this.end, maxHeight)
        }
    }

    override fun toString(): String {
        if (isEmpty()) {
            return ""
        }

        return "($start / $end / $height)"
    }

    /**
     * Checks if _this_ is overlapping to the left of _that_.
     */
    private fun isOverlappingLeftOf(that: Building): Boolean {
        return this.end > that.start
                && this.start < that.start
    }

    /**
     * Checks if _this_ is overlapping to the right of _that_.
     */
    private fun isOverlappingRightOf(that: Building): Boolean {
        return that.end > start && end > that.end
    }

    /**
     * Helper method for ensuring a building with valid start and end.
     *
     * If the start or end are in an invalid combination, [Buildings.empty] is used instead.
     */
    private fun orEmpty(start: Float, end: Float, height: Float = this.height): Building {
        val building = Building(start, end, height)

        return if (building.isEmpty()) {
            Buildings.empty()
        } else {
            building
        }
    }
}


