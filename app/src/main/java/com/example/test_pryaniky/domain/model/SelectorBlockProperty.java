package com.example.test_pryaniky.domain.model;

import com.example.test_pryaniky.data.BlockType;

import java.util.Map;

public class SelectorBlockProperty extends BlockProperty {

    private Map<Integer, String> idByName;
    private int selectedId;

    public SelectorBlockProperty(int selectedId, Map<Integer, String> idByName) {
        this.selectedId = selectedId;
        this.idByName = idByName;
    }

    @Override
    public String getBlockName() {
        return BlockType.SELECTOR.getName();
    }

    public int getSelectedId() {
        return selectedId;
    }

    public Map<Integer, String> getIdByName() {
        return idByName;
    }
}
