package com.luwei.rxbus;

import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by Mr_Zeng
 * 抛异常不会取消订阅
 * {@link io.reactivex.internal.subscribers.LambdaSubscriber}
 *
 * @date 2018/12/12
 */
public final class BusLambdaSubscriber<T> extends AtomicReference<Subscription>
        implements FlowableSubscriber<T>, Subscription, Disposable, LambdaConsumerIntrospection {

    private static final long serialVersionUID = -7251123623727029452L;
    final Consumer<? super T> onNext;
    final Consumer<? super Throwable> onError;
    final Action onComplete;
    final Consumer<? super Subscription> onSubscribe;

    public BusLambdaSubscriber(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
                            Action onComplete,
                            Consumer<? super Subscription> onSubscribe) {
        super();
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
        this.onSubscribe = onSubscribe;
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (SubscriptionHelper.setOnce(this, s)) {
            try {
                onSubscribe.accept(this);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                s.cancel();
                onError(ex);
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (!isDisposed()) {
            try {
                onNext.accept(t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
//                get().cancel();
                onError(e);
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        if (get() != SubscriptionHelper.CANCELLED) {
//            lazySet(SubscriptionHelper.CANCELLED);
            try {
                onError.accept(t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(new CompositeException(t, e));
            }
        } else {
            RxJavaPlugins.onError(t);
        }
    }

    @Override
    public void onComplete() {
        if (get() != SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED);
            try {
                onComplete.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
        }
    }

    @Override
    public void dispose() {
        cancel();
    }

    @Override
    public boolean isDisposed() {
        return get() == SubscriptionHelper.CANCELLED;
    }

    @Override
    public void request(long n) {
        get().request(n);
    }

    @Override
    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    @Override
    public boolean hasCustomOnError() {
        return onError != Functions.ON_ERROR_MISSING;
    }
}
