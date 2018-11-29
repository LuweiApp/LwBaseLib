package com.luwei.recyclerview.adapter.multitype;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.luwei.recyclerview.adapter.extension.FooterExtension;
import com.luwei.recyclerview.adapter.extension.HeaderExtension;

import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/2
 */
public class LwAdapter extends MultiTypeAdapter {

    private HeaderExtension mHeader;
    private FooterExtension mFooter;

    public LwAdapter() {
    }

    public LwAdapter(Items items) {
        super(items);
    }

    @Override
    public final int getItemViewType(int position) {
        Object item = null;
        int headerSize = getHeaderSize();
        int mainSize = getItems().size();
        if (mHeader != null) {
            if (mHeader.isInRange(getItemCount(), position)) {
                item = mHeader.getItem(position);
                return indexInTypesOf(position, item);
            }
        }
        if (mFooter != null) {
            if (mFooter.isInRange(getItemCount(), position)) {
                int relativePos = position - headerSize - mainSize;
                item = mFooter.getItem(relativePos);
                return indexInTypesOf(relativePos, item);
            }
        }
        int relativePos = position - headerSize;
        return super.getItemViewType(relativePos);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        Object item = null;
        int headerSize = getHeaderSize();
        int mainSize = getItems().size();
        ItemViewBinder binder = getTypePool().getItemViewBinder(holder.getItemViewType());
        if (mHeader != null) {
            if (mHeader.isInRange(getItemCount(), position)) {
                item = mHeader.getItem(position);
            }
        }
        if (mFooter != null) {
            if (mFooter.isInRange(getItemCount(), position)) {
                int relativePos = position - headerSize - mainSize;
                item = mFooter.getItem(relativePos);
            }
        }
        if (item != null) {
            binder.onBindViewHolder(holder, item);
            return;
        }
        super.onBindViewHolder(holder, position - headerSize, payloads);
    }


    /**
     * 获取Header数量
     *
     * @return
     */
    public int getHeaderSize() {
        return mHeader == null ? 0 : mHeader.getItemSize();
    }

    /**
     * 获取Footer数量
     *
     * @return
     */
    public int getFooterSize() {
        return mFooter == null ? 0 : mFooter.getItemSize();
    }

    @Override
    public final int getItemCount() {
        int extensionSize = getHeaderSize() + getFooterSize();
        return super.getItemCount() + extensionSize;
    }

    /**
     * @return 返回 HeaderExtension 实例
     */
    public HeaderExtension getHeader() {
        return mHeader;
    }

    public FooterExtension getFooter() {
        return mFooter;
    }

    /**
     * 添加Footer
     *
     * @param o Header item
     */
    public LwAdapter addHeader(Object o) {
        createHeader();
        mHeader.add(o);
        notifyItemRangeInserted(getHeaderSize() - 1, 1);
        return this;
    }

    /**
     * 增加Header数据集
     *
     * @param items Header 的数据集
     */
    public LwAdapter addHeader(Items items) {
        createHeader();
        mHeader.addAll(items);
        notifyItemRangeInserted(getHeaderSize() - 1, items.size());
        return this;
    }

    /**
     * 添加Footer
     *
     * @param o Footer item
     */
    public LwAdapter addFooter(Object o) {
        createFooter();
        mFooter.add(o);
        notifyItemInserted(getItemCount() + getHeaderSize() + getFooterSize() - 1);
        return this;
    }

    /**
     * 增加Footer数据集
     *
     * @param items Footer 的数据集
     */
    public LwAdapter addFooter(Items items) {
        createFooter();
        mFooter.addAll(items);
        notifyItemRangeInserted(getFooterSize() - 1, items.size());
        return this;
    }

    private void createHeader() {
        if (mHeader == null) {
            mHeader = new HeaderExtension();
        }
    }

    private void createFooter() {
        if (mFooter == null) {
            mFooter = new FooterExtension();
        }
    }

}

