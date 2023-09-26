package com.example.testapplication.core.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.customClickable(
    onClick: () -> Unit,
    isActive: Boolean = true,
    noIndication: Boolean = false,
    bounded: Boolean = false,
) = composed {
    clickable(
        onClick = onClick,
        indication = if (isActive && !noIndication) rememberRipple(bounded = bounded) else null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = isActive,
        onClickLabel = null,
        role = null,
    )
}


fun Modifier.addCardShadow(cornersRadius: Dp = 12.dp) = advancedShadow(
    alpha = 0.08f, offsetY = (1.dp), shadowBlurRadius = 15.dp, cornersRadius = cornersRadius
)

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 0f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {
    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()
    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(), offsetX.toPx(), offsetY.toPx(), shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}