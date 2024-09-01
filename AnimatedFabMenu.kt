package com.codingWithUmair.AnimatedFabMenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.kashif.myapplication.R

@Composable
fun AnimatedFabMenu(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    items: List<FabItem>,
    onItemClick: (FabItem) -> Unit,
    fabColor: Color = MaterialTheme.colorScheme.tertiary,
    menuColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    expandedWidth: Dp = 180.dp,
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    val dpAnimationSpec = tween<Dp>(300, easing = LinearEasing)
    val floatAnimationSpec = tween<Float>(300, easing = LinearEasing)

    val fabSize by animateDpAsState(
        targetValue = if (isFabExpanded) expandedWidth else 75.dp,
        animationSpec = dpAnimationSpec, label = ""
    )

    val fabHeight by animateDpAsState(
        targetValue = if (isFabExpanded) 60.dp else 75.dp,
        animationSpec = dpAnimationSpec, label = ""
    )

    val menuHeight by animateDpAsState(
        targetValue = if (isFabExpanded) (items.size * 60).dp else 0.dp,
        animationSpec = dpAnimationSpec, label = ""
    )

    ElevatedCard(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors().copy(containerColor = menuColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.height(menuHeight)
        ) {
            items.forEach { item ->
                AnimatedVisibility(
                    visible = isFabExpanded,
                    enter = fadeIn(floatAnimationSpec),
                    exit = fadeOut(floatAnimationSpec)
                ) {
                    Row(
                        modifier = Modifier
                            .width(fabSize)
                            .height(60.dp)
                            .clickable { onItemClick(item) },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = item.text),
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = fabColor,
            onClick = { isFabExpanded = !isFabExpanded },
            modifier = Modifier
                .size(fabSize, fabHeight)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FabPreview() {
    val items = listOf(
        FabItem(icon = R.drawable.ic_launcher_foreground, text = R.string.app_name),
        FabItem(icon = R.drawable.ic_launcher_foreground, text = R.string.app_name),
        FabItem(icon = R.drawable.ic_launcher_foreground, text = R.string.app_name),
        FabItem(icon = R.drawable.ic_launcher_foreground, text = R.string.app_name),
        FabItem(icon = R.drawable.ic_launcher_foreground, text = R.string.app_name),
        FabItem(icon = R.drawable.ic_launcher_foreground, text = R.string.app_name)
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedFabMenu(
            icon = R.drawable.ic_launcher_foreground,
            text = R.string.app_name,
            items = items,
            onItemClick = { }
        )
    }
}
data class FabItem(
    @DrawableRes val icon: Int,
    @StringRes val text: Int
)
