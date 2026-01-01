package com.Wood.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordRoot {
    private List<WordItem> words;

    public WordRoot() {
        this.words = new ArrayList<>();
    }

    public WordRoot(WordRoot other) {
        this.words = new ArrayList<>(other.words);
    }

    public WordRoot(WordItem wordItem) {
        this();
        if (wordItem != null) {
            this.words.add(wordItem);
        }
    }

    public WordRoot(List<WordItem> wordItems) {
        this.words = new ArrayList<>(wordItems);
    }

    public WordRoot(WordItem[] wordItems) {
        this();
        if (wordItems != null) {
            for (WordItem item : wordItems) {
                if (item != null) {
                    this.words.add(item);
                }
            }
        }
    }

    // 添加单词项
    public void addWordItem(WordItem wordItem) {
        if (wordItem != null) {
            words.add(wordItem);
        }
    }

    // 移除单词项
    public boolean removeWordItem(WordItem wordItem) {
        return words.remove(wordItem);
    }

    public boolean removeWordItem(String word) {
        return words.removeIf(item -> item.getWord().equalsIgnoreCase(word));
    }

    // 根据词性获取单词项
    public List<WordItem> getByCategory(Category category) {
        return words.stream()
                .filter(item -> item.hasCategory(category))
                .collect(Collectors.toList());
    }

    // 获取所有单词
    public List<WordItem> getAllWords() {
        return new ArrayList<>(words);
    }

    // 获取所有单词字符串
    public List<String> getAllWordStrings() {
        return words.stream()
                .map(WordItem::getWord)
                .collect(Collectors.toList());
    }

    // 搜索单词
    public List<WordItem> search(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return words.stream()
                .filter(item -> item.getWord().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // 获取单词数量
    public int size() {
        return words.size();
    }

    // 是否为空
    public boolean isEmpty() {
        return words.isEmpty();
    }

    // 清空
    public void clear() {
        words.clear();
    }

    // 合并另一个WordRoot
    public void merge(WordRoot other) {
        if (other != null) {
            for (WordItem item : other.words) {
                // 避免重复添加相同的单词
                if (!containsWord(item.getWord())) {
                    words.add(item);
                }
            }
        }
    }

    // 检查是否包含单词
    public boolean containsWord(String word) {
        return words.stream()
                .anyMatch(item -> item.getWord().equalsIgnoreCase(word));
    }

    // 获取词性统计
    public Map<Category, Integer> getCategoryStatistics() {
        Map<Category, Integer> stats = new HashMap<>();
        for (WordItem item : words) {
            for (Meaning meaning : item.getMeanings()) {
                Category cat = meaning.getCategory();
                stats.put(cat, stats.getOrDefault(cat, 0) + 1);
            }
        }
        return stats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WordRoot (包含 ").append(size()).append(" 个单词):\n");
        for (int i = 0; i < words.size(); i++) {
            sb.append(i + 1).append(". ").append(words.get(i)).append("\n");
        }
        return sb.toString();
    }

    // 转换为WordLib
    public WordLib toWordLib() {
        WordLib wordLib = new WordLib(this.size());
        for (WordItem item : words) {
            wordLib.addWord(item.getWord(), item.getMeanings().get(0));
        }
        return wordLib;
    }
}