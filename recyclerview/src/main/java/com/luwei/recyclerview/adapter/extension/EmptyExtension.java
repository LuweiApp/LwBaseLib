package com.luwei.recyclerview.adapter.extension;

import com.luwei.recyclerview.adapter.multitype.LwAdapter;

import java.util.List;

public class EmptyExtension implements Extension {

    private final LwAdapter mAdapter;
    private final EmptyBean EMPTY_BEAN = new EmptyBean();
    private static EmptyBinder mGlobalBinder;

    public EmptyExtension(LwAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public Object getItem(int position) {
        return EMPTY_BEAN;
    }

    @Override
    public boolean isInRange(int adapterSize, int adapterPos) {
        return mAdapter.getItems().size() == 0 && adapterPos == mAdapter.getHeaderSize();
    }

    @Override
    public int getItemSize() {
        return mAdapter.getItems().size() == 0 ? 1 : 0;
    }

    @Override
    public void add(Object o) {

    }

    @Override
    public void add(int index, Object o) {

    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void addAll(List<Object> objects) {

    }

    @Override
    public void clear() {

    }

    public static void setGlobalBinder(EmptyBinder binder) {
        mGlobalBinder = binder;
    }

    public static EmptyBinder getGlobalBinder() {
        return mGlobalBinder;
    }
}
