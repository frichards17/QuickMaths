package com.frankrichards.quickmaths.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

public val FontFamily.Quicksand: FontFamily
    get(){
        return quicksand
    }



// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    titleMedium = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    titleSmall = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    labelSmall = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )

)

val Typography.button: TextStyle
    get() = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )

val Typography.buttonSmall: TextStyle
    get() = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )

val Typography.targetNum: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    )

val Typography.error: TextStyle
    get() = TextStyle(
        fontFamily = quicksand,
        fontSize = 18.sp,
        color = Red
    )

val Typography.calculation: TextStyle
    get() = TextStyle(
        fontFamily = quicksand,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
