package com.example.test_pryaniky.data;

import android.annotation.SuppressLint;

import com.example.test_pryaniky.domain.model.BlockProperty;
import com.example.test_pryaniky.domain.model.PictureBlockProperty;
import com.example.test_pryaniky.domain.model.SelectorBlockProperty;
import com.example.test_pryaniky.domain.model.TextBlockProperty;
import com.example.test_pryaniky.domain.model.Block;
import com.example.test_pryaniky.domain.model.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataJsonDeserializer implements JsonDeserializer<Data> {
    @Override
    public Data deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new Data(getListBlocks(json), getVisibleBlocksType(json));
    }

    private List<BlockType> getVisibleBlocksType(JsonElement json) {
        JsonArray jsonArrayView = json.getAsJsonObject().getAsJsonArray("view");
        List<BlockType> listView = new ArrayList<>();
        for (JsonElement jsonElement : jsonArrayView) {
            listView.add(
                    BlockType.getBlockTypeByName(jsonElement.getAsString())
            );
        }
        return listView;
    }

    private List<Block> getListBlocks(JsonElement json) {
        JsonArray jsonArrayData = json.getAsJsonObject().getAsJsonArray("data");
        List<Block> listData = new ArrayList<>();

        for (JsonElement jsonElementData : jsonArrayData) {
            BlockProperty blockProperty;
            BlockType blockType = BlockType.getBlockTypeByName(
                    jsonElementData.getAsJsonObject().get("name").getAsString()
            );

            switch (blockType) {
                case HZ:
                    blockProperty = new TextBlockProperty(jsonElementData.getAsJsonObject().
                            get("data").getAsJsonObject().get("text").getAsString()
                    );
                    break;
                case PICTURE: {
                    JsonObject jsonObjectProperty = jsonElementData.getAsJsonObject().get("data").getAsJsonObject();
                    blockProperty = new PictureBlockProperty(
                            jsonObjectProperty.get("url").getAsString(),
                            jsonObjectProperty.get("text").getAsString()
                    );
                    break;
                }
                case SELECTOR: {
                    JsonObject jsonObjectProperty = jsonElementData.getAsJsonObject().get("data").getAsJsonObject();
                    int selectedId = jsonObjectProperty.get("selectedId").getAsInt();
                    JsonArray jsonArrayVariants = jsonObjectProperty.get("variants").getAsJsonArray();
                    @SuppressLint("UseSparseArrays") Map<Integer, String> mapVariants = new HashMap<>();

                    for (JsonElement jsonElementVariant : jsonArrayVariants) {
                        JsonObject jsonObjectVariant = jsonElementVariant.getAsJsonObject();
                        mapVariants.put(
                                jsonObjectVariant.get("id").getAsInt(),
                                jsonObjectVariant.get("text").getAsString()
                        );
                    }
                    blockProperty = new SelectorBlockProperty(selectedId, mapVariants);
                    break;
                }
                default:
                    throw new IllegalStateException();
            }

            listData.add(new Block(blockType, blockProperty));
        }
        return listData;
    }
}
