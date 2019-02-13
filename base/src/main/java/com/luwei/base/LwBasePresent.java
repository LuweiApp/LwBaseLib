package com.luwei.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LwBasePresent<V extends IView> implements IPresent<V> {

    private V v;
    private CompositeDisposable mCompositeDisposable;

    public LwBasePresent(){
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * 统一管理RxJava生命周期，防止内存泄露
     */
    protected void put(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void attachV(V view) {
        v = view;
    }

    @Override
    public void detachV() {
        v = null;
        mCompositeDisposable.clear();
    }

    protected V getV() {
        return v;
    }

}
