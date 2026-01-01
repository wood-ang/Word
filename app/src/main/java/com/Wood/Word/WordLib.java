package com.Wood.Word;

import java.util.*;
import java.util.stream.Collectors;

public class WordLib {
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
     *
     * @return 分配的ID，如果单词已存在则返回-1
     */
    public int addWord(String word, Meaning meaning) {
        String normalizedWord = word.toLowerCase();

        // 检查单词是否已存在
        if (containsWord(normalizedWord)) {
            return -1;
        }

        WordItem item = new WordItem(normalizedWord, meaning);
        return addWordItem(item);
    }

    /**
     * 添加单词（完整信息）
     *
     * @return 分配的ID，如果单词已存在则返回-1
     */
    public int addWord(String word, Meaning meaning, String example) {
        String normalizedWord = word.toLowerCase();

        if (containsWord(normalizedWord)) {
            return -1;
        }

        WordItem item = new WordItem(normalizedWord, meaning, example);
        return addWordItem(item);
    }

    /**
     * 添加WordItem对象
     *
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
     * 根据单词获取ID
     */
    public Integer getWordId(String word) {
        return wordToIdMap.get(word.toLowerCase());
    }

    /**
     * 根据词性获取单词项
     */
    public List<WordItem> getByCategory(Category category) {
        return wordMap.values().stream()
                .filter(item -> item.hasCategory(category))
                .collect(Collectors.toList());
    }

    /**
     * 更新单词项
     */
    public boolean updateWordItem(int id, WordItem newItem) {
        if (!wordMap.containsKey(id)) {
            return false;
        }

        String newWord = newItem.getWord().toLowerCase();
        Integer existingId = wordToIdMap.get(newWord);

        if (existingId != null && existingId != id) {
            return false; // 单词已被其他ID使用
        }

        // 移除旧映射
        WordItem oldItem = wordMap.get(id);
        wordToIdMap.remove(oldItem.getWord().toLowerCase());

        // 添加新映射
        wordMap.put(id, newItem);
        wordToIdMap.put(newWord, id);
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
        return new ArrayList<>(wordMap.values());
    }

    public List<String> getAllWordStrings() {
        return wordMap.values().stream()
                .map(WordItem::getWord)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有ID
     */
    public Set<Integer> getAllIds() {
        return new HashSet<>(wordMap.keySet());
    }

    /**
     * 搜索包含特定文本的单词
     */
    public List<WordItem> search(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return wordMap.values().stream()
                .filter(item -> item.getWord().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // ========== 批量操作方法 ==========

    /**
     * 批量添加单词
     */
    public Map<String, Integer> addAllWords(Map<String, List<Meaning>> wordsWithMeanings) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, List<Meaning>> entry : wordsWithMeanings.entrySet()) {
            WordItem item = new WordItem(entry.getKey(), entry.getValue());
            int id = addWordItem(item);
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

    // ========== 统计方法 ==========

    /**
     * 获取词性统计
     */
    public Map<Category, Integer> getCategoryStatistics() {
        Map<Category, Integer> stats = new HashMap<>();
        for (WordItem item : wordMap.values()) {
            for (Meaning meaning : item.getMeanings()) {
                Category cat = meaning.getCategory();
                stats.put(cat, stats.getOrDefault(cat, 0) + 1);
            }
        }
        return stats;
    }

    /**
     * 获取单词数量最多的前N个词性
     */
    public List<Map.Entry<Category, Integer>> getTopCategories(int n) {
        return getCategoryStatistics().entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(n)
                .collect(Collectors.toList());
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
     * 导入数据
     */
    public void importData(Map<Integer, WordItem> data) {
        this.wordMap.clear();
        this.wordToIdMap.clear();

        this.wordMap.putAll(data);
        for (Map.Entry<Integer, WordItem> entry : data.entrySet()) {
            wordToIdMap.put(entry.getValue().getWord().toLowerCase(), entry.getKey());
        }

        // 更新下一个ID
        this.nextId = data.keySet().stream()
                .mapToInt(Integer::intValue)
                .max().orElse(0) + 1;
    }

    /**
     * 将 WordLib 转换为格式化的字符串表示
     * 格式：每行一个单词，格式为 "ID|单词|词性:含义|例句"
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# WordLib Export Version 1.0\n");
        sb.append("# Format: ID|Word|Category:Meaning|Example\n");
        sb.append("# Multiple meanings separated by semicolons\n");

        for (Map.Entry<Integer, WordItem> entry : wordMap.entrySet()) {
            int id = entry.getKey();
            WordItem item = entry.getValue();

            // 构建含义字符串
            String meaningsStr = item.getMeanings().stream()
                    .map(m -> m.getCategory() + ":" + escapeSpecialChars(m.getMeaningText()))
                    .collect(Collectors.joining(";"));

            // 转义特殊字符
            String word = escapeSpecialChars(item.getWord());
            String example = escapeSpecialChars(item.getExample());

            sb.append(String.format("%d|%s|%s|%s\n",
                    id, word, meaningsStr, example));
        }

        return sb.toString();
    }

    /**
     * 从字符串创建 WordLib 对象（静态工厂方法）
     */
    public static WordLib fromString(String data) {
        WordLib wordLib = new WordLib();
        wordLib.importFromString(data);
        return wordLib;
    }

    public WordLib(String data) {
        this(); // 初始化数据结构
        if (data != null && !data.trim().isEmpty()) {
            importFromString(data);
        }
    }

    /**
     * 从字符串导入数据到当前 WordLib 对象
     */
    public void importFromString(String data) {
        if (data == null || data.trim().isEmpty()) {
            return;
        }

        Scanner scanner = new Scanner(data);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // 跳过空行和注释
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            try {
                // 解析行数据
                String[] parts = line.split("\\|", -1); // -1 保留空字符串
                if (parts.length < 3) {
                    continue; // 跳过格式不正确的行
                }

                int id = Integer.parseInt(parts[0].trim());
                String word = unescapeSpecialChars(parts[1].trim());
                String meaningsStr = parts[2].trim();
                String example = parts.length > 3 ? unescapeSpecialChars(parts[3].trim()) : "";

                // 解析多个含义
                List<Meaning> meanings = new ArrayList<>();
                if (!meaningsStr.isEmpty()) {
                    String[] meaningEntries = meaningsStr.split(";");
                    for (String entry : meaningEntries) {
                        String[] meaningParts = entry.split(":", 2);
                        if (meaningParts.length == 2) {
                            String categoryStr = meaningParts[0].trim();
                            String meaningText = unescapeSpecialChars(meaningParts[1].trim());

                            try {
                                Category category = Category.valueOf(categoryStr.toUpperCase());
                                meanings.add(new Meaning(meaningText, category));
                            } catch (IllegalArgumentException e) {
                                // 如果词性不合法，使用 UNSPECIFIED
                                meanings.add(new Meaning(meaningText, Category.UNSPECIFIED));
                            }
                        }
                    }
                }

                // 创建 WordItem
                WordItem item = new WordItem(word, meanings);
                item.setExample(example);

                // 手动添加到映射
                wordMap.put(id, item);
                wordToIdMap.put(word.toLowerCase(), id);

                // 更新下一个ID
                if (id >= nextId) {
                    nextId = id + 1;
                }

            } catch (Exception e) {
                // 跳过解析错误的行
                System.err.println("Warning: Failed to parse line: " + line);
            }
        }
        scanner.close();
    }

    /**
     * 转义特殊字符
     */
    private String escapeSpecialChars(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("|", "\\p")
                .replace(":", "\\c")
                .replace(";", "\\s")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    /**
     * 反转义特殊字符
     */
    private String unescapeSpecialChars(String str) {
        if (str == null) return "";
        return str.replace("\\r", "\r")
                .replace("\\n", "\n")
                .replace("\\s", ";")
                .replace("\\c", ":")
                .replace("\\p", "|")
                .replace("\\\\", "\\");
    }

    /**
     * 导出为简单文本格式（只包含单词和含义）
     */
    public String toSimpleString() {
        StringBuilder sb = new StringBuilder();
        wordMap.values().forEach(item -> {
            sb.append(item.getWord()).append(": ");
            List<Meaning> meanings = item.getMeanings();
            for (int i = 0; i < meanings.size(); i++) {
                if (i > 0) sb.append("; ");
                sb.append(meanings.get(i));
            }
            if (!item.getExample().isEmpty()) {
                sb.append(" [例: ").append(item.getExample()).append("]");
            }
            sb.append("\n");
        });
        return sb.toString();
    }
}
