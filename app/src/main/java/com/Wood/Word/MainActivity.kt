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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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

        Initer.initWordLibs(this)

        setContent {
            WordTheme {
                // 1. 创建导航控制器
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable(
                            route = "home",
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(durationMillis = 350)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -it },
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
                                    initialOffsetX = { -it },
                                    animationSpec = tween(durationMillis = 350)
                                )
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
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
                                    initialOffsetX = { -it },
                                    animationSpec = tween(durationMillis = 350)
                                )
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(durationMillis = 350)
                                )
                            }
                        ) {
                            DebugScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val pagerState = rememberPagerState(
        pageCount = { StaticValue.WordLibsName.size + 1 }
    )

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
                            Button(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .shadow(4.dp),
                                shape = RoundedCornerShape(4.dp),
                                onClick = {  },
                            ) {

                                Text(
                                    text = "页面 ${page + 1}",
                                    fontSize = 30.sp
                                )

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(navController: NavController) {
    // 状态管理
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        // 1. 顶部应用栏 (TopAppBar)
        topBar = {
            TopAppBar(
                title = { Text("应用标题") },
                actions = {
                    IconButton(onClick = { /* 操作 */ }) {
                        Icon(Icons.Default.Search, "搜索")
                    }
                    IconButton(onClick = { /* 操作 */ }) {
                        Icon(Icons.Default.MoreVert, "更多")
                    }
                }
            )
        },

        // 2. 底部导航栏
        bottomBar = {
            NavigationBar {
                val items = listOf("首页", "搜索", "个人")
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            when (index) {
                                0 -> Icon(Icons.Default.Home, item)
                                1 -> Icon(Icons.Default.Search, item)
                                2 -> Icon(Icons.Default.Settings, item)
                                else -> Icon(Icons.Default.Home, item)
                            }
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },

        // 3. 浮动操作按钮 (FloatingActionButton)
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* 点击事件 */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "添加")
            }
        },

        // 4. 浮动操作按钮位置
        floatingActionButtonPosition = FabPosition.End,

        // 主要内容
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
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
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("金色按钮")
                    }

                    // 次要按钮
                    OutlinedButton(
                        onClick = { /* Do something */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("轮廓按钮")
                    }

                    // 金色卡片
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "金色卡片",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "使用暖米色作为卡片背景",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // 使用自定义颜色
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = CustomColors.richBrown
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "自定义浓郁棕色",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}