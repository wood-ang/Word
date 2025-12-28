package com.Wood.Word;

import android.os.Build;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WordLib {

    // 内部类：单词项
    public static class WordItem {
        private String word;      // 单词
        private String meaning;   // 含义
        private String example;   // 例句（可选）
        private Category[] category;  // 词性/分类

        // 构造函数
        public WordItem(String word, String meaning) {
            this.word = word;
            this.meaning = meaning;
        }

        public WordItem(String word, String meaning, String example, Category[] category) {
            this.word = word;
            this.meaning = meaning;
            this.example = example;
            this.category = category;
        }

        // Getter方法
        public String getWord() { return word; }
        public String getMeaning() { return meaning; }
        public String getExample() { return example; }
        public Category[] getCategory() { return category; }

        // Setter方法
        public void setWord(String word) { this.word = word; }
        public void setMeaning(String meaning) { this.meaning = meaning; }
        public void setExample(String example) { this.example = example; }
        public void setCategory(Category[] category) { this.category = category; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(word);

            if (category != null && !(category.length ==0)) {
                sb.append(" [").append(category).append("]");
            }

            sb.append(": ").append(meaning);

            if (example != null && !example.isEmpty()) {
                sb.append(" 例: ").append(example);
            }

            return sb.toString();
        }
    }

    // 核心数据结构
    private final Map<Integer, WordItem> wordMap;  // ID -> WordItem
    private final Map<String, Integer> wordToIdMap; // 单词 -> ID（快速查找）

    private int nextId = 1;  // 下一个可用的ID

    // 构造函数
    public WordLib() {
        this.wordMap = new HashMap<>();
        this.wordToIdMap = new HashMap<>();
    }

    public WordLib(int initialCapacity) {
        this.wordMap = new HashMap<>(initialCapacity);
        this.wordToIdMap = new HashMap<>(initialCapacity);
    }

    // ========== 核心操作方法 ==========

    /**
     * 添加单词（自动生成ID）
     * @return 分配的ID，如果单词已存在则返回-1
     */
    public int addWord(String word, String meaning) {
        // 检查单词是否已存在（不区分大小写）
        if (containsWord(word)) {
            return -1;
        }

        WordItem item = new WordItem(word, meaning);
        return addWordItem(item);
    }

    /**
     * 添加单词（完整信息）
     * @return 分配的ID，如果单词已存在则返回-1
     */
    public int addWord(String word, String meaning, String example, Category[] category) {
        if (containsWord(word)) {
            return -1;
        }

        WordItem item = new WordItem(word, meaning, example, category);
        return addWordItem(item);
    }

    /**
     * 添加WordItem对象
     * @return 分配的ID
     */
    private int addWordItem(WordItem item) {
        int id = nextId++;
        wordMap.put(id, item);
        wordToIdMap.put(item.getWord().toLowerCase(), id);
        return id;
    }

    /**
     * 根据ID获取单词项
     */
    public WordItem getById(int id) {
        return wordMap.get(id);
    }

    /**
     * 根据单词获取单词项
     */
    public WordItem getByWord(String word) {
        Integer id = wordToIdMap.get(word.toLowerCase());
        return id != null ? wordMap.get(id) : null;
    }

    /**
     * 根据ID获取单词含义
     */
    public String getMeaning(int id) {
        WordItem item = wordMap.get(id);
        return item != null ? item.getMeaning() : null;
    }

    /**
     * 根据单词获取含义
     */
    public String getMeaning(String word) {
        WordItem item = getByWord(word);
        return item != null ? item.getMeaning() : null;
    }

    /**
     * 更新单词项
     */
    public boolean updateWordItem(int id, WordItem newItem) {
        if (!wordMap.containsKey(id)) {
            return false;
        }

        // 如果要更新的单词已经存在且不是当前ID的单词
        Integer existingId = wordToIdMap.get(newItem.getWord().toLowerCase());
        if (existingId != null && existingId != id) {
            return false; // 单词已被其他ID使用
        }

        // 移除旧单词的映射
        WordItem oldItem = wordMap.get(id);
        wordToIdMap.remove(oldItem.getWord().toLowerCase());

        // 添加新映射
        wordMap.put(id, newItem);
        wordToIdMap.put(newItem.getWord().toLowerCase(), id);
        return true;
    }

    /**
     * 根据ID删除单词
     */
    public WordItem removeById(int id) {
        WordItem removed = wordMap.remove(id);
        if (removed != null) {
            wordToIdMap.remove(removed.getWord().toLowerCase());
        }
        return removed;
    }

    /**
     * 根据单词删除
     */
    public WordItem removeByWord(String word) {
        Integer id = wordToIdMap.remove(word.toLowerCase());
        return id != null ? wordMap.remove(id) : null;
    }

    // ========== 查询方法 ==========

    public boolean containsId(int id) {
        return wordMap.containsKey(id);
    }

    public boolean containsWord(String word) {
        return wordToIdMap.containsKey(word.toLowerCase());
    }

    public int size() {
        return wordMap.size();
    }

    public boolean isEmpty() {
        return wordMap.isEmpty();
    }

    /**
     * 获取所有单词项
     */
    public Collection<WordItem> getAllWords() {
        return wordMap.values();
    }

    /**
     * 获取所有ID
     */
    public Set<Integer> getAllIds() {
        return wordMap.keySet();
    }

    // ========== 批量操作方法 ==========

    /**
     * 批量添加单词（单词-含义对）
     */
    public Map<String, Integer> addAllWords(Map<String, String> wordMeanings) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, String> entry : wordMeanings.entrySet()) {
            int id = addWord(entry.getKey(), entry.getValue());
            if (id != -1) {
                result.put(entry.getKey(), id);
            }
        }
        return result;
    }

    /**
     * 清空词库
     */
    public void clear() {
        wordMap.clear();
        wordToIdMap.clear();
        nextId = 1;
    }

    /**
     * 导出为简单Map（ID -> 单词）
     */
    public Map<Integer, String> exportIdToWordMap() {
        Map<Integer, String> result = new HashMap<>();
        for (Map.Entry<Integer, WordItem> entry : wordMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getWord());
        }
        return result;
    }

    /**
     * 导出为单词到含义的Map
     */
    public Map<String, String> exportWordToMeaningMap() {
        Map<String, String> result = new HashMap<>();
        for (WordItem item : wordMap.values()) {
            result.put(item.getWord(), item.getMeaning());
        }
        return result;
    }

    // ========== 显示方法 ==========

    /**
     * 打印所有单词
     */
    public void printAll() {
        System.out.println("====== 单词库（共" + size() + "个单词）======");
        wordMap.forEach((id, item) ->
                System.out.printf("%4d: %s%n", id, item.toString())
        );
        System.out.println("==================================");
    }

    /**
     * 格式化显示所有单词
     */
    public String formatAllWords() {
        StringBuilder sb = new StringBuilder();
        sb.append("单词库（共").append(size()).append("个单词）\n");
        wordMap.forEach((id, item) ->
                sb.append(String.format("%4d: %s%n", id, item.toString()))
        );
        return sb.toString();
    }

    public enum Category {
        N,
        V,
        ADJ,
        ADV,
        PRON,
        PREP,
        CONJ,
        ART,
        NUM,
        INTERJ,
        AUX_V,
        ONOMATOPOEIA
    }
}