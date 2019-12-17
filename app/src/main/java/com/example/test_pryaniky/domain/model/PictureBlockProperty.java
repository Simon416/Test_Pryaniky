package com.example.test_pryaniky.domain.model;

import com.example.test_pryaniky.data.BlockType;

public class PictureBlockProperty extends BlockProperty {

    private String url;
    private String text;

    public PictureBlockProperty(String url, String text) {
        this.url = url;
        this.text = text;
    }

    @Override
    public String getBlockName() {
        return BlockType.PICTURE.getName();
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
