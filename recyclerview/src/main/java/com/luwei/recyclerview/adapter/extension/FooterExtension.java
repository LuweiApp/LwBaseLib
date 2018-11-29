package com.luwei.recyclerview.adapter.extension;

import com.luwei.recyclerview.adapter.multitype.Items;

import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/14
 */
public class FooterExtension implements Extension {

    private final Items mItems;

    public FooterExtension(Items items) {
        mItems = items;
    }

    public FooterExtension(){
        mItems = new Items();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public boolean isInRange(int adapterSize, int adapterPos) {
        return adapterPos < adapterSize &&
                adapterPos >= adapterSize - getItemSize();
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
        mItems.remove(o);
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
