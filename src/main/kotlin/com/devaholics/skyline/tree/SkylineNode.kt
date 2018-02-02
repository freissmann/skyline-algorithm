package com.devaholics.skyline.tree

/**
 * A node of the
 *
 * @author Fabian Reißmann
 * @since 1/25/18
 */
class SkylineNode(private var leftNode: SkylineNode? = null,
                  private var rightNode: SkylineNode? = null,
                  private var building: Building = Buildings.empty()) {

    /**
     * Convenient method for adding buildings easier.
     *
     * @param start  the start x-coordinate of the added building
     * @param end    the end x-coordinate of the added building
     * @param height the height of the added building
     */
    fun addBuilding(start: Number, end: Number, height: Number): SkylineNode {
        return addBuilding(Building(start.toFloat(), end.toFloat(), height.toFloat()))
    }

    /**
     * Adds the given building to the tree.
     *
     *@param added the building which will be added to the tree
     */
    fun addBuilding(added: Building): SkylineNode {
        if (added.isEmpty()) {
            return this
        }

        if (building.isEmpty()) {
            building = added

            return this
        }

        ensureLeftAndRightNodesAreNonNull()

        // The added building is far right
        if (added.isRightOf(building)) {
            rightNode?.addBuilding(added)
            return this
        }

        // The added building is far left
        if (added.isLeftOf(building)) {
            leftNode?.addBuilding(added)
            return this
        }

        handleLeftPart(added)
        handleRightPart(added)

        // Must be handled after left- and right-part, as it has the side-effect of changing this.building
        handleMiddlePart(added)

        return this
    }

    /**
     * As the [SkylineNode] is a sorted binary tree the addition can result in a linked list
     * (all added buildings far right or far left of all existing buildings) in the worst case.

     * Balancing as could improve performance in that case.
     *
     * @return a copy of this but totally balanced
     */
    fun balance(): SkylineNode {
        throw UnsupportedOperationException("TODO: Implement balancing mechanism")
    }

    /**
     * Returns the tree as sorted collapsed list. This is useful for drawing the skyline.
     *
     * Collapsed means, that nodes which are areAdjacent and have the same height are merged to one.
     *
     * @return the complete tree as a list
     */
    fun collapsedList(): List<Building> {
        val collapsedList = mutableListOf<Building>()

        var merged = Buildings.empty()

        val toList = toList()
        for (building in toList) {
            merged = if (merged.isEmpty()) {
                building
            } else if (canBeMerged(merged, building)) {
                merged.copy(end = building.end)
            } else {
                collapsedList.add(merged)
                building
            }
        }

        collapsedList.add(merged)
        return collapsedList
    }

    override fun toString(): String {
        return "$building"
    }

    private fun ensureLeftAndRightNodesAreNonNull() {
        if (rightNode == null) {
            rightNode = SkylineNode()
        }

        if (leftNode == null) {
            leftNode = SkylineNode()
        }
    }

    private fun handleLeftPart(added: Building) {
        val leftPart = building.leftPartOfBoth(added)

        leftNode?.addBuilding(leftPart)
    }

    private fun handleRightPart(added: Building) {
        val rightPart = building.rightPartOfBoth(added)

        rightNode?.addBuilding(rightPart)
    }

    private fun handleMiddlePart(added: Building) {
        val intersect = added.intersect(building)

        if (intersect.isNotEmpty()) {
            building = intersect
        }
    }

    private fun canBeMerged(left: Building, right: Building): Boolean {
        return areAdjacent(left, right)
                && left.isSameHeight(right)
    }


    private fun areAdjacent(left: Building, right: Building): Boolean {
        return left.end == right.start
    }

    private fun toList(): List<Building> {
        val list = getListOfBuilding()

        val left = leftNode?.toList()
        if (left != null && !left.isEmpty()) {
            list.addAll(0, left)
        }

        val right = rightNode?.toList()
        if (right != null && !right.isEmpty()) {
            list.addAll(right)
        }

        return list
    }


    private fun getListOfBuilding(): MutableList<Building> {
        return if (building.isEmpty()) {
            mutableListOf()
        } else {
            mutableListOf(building)
        }
    }

}
