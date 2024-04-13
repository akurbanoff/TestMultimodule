package ru.axas.core.theme.res

import androidx.compose.foundation.shape.RoundedCornerShape
import ru.axas.core.theme.ShapeScheme

object ShapesApp {

    val Shapes = ShapeScheme(
        smallTop = RoundedCornerShape(
            topStart = DimApp.smallShapeSize,
            topEnd = DimApp.smallShapeSize
        ),
        smallAll = RoundedCornerShape(DimApp.smallShapeSize),
        smallRight = RoundedCornerShape(
            topEnd = DimApp.smallShapeSize,
            bottomEnd = DimApp.smallShapeSize
        ),
        mediumTop = RoundedCornerShape(
            topStart = DimApp.mediumShapeSize,
            topEnd = DimApp.mediumShapeSize
        ),
        mediumBottom = RoundedCornerShape(
            bottomStart = DimApp.mediumShapeSize,
            bottomEnd = DimApp.mediumShapeSize
        ),
        mediumAll = RoundedCornerShape(DimApp.mediumShapeSize),
        mediumLeft = RoundedCornerShape(
            topStart = DimApp.mediumShapeSize,
            bottomStart = DimApp.mediumShapeSize
        ),
        mediumRight = RoundedCornerShape(
            topEnd = DimApp.mediumShapeSize,
            bottomEnd = DimApp.mediumShapeSize
        ),
        smallBottom = RoundedCornerShape(
            bottomStart = DimApp.smallShapeSize,
            bottomEnd = DimApp.smallShapeSize
        )
    )
}