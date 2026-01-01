package com.Wood.Word;

import static com.Wood.Word.Category.UNSPECIFIED;

public class Meaning {
    private Category category;  // 词性（单数）
    private String meaningText;  // 含义文本（避免与类名冲突）

    public Meaning(String meaningText, Category category) {
        this.meaningText = meaningText;
        this.category = category;
    }

    public Meaning() {
        this.category = UNSPECIFIED;
        this.meaningText = "";
    }

    // Getter 方法
    public Category getCategory() {
        return category;
    }

    public String getMeaningText() {
        return meaningText;
    }

    // Setter 方法
    public void setCategory(Category category) {
        this.category = category;
    }

    public void setMeaningText(String meaningText) {
        this.meaningText = meaningText;
    }

    @Override
    public String toString() {
        return category + ": " + meaningText;
    }
}