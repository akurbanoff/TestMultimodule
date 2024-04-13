package ru.axas.core.base.compose_items.crop

internal sealed interface TouchRegion {
    enum class Vertex : TouchRegion {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    object Inside : TouchRegion
}