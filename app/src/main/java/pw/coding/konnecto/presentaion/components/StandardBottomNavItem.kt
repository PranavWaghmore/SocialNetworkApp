 package pw.coding.konnecto.presentaion.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pw.coding.konnecto.presentaion.ui.theme.LargeSpace
import pw.coding.konnecto.presentaion.ui.theme.SmallSpace
import pw.coding.konnecto.presentaion.ui.theme.TextWhite

@Composable
@Throws(IllegalArgumentException::class)
fun RowScope.StandardBottomNavItem(
    icon: ImageVector?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alertCount: Int? = null,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = TextWhite,
    enabled: Boolean = true,
    onClick: () -> Unit,
){
    if(alertCount!=null && alertCount<0){
        throw IllegalArgumentException("AlertCount can't be negative")
    }

    val lineLength = animateFloatAsState(
        targetValue = if(selected) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    NavigationBarItem(
        modifier = modifier.
        padding(
           start= SmallSpace,
           end= SmallSpace,
           bottom = LargeSpace,
        ),
        selected = selected,
        onClick=onClick,
        enabled=enabled,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = selectedColor,
            unselectedIconColor = unselectedColor,
            indicatorColor = Color.Transparent
        ),
        icon = {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SmallSpace)
                    .drawBehind{
                      if(lineLength.value > 0f) {
                            drawLine(
                                color = if (selected) selectedColor
                                else unselectedColor,
                                start = Offset(
                                    x=(size.width / 2f - lineLength.value * 15.dp.toPx()),
                                    y= size.height
                                ),
                                end = Offset(
                                    x=size.width / 2f + lineLength.value * 15.dp.toPx(),
                                    y=size.height
                                ),
                                strokeWidth = 2.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                      }

                    }
            ){
                if(icon!=null){
                    Icon(
                        imageVector = icon,
                        contentDescription=contentDescription,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(30.dp)
                    )
                }
                if(alertCount!=null){
                    val alertText=if(alertCount>99){
                        "99+"
                    }else{
                        alertCount.toString()
                    }
                    Text(
                        text = alertText,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(9.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    )
}