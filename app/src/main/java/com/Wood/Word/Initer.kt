package com.Wood.Word

import com.Wood.Word.StaticValue
import android.content.Context
import android.util.Log
import java.io.File

object Initer {

    /**
     * 初始化应用程序数据
     */
    fun initWordLibs(context: Context) {
        initWordLibsList(context)
        loadWordLibs(context)
        initPreferences(context)
    }

    /**
     * 初始化词库文件列表
     */
    private fun initWordLibsList(context: Context) {
        // 获取所有 .dat 文件作为词库
        val files = context.filesDir.listFiles()
        val wordLibFiles = if (files != null) {
            files.filter { it.name.endsWith(".dat") }
                .map { it.name }
                .sorted()
                .toTypedArray()
        } else {
            emptyArray()
        }

        StaticValue.WordLibsName = wordLibFiles

        Log.d("Initer", "找到 ${wordLibFiles.size} 个词库文件")
        wordLibFiles.forEachIndexed { index, filename ->
            Log.d("Initer", "${index + 1}. $filename")
        }
    }

    /**
     * 加载词库到内存
     */
    private fun loadWordLibs(context: Context) {
        // 这里需要将词库加载到 StaticValue.WordLibs
        // 但 StaticValue.WordLibs 是 List 类型，需要具体实现
        // 暂时只初始化文件名列表
        Log.d("Initer", "词库文件名列表已初始化")
    }

    /**
     * 初始化首选项
     */
    private fun initPreferences(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val currentWordLib = prefs.getString("current_wordlib", null)

        if (currentWordLib != null) {
            Log.d("Initer", "当前词库: $currentWordLib")
        } else if (StaticValue.WordLibsName.isNotEmpty()) {
            Log.d("Initer", "使用第一个词库作为默认: ${StaticValue.WordLibsName[0]}")
        }
    }
}
