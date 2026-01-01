package com.Wood.Word.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 核心颜色 - 金色/黄色 (#E3A618)
private val GoldenYellow = Color(0xFFE3A618)  // 主色
private val DeepGold = Color(0xFFC88C0B)     // 深金色
private val LightGold = Color(0xFFFFC145)    // 浅金色
private val WarmBeige = Color(0xFFF5E6CA)    // 暖米色
private val DarkBrown = Color(0xFF2C2416)    // 深棕色
private val WarmGray = Color(0xFF756D5A)     // 暖灰色
private val RichBrown = Color(0xFF8B7355)    // 浓郁棕色

// 浅色主题 - 以金色为核心

// 浅色主题 - 以金色为核心
private val LightColorScheme = lightColorScheme(
    // 主要品牌颜色
    primary = GoldenYellow,
    onPrimary = Color.White,  // 金色上的文字用白色

    // 主要容器颜色（用于按钮、选中状态等）
    primaryContainer = LightGold,
    onPrimaryContainer = Color(0xFF261900),

    // 次要颜色（用于浮动按钮、开关等）
    secondary = RichBrown,
    onSecondary = Color.White,

    // 次要容器颜色
    secondaryContainer = WarmBeige,
    onSecondaryContainer = Color(0xFF2D2818),

    // 第三颜色（用于强调）
    tertiary = Color(0xFF7D5260),
    onTertiary = Color.White,

    // 第三容器颜色
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),

    // 背景颜色
    background = Color(0xFFFFFBFF),  // 稍微暖白的背景
    onBackground = Color(0xFF1C1B1F),

    // 表面颜色（卡片、对话框、菜单等）
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1C1B1F),

    // 表面变体（工具栏、底部导航等）
    surfaceVariant = WarmBeige,
    onSurfaceVariant = Color(0xFF49454F),

    // 轮廓颜色（边框、分割线）
    outline = Color(0xFF7D766A),
    outlineVariant = Color(0xFFCAC4D0),

    // 错误颜色
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    // 逆色
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = Color(0xFFFFD54F),  // 浅金色作为逆主色

    // 表面色调（用于浮动按钮等）
    surfaceTint = GoldenYellow
)

// 深色主题 - 以金色为核心
private val DarkColorScheme = darkColorScheme(
    // 主要品牌颜色（在深色主题中使用更亮的金色）
    primary = LightGold,
    onPrimary = Color(0xFF402D00),

    // 主要容器颜色
    primaryContainer = DeepGold,
    onPrimaryContainer = Color(0xFFFFDEA5),

    // 次要颜色
    secondary = Color(0xFFD9C3A0),
    onSecondary = Color(0xFF3B2F1C),

    // 次要容器颜色
    secondaryContainer = WarmGray,
    onSecondaryContainer = Color(0xFFE7DFC6),

    // 第三颜色
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),

    // 第三容器颜色
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),

    // 背景颜色（深棕色背景）
    background = DarkBrown,
    onBackground = Color(0xFFE7E1E5),

    // 表面颜色
    surface = DarkBrown,
    onSurface = Color(0xFFE7E1E5),

    // 表面变体
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),

    // 轮廓颜色
    outline = Color(0xFF948F99),
    outlineVariant = Color(0xFF49454F),

    // 错误颜色
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // 逆色
    inverseSurface = Color(0xFFE7E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF806500),  // 深金色作为逆主色

    // 表面色调
    surfaceTint = LightGold
)
@Composable
fun WordTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 完全移除动态颜色判断，始终使用自定义主题
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val MaterialTheme.customColors: CustomColors
    @Composable
    get() = CustomColors

// 自定义颜色对象，可以用于特殊场景
object CustomColors {
    // 核心金色系列
    val goldenYellow = GoldenYellow
    val deepGold = DeepGold
    val lightGold = LightGold

    // 中性色系列
    val warmBeige = WarmBeige
    val darkBrown = DarkBrown
    val warmGray = WarmGray
    val richBrown = RichBrown

    // 辅助色
    val successGreen = Color(0xFF4CAF50)
    val warningOrange = Color(0xFFFF9800)
    val infoBlue = Color(0xFF2196F3)

    // 文本颜色
    val textPrimary = Color(0xFF212121)
    val textSecondary = Color(0xFF757575)
    val textDisabled = Color(0xFFBDBDBD)
}