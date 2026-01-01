package com.Wood.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticValue {
    // 存储所有词库对象的列表
    public static List<WordLib> WordLibs = new ArrayList<>();

    // 存储词库文件名与对象的映射
    public static Map<String, WordLib> WordLibMap = new HashMap<>();

    // 词库文件名数组
    public static String[] WordLibsName = new String[0];

    // 当前选中的词库
    public static String CurrentWordLib = null;
}
