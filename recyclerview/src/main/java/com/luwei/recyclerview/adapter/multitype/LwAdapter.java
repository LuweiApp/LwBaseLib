package com.luwei.recyclerview.adapter.multitype;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luwei.recyclerview.adapter.extension.EmptyBean;
import com.luwei.recyclerview.adapter.extension.EmptyBinder;
import com.luwei.recyclerview.adapter.extension.EmptyBinderReal;
import com.luwei.recyclerview.adapter.extension.EmptyExtension;
import com.luwei.recyclerview.adapter.extension.FooterExtension;
import com.luwei.recyclerview.adapter.extension.HeaderExtension;
import com.luwei.recyclerview.adapter.extension.SimpleEmptyBinder;

import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/2
 */
public class LwAdapter extends MultiTypeAdapter {

    private HeaderExtension mHeader;
    private FooterExtension mFooter;
    private EmptyExtension mEmpty;
    private static AdapterConfig mConfig;

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
        if (mHeader != null && mHeader.isInRange(getItemCount(), position)) {
            item = mHeader.getItem(position);
            return indexInTypesOf(position, item);
        }
        if (mEmpty != null && mEmpty.isInRange(getItemCount(), position)) {
            item = mEmpty.getItem(position);
            return indexInTypesOf(position, item);
        }
        if (mFooter != null && mFooter.isInRange(getItemCount(), position)) {
            int relativePos = position - headerSize - mainSize - getEmptySize();
            item = mFooter.getItem(relativePos);
            return indexInTypesOf(relativePos, item);
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
        if (mHeader != null && mHeader.isInRange(getItemCount(), position)) {
            item = mHeader.getItem(position);
        }
        if (mFooter != null && mFooter.isInRange(getItemCount(), position)) {
            int relativePos = position - headerSize - mainSize - getEmptySize();
            item = mFooter.getItem(relativePos);
        }
        if (mEmpty != null && mEmpty.isInRange(getItemCount(), position)) {
            item = mEmpty.getItem(position);
        }
        if (item != null) {
            binder.onBindViewHolder(holder, item);
            return;
        }
        super.onBindViewHolder(holder, position - headerSize, payloads);
    }


    /**
     * 是否显示缺省view
     */
    public void setEmptyViewEnable(boolean showEmptyView) {
        if (showEmptyView) {
            setEmptyBinder(null);
        } else {
            getTypePool().unregister(EmptyBean.class);
            mEmpty = null;
        }
    }

    /**
     * 设置缺省view
     */
    public void setEmptyView(View view) {
        setEmptyBinder((inflater, parent) -> view);
    }

    /**
     * 设置缺省view
     */
    public void setEmptyView(@LayoutRes int viewRes) {
        setEmptyBinder((inflater, parent) -> inflater.inflate(viewRes, parent, false));
    }

    /**
     * 设置缺省view
     */
    public void setEmptyBinder(EmptyBinder emptyBinder) {
        if (emptyBinder == null) {
            emptyBinder = EmptyExtension.getGlobalBinder();
        }
        if (emptyBinder==null){
            emptyBinder = new SimpleEmptyBinder();
        }
        mEmpty = new EmptyExtension(this);
        register(EmptyBean.class, new EmptyBinderReal(emptyBinder));
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

    public int getEmptySize() {
        return mEmpty == null ? 0 : mEmpty.getItemSize();
    }

    @Override
    public final int getItemCount() {
        int extensionSize = getHeaderSize() + getFooterSize() + getEmptySize();
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


    public static AdapterConfig getConfig() {
        if (mConfig == null) {
            synchronized (LwAdapter.class) {
                if (mConfig == null) {
                    mConfig = new AdapterConfig();
                }
            }
        }
        return mConfig;
    }

    public static class AdapterConfig {

        /**
         * 设置缺省view
         */
        public AdapterConfig setGlobalEmptyView(@LayoutRes int viewRes) {
            setGlobalEmptyBinder((inflater, parent) -> inflater.inflate(viewRes, parent, false));
            return this;
        }

        /**
         * 设置缺省view
         */
        public AdapterConfig setGlobalEmptyBinder(EmptyBinder emptyBinder) {
            EmptyExtension.setGlobalBinder(emptyBinder);
            return this;
        }
    }
}

