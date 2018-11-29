package com.luwei.recyclerview.adapter.extension;

import com.luwei.recyclerview.adapter.multitype.Items;

import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/14
 */
public class HeaderExtension implements Extension {

    private Items mItems;

    public HeaderExtension(Items items) {
        this.mItems = items;
    }

    public HeaderExtension(){
        this.mItems = new Items();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public boolean isInRange(int adapterSize, int adapterPos) {
        return adapterPos < getItemSize();
    }

    @Override
    public int getItemSize() {
        return mItems.size();
    }

    @Override
    public void add(Object o) {
        mItems.add(o);
    }

    @Override
    public void add(int index, Object o) {
        mItems.add(index,o);
    }

    @Override
    public void remove(Object o) {
        mItems.add(o);
    }

    @Override
    public void remove(int index) {
        mItems.remove(index);
    }

    @Override
    public void addAll(List<Object> objects) {
        mItems.addAll(objects);
    }

    @Override
    public void clear() {
        mItems.clear();
    }
}
