package com.luwei.util.forresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * @Author: chenjianrun
 * @Time: 2018/12/7
 * @Description:    真正调用 startActivity 和处理 onActivityResult 的类。
 */
public class SimpleOnResultFragment extends Fragment {
    private Map<Integer, PublishSubject<ActivityResultInfo>> mSubjects = new HashMap<>();
    private Map<Integer, SimpleForResult.Callback> mCallbacks = new HashMap<>();

    public SimpleOnResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent) {
        int requestCode = generateRequestCode();
        PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        startActivityForResult(intent, requestCode);
        mSubjects.put(requestCode, subject);
        return subject;
    }

    public void startForResult(Intent intent, SimpleForResult.Callback callback) {
        int requestCode = generateRequestCode();
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava方式的处理
        PublishSubject<ActivityResultInfo> subject = mSubjects.remove(requestCode);
        if (subject != null) {
            subject.onNext(new ActivityResultInfo(requestCode, resultCode, data));
            subject.onComplete();
        }

        //callback方式的处理
        SimpleForResult.Callback callback = mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }

    private int generateRequestCode(){
        Random random = new Random();
        for (;;){
            int code = random.nextInt(65536);
            if (!mSubjects.containsKey(code) && !mCallbacks.containsKey(code)){
                return code;
            }
        }
    }
}
