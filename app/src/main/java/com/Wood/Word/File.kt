package com.Wood.Word

import com.Wood.Word.WordLib
import android.content.Context
import android.util.Log
import java.io.File

fun makeWordLib(name: String, context: Context) {
    try {
        val file = File(context.filesDir, "$name.dat")
        if (file.createNewFile()) {
            Log.d("File", "创建词库文件成功: $name.dat")
        } else {
            Log.d("File", "词库文件已存在: $name.dat")
        }
    } catch (e: Exception) {
        Log.e("File", "创建词库文件失败: ${e.message}")
        e.printStackTrace()
    }
}

fun getWordLib(name: String, context: Context): WordLib {
    return try {
        val file = File(context.filesDir, "$name.dat")
        if (!file.exists()) {
            Log.e("File", "文件不存在: $name.dat")
            return WordLib()
        }

        val content = file.readText()
        val result = WordLib(content)
        Log.d("File", "读取词库成功: $name.dat (${result.size()} 个单词)")
        result
    } catch (e: Exception) {
        Log.e("File", "读取文件失败: ${e.message}")
        WordLib()
    }
}

fun saveWordLib(name: String, wordLib: WordLib, context: Context) {
    try {
        val content = wordLib.toString()
        val file = File(context.filesDir, "$name.dat")
        file.writeText(content)
        Log.d("File", "保存词库成功: $name.dat (${wordLib.size()} 个单词)")
    } catch (e: Exception) {
        Log.e("File", "保存词库失败: ${e.message}")
    }
}

fun saveWordLib(name: String, content: String, context: Context) {
    try {
        val file = File(context.filesDir, "$name.dat")
        file.writeText(content)
        Log.d("File", "保存词库成功: $name.dat")
    } catch (e: Exception) {
        Log.e("File", "保存词库失败: ${e.message}")
    }
}
