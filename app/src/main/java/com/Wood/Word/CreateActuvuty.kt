package com.Wood.Word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.Wood.Word.ui.theme.WordTheme

class CreateActuvuty : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			WordTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					Greeting2(
						name = "Android",
						modifier = Modifier.padding(innerPadding)
					)
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "Word") },
				actions = {
					IconButton(onClick = { /* 处理操作 */ }) {
						Icon(Icons.Default.Search, contentDescription = "搜索")
					}
				},
			)
		}
	) { innerPadding ->
		Row(
			modifier = Modifier.fillMaxSize(),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				painter = painterResource(id = R.drawable.stylus_fountain_pen),
				contentDescription = "收藏"
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
	WordTheme {
		Greeting2("Android")
	}
}