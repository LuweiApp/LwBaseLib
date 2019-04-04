package com.luwei.rxbus;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by Mr_Zeng at 2018/11/28
 */
public class RxBus {

    public static final String TAG = "RxBus";

    /**
     * 被观察者（发射器）
     */
    private FlowableProcessor<Object> mProcessor;

    /**
     * 管理已注册的 {@link CompositeDisposable}
     */
    private HashMap<Object, CompositeDisposable> mDisposableMap;


    private static RxBus instance;

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    private RxBus() {
        mProcessor = PublishProcessor.create().toSerialized();
        mDisposableMap = new HashMap<>();
    }

    /**
     * 注册
     *
     * @param object 需要注册的对象，如Activity
     * @return 链式调用的链接类
     */
    @CheckReturnValue
    public TypeLink register(Object object) {
        return new TypeLink(object);
    }


    /**
     * 注销，当被注册者被销毁或者不需要使用RxBus时，应该注销，防止内存泄露
     *
     * @param object 已经注册的对象
     */
    public void unregister(Object object) {
        CompositeDisposable disposable = mDisposableMap.get(object);
        if (disposable == null) {
            return;
        }
        disposable.dispose();
        mDisposableMap.remove(object);
    }


    /**
     * 发送事件
     *
     * @param event 事件
     */
    public void post(IEvent event) {
        mProcessor.onNext(event);
    }

    /**
     * 发送粘性事件
     */
    public void postStick(IEvent event) {
        mProcessor.onNext(event);
        CacheUtils.getInstance().putStickEvent(event);
    }

    public void removeStick(IEvent event) {
        CacheUtils.getInstance().removeStickEvent(event);
    }

    /**
     * {@link #with(Object, Disposable)}
     */
    void with(Object observer) {
        if (mDisposableMap.containsKey(observer)) {
            return;
        }
        mDisposableMap.put(observer, new CompositeDisposable());
    }


    /**
     * 关联（注册）观察者寄体
     *
     * @param observer   观察者寄体，如Activity
     * @param disposable 观察者{@link Flowable}的{@link Disposable}
     */
    void with(Object observer, Disposable disposable) {
        if (mDisposableMap.containsKey(observer)) {
            mDisposableMap.get(observer).add(disposable);
        } else {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            mDisposableMap.put(observer, compositeDisposable);
        }
    }

    /**
     * 链式调用链接类
     */
    public class TypeLink {

        //观察者寄体（如Activity）
        Object mObserver;

        public TypeLink(Object o) {
            mObserver = o;
            RxBus.getInstance().with(o);
        }

        /**
         * 注册的类型，绑定改类型就只能收到该类型的事件
         *
         * @param type 类型
         */
        @CheckReturnValue
        public <T extends IEvent> FunctionLink<T> ofType(Class<T> type) {
            Flowable<T> flowable = mProcessor.onBackpressureBuffer()
                    .ofType(type);
            return new FunctionLink<>(mObserver, flowable);
        }

        /**
         * 注册的类型，绑定改类型就只能收到该类型的事件
         *
         * @param type 类型
         */
        @CheckReturnValue
        public <T extends IEvent> FunctionLink<T> ofStickType(final Class<T> type) {
            List<IEvent> eventSources = CacheUtils.getInstance().getStickEvents(type);
            final List<IEvent> eventList ;
            if (eventSources==null){
                eventList = null;
            }else {
                eventList = new ArrayList<>(eventSources) ;
            }
             final Flowable<T> flowable = Flowable.create(new FlowableOnSubscribe<T>() {
                @Override
                public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                    if (eventList==null){
                        return;
                    }
                    for (IEvent iEvent : eventList) {
                        emitter.onNext(type.cast(iEvent));
                    }
                    emitter.onComplete();
                }
            }, BackpressureStrategy.LATEST);
            return new FunctionLink<>(mObserver, mProcessor.ofType(type).mergeWith(flowable));
        }

    }

    /**
     * 链式调用链接类
     */
    public class FunctionLink<T> {

        private Flowable<T> mFlowable;
        Object mObserver;

        public FunctionLink(Object observer, Flowable<T> flowable) {
            mFlowable = flowable;
            mObserver = observer;
        }

        /**
         * {@link Flowable#compose(FlowableTransformer)}
         */
        @CheckReturnValue
        @SuppressLint("CheckResult")
        public <V> FunctionLink<V> compose(FlowableTransformer<T, V> transformer) {
            return new FunctionLink<>(mObserver, mFlowable.compose(transformer));
        }

        /**
         * {@link Flowable#map(Function)}
         */
        @CheckReturnValue
        public <V> FunctionLink<V> map(Function<T, V> function) {
            return new FunctionLink<>(mObserver, mFlowable.map(function));
        }

        /**
         * {@link Flowable#observeOn(Scheduler)}
         */
        @CheckReturnValue
        @SuppressLint("CheckResult")
        public FunctionLink<T> observeOn(Scheduler scheduler) {
            mFlowable = mFlowable.observeOn(scheduler);
            return this;
        }

        /**
         * {@link Flowable#subscribeOn(Scheduler)}
         */
        @CheckReturnValue
        @SuppressLint("CheckResult")
        public FunctionLink<T> subscribeOn(Scheduler scheduler) {
            mFlowable = mFlowable.subscribeOn(scheduler);
            return this;
        }

        /**
         * {@link Flowable#subscribe(Consumer)}
         */
        public void subscribe(Consumer<T> onNext) {
            subscribe(onNext, Functions.ON_ERROR_MISSING);
        }

        /**
         * {@link Flowable#subscribe(Consumer, Consumer)}
         */
        public void subscribe(Consumer<T> onNext, Consumer<? super Throwable> onError) {
            subscribe(onNext, onError, Functions.EMPTY_ACTION);
        }

        /**
         * {@link Flowable#subscribe(Consumer, Consumer, Action)}
         */
        public void subscribe(Consumer<T> onNext, Consumer<? super Throwable> onError,
                              Action onComplete) {
            BusLambdaSubscriber<T> subscriber = new BusLambdaSubscriber<>(
                    onNext, onError, onComplete, FlowableInternalHelper.RequestMax.INSTANCE);
            mFlowable.subscribe(subscriber);
            RxBus.getInstance().with(mObserver, subscriber);
        }

//
//        /**
//         * {@link Flowable#subscribe(Consumer)}
//         */
//        public void subscribeStick(Consumer<T> onNext) {
//            subscribe(onNext, Functions.ON_ERROR_MISSING);
//        }
//
//        /**
//         * {@link Flowable#subscribe(Consumer, Consumer)}
//         */
//        public void subscribeStick(Consumer<T> onNext, Consumer<? super Throwable> onError) {
//            subscribe(onNext, onError, Functions.EMPTY_ACTION);
//        }
//
//        /**
//         * {@link Flowable#subscribe(Consumer, Consumer, Action)}
//         */
//        public void subscribeStick(Consumer<T> onNext, Consumer<? super Throwable> onError,
//                              Action onComplete) {
//            BusLambdaSubscriber<T> subscriber = new BusLambdaSubscriber<>(
//                    onNext, onError, onComplete, FlowableInternalHelper.RequestMax.INSTANCE);
//            mFlowable.subscribe(subscriber);
//            RxBus.getInstance().with(mObserver, subscriber);
//        }
    }
}
