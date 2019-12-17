package com.example.test_pryaniky.domain.model;

import com.example.test_pryaniky.data.BlockType;

import java.util.List;

public class Data {

    private List<Block> listBlocks;
    private List<BlockType> listVisibleBlocksType;

    public Data(List<Block> listBlocks, List<BlockType> listVisibleBlocksType) {
        this.listBlocks = listBlocks;
        this.listVisibleBlocksType = listVisibleBlocksType;
    }

    public List<Block> getListBlocks() {
        return listBlocks;
    }

    public List<BlockType> getListVisibleBlocksType() {
        return listVisibleBlocksType;
    }
}
