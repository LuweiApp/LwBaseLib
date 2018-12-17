package com.luwei.util.forresult;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;

import io.reactivex.Observable;

/**
 * @Author: chenjianrun
 * @Time: 2018/12/7
 * @Description:   避免调用 startActivity 时，需要 onActivityResult 处理的类
 */
public class SimpleForResult {
    private static final String TAG = "SimpleForResult";
    private SimpleOnResultFragment mSimpleOnResultFragment;

    public SimpleForResult(AppCompatActivity activity) {
        mSimpleOnResultFragment = getOnResultFragment(activity.getSupportFragmentManager());
    }

    public SimpleForResult(Fragment fragment){
        mSimpleOnResultFragment = getOnResultFragment(fragment.getChildFragmentManager());
    }

    private SimpleOnResultFragment getOnResultFragment(FragmentManager fragmentManager) {
        SimpleOnResultFragment simpleOnResultFragment = findSimpleOnResultFragment(fragmentManager);
        if (simpleOnResultFragment == null) {
            simpleOnResultFragment = new SimpleOnResultFragment();
            fragmentManager
                    .beginTransaction()
                    .add(simpleOnResultFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return simpleOnResultFragment;
    }

    private SimpleOnResultFragment findSimpleOnResultFragment(FragmentManager fragmentManager) {
        return (SimpleOnResultFragment) fragmentManager.findFragmentByTag(TAG);
    }

    public Observable<ActivityResultInfo> startForResult(Intent intent) {
        return mSimpleOnResultFragment.startForResult(intent);
    }

    public Observable<ActivityResultInfo> startForResult(Class<?> clazz) {
        Intent intent = new Intent(mSimpleOnResultFragment.getActivity(), clazz);
        return startForResult(intent);
    }

    public void startForResult(Intent intent, Callback callback) {
        mSimpleOnResultFragment.startForResult(intent, callback);
    }

    public void startForResult(Class<?> clazz, Callback callback) {
        Intent intent = new Intent(mSimpleOnResultFragment.getActivity(), clazz);
        startForResult(intent, callback);
    }

    public interface Callback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
