package com.Wood.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordItem {
    private String word;      // 单词
    private String example;   // 例句（可选）
    private List<Meaning> meanings;  // 多个含义

    // 构造方法
    public WordItem(String word) {
        this.word = word;
        this.meanings = new ArrayList<>();
        this.example = "";
    }

    public WordItem(String word, Meaning meaning) {
        this(word);
        if (meaning != null) {
            this.meanings.add(meaning);
        }
    }

    public WordItem(String word, Meaning meaning, String example) {
        this(word, meaning);
        this.example = example;
    }

    public WordItem(String word, List<Meaning> meanings) {
        this.word = word;
        this.meanings = meanings != null ? new ArrayList<>(meanings) : new ArrayList<>();
        this.example = "";
    }

    // Getter方法
    public String getWord() {
        return word;
    }

    public List<Meaning> getMeanings() {
        return new ArrayList<>(meanings);  // 返回拷贝
    }

    public Meaning[] getMeaningArray() {
        return meanings.toArray(new Meaning[0]);
    }

    public String getExample() {
        return example;
    }

    // 获取所有词性
    public Category[] getCategories() {
        return meanings.stream()
                .map(Meaning::getCategory)
                .distinct()
                .toArray(Category[]::new);
    }

    // 检查是否包含特定词性
    public boolean hasCategory(Category category) {
        return meanings.stream()
                .anyMatch(m -> m.getCategory() == category);
    }

    // Setter方法
    public void setWord(String word) {
        this.word = word;
    }

    public void addMeaning(Meaning meaning) {
        if (meaning != null) {
            meanings.add(meaning);
        }
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = new ArrayList<>(meanings);
    }

    public void setExample(String example) {
        this.example = example;
    }

    // 移除含义
    public boolean removeMeaning(Meaning meaning) {
        return meanings.remove(meaning);
    }

    // 获取含义数量
    public int getMeaningCount() {
        return meanings.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word);

        // 显示词性
        Category[] categories = getCategories();
        if (categories.length > 0) {
            sb.append(" [");
            for (int i = 0; i < categories.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(categories[i]);
            }
            sb.append("]");
        }

        sb.append(": ");

        // 显示所有含义
        for (int i = 0; i < meanings.size(); i++) {
            if (i > 0) sb.append("; ");
            sb.append(meanings.get(i));
        }

        if (example != null && !example.isEmpty()) {
            sb.append(" 例: ").append(example);
        }

        return sb.toString();
    }
}