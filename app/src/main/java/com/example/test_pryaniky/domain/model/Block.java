package com.example.test_pryaniky.domain.model;

import com.example.test_pryaniky.data.BlockType;

public class Block {

    private BlockType type;
    private BlockProperty property;

    public Block(BlockType type, BlockProperty property) {
        this.type = type;
        this.property = property;
    }

    public BlockType getType() {
        return type;
    }

    public BlockProperty getProperty() {
        return property;
    }
}
