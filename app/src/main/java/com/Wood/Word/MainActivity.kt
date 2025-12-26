package com.Wood.Word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Wood.Word.ui.theme.WordTheme

// 你的 MainActivity.kt - 唯一的 Activity
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			WordTheme {
				// 1. 创建导航控制器（大脑）
				val navController = rememberNavController()

				// 2. 设置导航宿主（画布）并定义导航图（地图）
				NavHost(
					navController = navController,
					startDestination = "home" // 起始页面标识
				) {
					// 定义“主页”这个目的地
					composable("home") {
						MainActivity(navController) // 绘制主页内容
					}
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivity(navController: NavController) {
	val pagerState = rememberPagerState(pageCount = { 10 })

	// 使用Scaffold作为根布局
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
		// 内容区域
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			// HorizontalPager区域
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.weight(1f)
			) {
				HorizontalPager(state = pagerState) { page ->
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color(0x20808080)),
						contentAlignment = Alignment.Center
					) {
						Text(
							text = "页面 ${page + 1}",
							fontSize = 30.sp
						)
					}
				}
			}

			// 底部按钮区域
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(100.dp),
				contentAlignment = Alignment.Center
			) {
				Row(
					modifier = Modifier.fillMaxSize(),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					Button(
						onClick = { /* 按钮点击处理 */ },
						modifier = Modifier.padding(8.dp)
					) {
						Text(text = "新建")
					}
					Button(
						onClick = { /* 按钮点击处理 */ },
						modifier = Modifier
							.padding(8.dp)
					) {
						Text(text = "导入")
					}
				}
			}
		}
	}
}