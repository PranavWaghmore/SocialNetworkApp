package pw.coding.konnecto.presentaion.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pw.coding.konnecto.R

val quicksand=FontFamily(
            Font(R.font.quicksand_medium),
            Font(R.font.quicksand_bold),
            Font(R.font.quicksand_light),
            Font(R.font.quicksand_regular),
            Font(R.font.quicksand_semibold) ,
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ) ,
    bodyMedium = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp ,
        color = TextWhite
    ),
    bodySmall = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextGrayLighter
    ),
    displayLarge = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = TextWhite
    ),
    displayMedium = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = TextWhite
    ),
    labelMedium = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextWhite
    )
)