package com.devaholics.skyline.tree

import java.util.regex.Pattern

/**
 * Utility-Class for creating [Building]s.
 *
 * @author Fabian Reißmann
 * @since 2/2/18
 */
object Buildings {

    private val EMPTY: Building = Building(0, 0, 0)

    private const val ONE_PART_REGEX: String = "(\\d+(?:.\\d+)?)"

    private val BUILDING_PATTERN: Pattern = Pattern.compile("\\[$ONE_PART_REGEX,$ONE_PART_REGEX,$ONE_PART_REGEX]")

    /**
     * Convenient method for retrieving an empty [Building].
     *
     * Will always return the same instance, as [Building] is immutable.
     *
     * @return always same EMPTY instance
     */
    fun empty(): Building {
        return EMPTY
    }

    /**
     * Tries to create a [Building] from the given String.
     *
     * A correct building looks like "[1,2,3]".
     *
     * @return building with the values from the String
     * @throws IllegalArgumentException in case the String could not be parsed
     */
    fun create(buildingAsString: String): Building {
        if (buildingAsString.isBlank()) {
            throw IllegalArgumentException("Blank String can not be parsed to a building")
        }

        if (hasNegativeValues(buildingAsString)) {
            throw IllegalArgumentException("The values must not be negative")
        }

        val matcher = BUILDING_PATTERN.matcher(buildingAsString)

        if (matcher.find()) {
            val start = matcher.group(1).toFloat()
            val end = matcher.group(2).toFloat()
            val height = matcher.group(3).toFloat()

            assertMinimumHeight(height)
            assertStartBeforeTo(start, end)

            return Building(start, end, height)
        }

        throw IllegalArgumentException("The given '$buildingAsString' could not be parsed")
    }

    private fun hasNegativeValues(buildingAsString: String) = buildingAsString.contains("-")

    private fun assertMinimumHeight(height: Float) {
        if (isBuildingTooFlat(height)) {
            throw IllegalArgumentException("Building must not be flat or below ground")
        }
    }

    private fun assertStartBeforeTo(start: Float, end: Float) {
        if (start > end) {
            throw IllegalArgumentException("Building's 'start' must be at most 'end' to be valid")
        }
    }

    private fun isBuildingTooFlat(height: Float): Boolean {
        return height <= 0
    }
}
