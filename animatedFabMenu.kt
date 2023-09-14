package com.codingwithumair.app.vidcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedFabMenu(){

	var isFabExpanded by remember {
		mutableStateOf(false)
	}

	val fabTransition = updateTransition(targetState = isFabExpanded, label = "")

	val expandFabHorizontally by fabTransition.animateDp(
		transitionSpec = { tween(200, 50, LinearEasing) }, label = "",
		targetValueByState = {isExpanded ->
			if (isExpanded) 155.dp else 80.dp
		}
	)

	val shrinkFabVertically by fabTransition.animateDp(
		transitionSpec = { tween(200, 50, LinearEasing) }, label = "",
		targetValueByState = {isExpanded ->
			if (isExpanded) 60.dp else 80.dp
		}
	)

	val menuHeight by fabTransition.animateDp(
		transitionSpec = { tween(150, 100, LinearEasing) }, label = "",
		targetValueByState = {isExpanded ->
			if (isExpanded) 200.dp else 0.dp
		}
	)


	Scaffold(
		floatingActionButton = {
			ElevatedCard(
				modifier = Modifier.padding(4.dp),
				shape = RoundedCornerShape(20.dp)
			) {
				AnimatedVisibility(
					visible = isFabExpanded,
					enter = fadeIn(tween(700, 200, LinearEasing)),
					exit = fadeOut(tween(500, 0, LinearEasing)),
				){
					Column(
						modifier = Modifier
							.height(menuHeight)
					){
						(1..4).forEach {
							Row(
								modifier = Modifier
									.width(expandFabHorizontally),
								horizontalArrangement = Arrangement.Center,
								verticalAlignment = Alignment.CenterVertically
							) {
								Icon(
									Icons.Default.Menu,
									contentDescription = null,
									modifier = Modifier.padding(12.dp)
								)
								Spacer(modifier = Modifier.weight(1f))
								Text(text = "item $it", modifier = Modifier.padding(end = 12.dp))
							}
						}
					}

				}
				FloatingActionButton(
					onClick = { isFabExpanded = !isFabExpanded },
					modifier = Modifier
						.width(expandFabHorizontally)
						.height(shrinkFabVertically)
				) {
					Row(
						horizontalArrangement = Arrangement.Center,
						verticalAlignment = Alignment.CenterVertically
					){
						Icon(Icons.Default.Menu, contentDescription = null, modifier = Modifier.padding(12.dp))
						AnimatedVisibility(
							visible = isFabExpanded,
							enter = fadeIn(tween(300, 100, LinearEasing)),
							exit = fadeOut(tween(150, 50, LinearEasing))
						) {
							Text(text = "Expanded Menu", modifier = Modifier.padding(end = 12.dp))
						}
					}
				}
			}
		}
	){ paddingValues ->
		LazyColumn(Modifier.padding(paddingValues)){
			items(20){
				Text(text = "item $it")
			}
		}
	}
}
