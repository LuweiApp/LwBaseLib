package com.luwei.base;



public class LwBasePresent<V extends IView> implements IPresent<V> {

    private V v;

    @Override
    public void attachV(V view) {
        v = view;
    }

    @Override
    public void detachV() {
        v = null;
    }

    protected V getV() {
        return v;
    }

}
