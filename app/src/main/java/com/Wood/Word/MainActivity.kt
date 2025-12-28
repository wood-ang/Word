package com.Wood.Word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Wood.Word.ui.theme.CustomColors
import com.Wood.Word.ui.theme.WordTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			WordTheme {
				// 1. 创建导航控制器
				val navController = rememberNavController()

				// 2. 设置导航宿主
				NavHost(
					navController = navController,
					startDestination = "home"
				) {
					composable(
						route = "home",
						exitTransition = { // 添加这整个exitTransition块
							// 当离开主页前往创建页时，主页向左滑出
							slideOutHorizontally(
								targetOffsetX = { -it }, // 向左滑动（负方向）
								animationSpec = tween(durationMillis = 350)
							)
						},
						popEnterTransition = { // 添加返回时的进入动画
							// 当从创建页返回时，主页从右侧滑入
							slideInHorizontally(
								initialOffsetX = { it }, // 从右侧滑入（正方向）
								animationSpec = tween(durationMillis = 350)
							)
						}
					) {
						HomeScreen(navController)
					}

					composable(
						route = "create",
						enterTransition = {
							slideInHorizontally(
								initialOffsetX = { it }, // 从右侧滑入
								animationSpec = tween(durationMillis = 350)
							)
						},
						popExitTransition = { // 添加返回时的退出动画
							// 当从创建页返回时，创建页向左滑出
							slideOutHorizontally(
								targetOffsetX = { -it }, // 向左滑动
								animationSpec = tween(durationMillis = 350)
							)
						}
					) {
						CreateScreen(navController)
					}

					composable(
						route = "bate",
						enterTransition = {
							slideInHorizontally(
								initialOffsetX = { it }, // 从右侧滑入
								animationSpec = tween(durationMillis = 350)
							)
						},
						popExitTransition = { // 添加返回时的退出动画
							// 当从创建页返回时，创建页向左滑出
							slideOutHorizontally(
								targetOffsetX = { -it }, // 向左滑动
								animationSpec = tween(durationMillis = 350)
							)
						}
					) {
						BateScreen(navController)
					}
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) { 
	val pagerState = rememberPagerState(pageCount = { 10 })

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "Word") },
				actions = {
					IconButton(onClick = { /* 处理操作 */ }) {
						Icon(Icons.Default.Settings, contentDescription = "设置")
					}
					IconButton(
						onClick = { navController.navigate("bate") }
					) {
						Icon(Icons.Default.Build, contentDescription = "开发选项")
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
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
						Column(
							horizontalAlignment = Alignment.CenterHorizontally,
							verticalArrangement = Arrangement.spacedBy(16.dp)
						) {
							Text(
								text = "页面 ${page + 1}",
								fontSize = 30.sp
							)
							Button(onClick = {

							}) {
								Text("查看详情")
							}
						}
					}
				}
			}

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
					OutlinedButton(
						onClick = { navController.navigate("create") },
						modifier = Modifier.padding(8.dp)
					) {
						Text(text = "新建")
					}
					OutlinedButton(
						onClick = { /* 导入功能 */ },
						modifier = Modifier.padding(8.dp)
					) {
						Text(text = "导入")
					}
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(navController: NavController) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "创建") },
				navigationIcon = {
					IconButton(onClick = { navController.navigateUp() }) {
						Icon(
							Icons.Default.ArrowBack,
							contentDescription = "返回"
						)
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = "创建新内容页面",
				fontSize = 24.sp,
				modifier = Modifier.padding(bottom = 16.dp)
			)
			OutlinedTextField(
				value = "",
				onValueChange = {},
				label = { Text("标题") },
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 16.dp, vertical = 8.dp)
			)
			Button(
				onClick = { navController.navigateUp() },
				modifier = Modifier.padding(top = 24.dp)
			) {
				Text("完成创建")
			}
		}
	}
}


@Composable
fun BateScreen(navController: NavController) {
	WordTheme {
		Surface(
			modifier = Modifier.fillMaxSize(),
			color = MaterialTheme.colorScheme.background
		) {
			Column(
				modifier = Modifier.padding(16.dp),
				verticalArrangement = Arrangement.spacedBy(16.dp)
			) {
				// 使用金色主题的组件
				Text(
					text = "金色主题标题",
					style = MaterialTheme.typography.headlineLarge,
					color = MaterialTheme.colorScheme.primary
				)

				Text(
					text = "这是一个以金色(E3A618)为核心的主题",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurface
				)

				// 金色按钮
				Button(
					onClick = { /* Do something */ },
					colors = ButtonDefaults.buttonColors(
						containerColor = MaterialTheme.colorScheme.primary,
						contentColor = MaterialTheme.colorScheme.onPrimary
					)
				) {
					Text("金色按钮")
				}

				// 次要按钮
				OutlinedButton(
					onClick = { /* Do something */ }
				) {
					Text("轮廓按钮")
				}

				// 金色卡片
				Card(
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.surfaceVariant
					)
				) {
					Column(modifier = Modifier.padding(16.dp)) {
						Text(
							"金色卡片",
							style = MaterialTheme.typography.titleLarge,
							color = MaterialTheme.colorScheme.primary
						)
						Text(
							"使用暖米色作为卡片背景",
							style = MaterialTheme.typography.bodyMedium
						)
					}
				}

				// 使用自定义颜色
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(50.dp)
						.background(CustomColors.richBrown)
				) {
					Text(
						"自定义浓郁棕色",
						modifier = Modifier.align(Alignment.Center),
						color = Color.White
					)
				}
			}
		}
	}
}













































































































