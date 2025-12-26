package com.Wood.Word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults // 新增导入
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Wood.Word.ui.theme.WordTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordTheme {
                // 这里只需要一个Scaffold
                Greeting(name = "Android")
            }
        }
    }

    fun dateInit() {
        // 初始化代码
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 10 })

    // 使用Scaffold作为根布局
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My App") },
                navigationIcon = {
                    IconButton(onClick = { /* 处理返回或菜单点击 */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "菜单")
                    }
                },
                actions = {
                    IconButton(onClick = { /* 处理操作 */ }) {
                        Icon(Icons.Default.Search, contentDescription = "搜索")
                    }
                },
                // 使用TopAppBarDefaults自定义颜色
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0x373F3F3F), // 背景色
                    titleContentColor = Color.White, // 标题颜色
                    navigationIconContentColor = Color.White, // 导航图标颜色
                    actionIconContentColor = Color.White // 操作图标颜色
                )
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
            // HorizontalPager区域 - 占据大部分空间
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                HorizontalPager(state = pagerState) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = when (page % 3) {
                                    0 -> Color.LightGray
                                    1 -> Color(0xFFF5F1E8)
                                    else -> Color(0xFFFDE4E3)
                                }
                            ),
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
                    .height(100.dp)
                    .background(Color(0x373F3F3F)),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { /* 按钮点击处理 */ },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Button",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WordTheme {
        Greeting("Android")
    }
}