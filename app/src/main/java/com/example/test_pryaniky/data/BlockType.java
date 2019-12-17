package com.example.test_pryaniky.data;

public enum BlockType {

    HZ("hz"),
    PICTURE("picture"),
    SELECTOR("selector");

    private String name;

    BlockType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BlockType getBlockTypeByName(String nameBlock){
        for (BlockType blockType: values()){
            if (blockType.getName().equals(nameBlock)){
                return blockType;
            }
        }
        throw new IllegalArgumentException();
    }
}
