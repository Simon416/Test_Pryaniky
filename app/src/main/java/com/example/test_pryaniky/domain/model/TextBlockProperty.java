package com.example.test_pryaniky.domain.model;

import com.example.test_pryaniky.data.BlockType;

public class TextBlockProperty extends BlockProperty {

    private String text;

    public TextBlockProperty(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getBlockName() {
        return BlockType.HZ.getName();
    }
}
