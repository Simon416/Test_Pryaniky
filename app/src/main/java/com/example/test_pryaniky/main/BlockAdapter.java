package com.example.test_pryaniky.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test_pryaniky.R;
import com.example.test_pryaniky.domain.model.BlockProperty;
import com.example.test_pryaniky.domain.model.PictureBlockProperty;
import com.example.test_pryaniky.domain.model.SelectorBlockProperty;
import com.example.test_pryaniky.domain.model.TextBlockProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BaseViewHolder> {

    interface OnClickListener {
        void onClickBlock(String blockName);
    }

    private static final int TYPE_ITEM_TEXT = 0;
    private static final int TYPE_ITEM_PICTURE = 1;
    private static final int TYPE_ITEM_SELECTOR = 2;
    private OnClickListener onClickListener;

    private List<BlockProperty> blockPropertyList;

    BlockAdapter(List<BlockProperty> blockPropertyList, OnClickListener onClickListener) {
        this.blockPropertyList = blockPropertyList;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (blockPropertyList.get(position) instanceof TextBlockProperty) {
            return TYPE_ITEM_TEXT;
        } else if (blockPropertyList.get(position) instanceof PictureBlockProperty) {
            return TYPE_ITEM_PICTURE;
        } else if (blockPropertyList.get(position) instanceof SelectorBlockProperty) {
            return TYPE_ITEM_SELECTOR;
        } else {
            throw new IllegalStateException();
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM_TEXT:
                return new TextViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view_block_text,parent,false));
            case TYPE_ITEM_PICTURE:
                return new PictureHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view_block_picture, parent, false));
            case TYPE_ITEM_SELECTOR:
                return new SelectorHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view_block_selector, parent, false));
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        BlockProperty blockProperty = blockPropertyList.get(position);
        switch (getItemViewType(position)) {
            case TYPE_ITEM_TEXT:
                ((TextViewHolder) holder).onBind((TextBlockProperty) blockProperty);
                break;
            case TYPE_ITEM_PICTURE:
                ((PictureHolder) holder).onBind((PictureBlockProperty) blockProperty);
                break;
            case TYPE_ITEM_SELECTOR:
                ((SelectorHolder) holder).onBind((SelectorBlockProperty) blockProperty);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public int getItemCount() {
        return blockPropertyList.size();
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClickBlock(blockPropertyList.get(getAdapterPosition()).getBlockName());
        }
    }

    class TextViewHolder extends BaseViewHolder {
        private TextView textView;

        TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
        void onBind(TextBlockProperty blockText) {
            textView.append(blockText.getText());
        }
    }

    class PictureHolder extends BaseViewHolder {
        private TextView txPictureView;
        private ImageView imPictureView;

        PictureHolder(@NonNull View itemView) {
            super(itemView);
            txPictureView = itemView.findViewById(R.id.txPictureView);
            imPictureView = itemView.findViewById(R.id.imPictureView);
        }

        void onBind(PictureBlockProperty blockPicture) {
            txPictureView.append(blockPicture.getText());
            Glide.with(itemView.getContext())
                    .load(blockPicture.getUrl())
                    .into(imPictureView);
        }
    }

    class SelectorHolder extends BaseViewHolder {
        private TextView txSelectorView;
        private Spinner spinner;

        SelectorHolder(@NonNull View itemView) {
            super(itemView);
            txSelectorView = itemView.findViewById(R.id.txSelectorView);
            spinner = itemView.findViewById(R.id.spinner);
        }

        void onBind(SelectorBlockProperty blockSelector) {
            txSelectorView.append(Integer.toString(blockSelector.getSelectedId()));
            initSpinner(blockSelector);
        }

        private void initSpinner(SelectorBlockProperty blockSelector) {
            Map<Integer, String> idByName = blockSelector.getIdByName();
            List<String> listValues = new ArrayList<>(idByName.values());
            String[] arrValues = listValues.toArray(new String[0]);
            String selectValue = blockSelector.getIdByName().get(blockSelector.getSelectedId());
            int selectIndexAdapter = listValues.indexOf(selectValue);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(itemView.getContext(),
                    R.layout.spinner_item, arrValues);
            adapter.setDropDownViewResource(R.layout.spinner_item);

            spinner.setAdapter(adapter);
            spinner.setSelection(selectIndexAdapter);
        }
    }
}
