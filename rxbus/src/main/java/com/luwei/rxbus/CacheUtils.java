package com.luwei.rxbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr_Zeng
 *
 * @date 2019/3/4
 */
public class CacheUtils {

    private final Map<Class, List<IEvent>> mMap =new ConcurrentHashMap<>();

    private CacheUtils() {

    }

    public static CacheUtils getInstance() {
        return Holder.mInstance;
    }

    public List<IEvent> getStickEvents(Class type) {
        return mMap.get(type);
    }

    public void putStickEvent(IEvent event) {
        synchronized (this) {
            List<IEvent> eventList = mMap.get(event.getClass());
            if (eventList == null) {
                eventList = new ArrayList<>();
                mMap.put(event.getClass(), eventList);
            }
            if (eventList.indexOf(event) == -1) {
                eventList.add(event);
            }
        }
    }

    public void removeStickEvent(IEvent event) {
        synchronized (this) {
            List<IEvent> list = mMap.get(event.getClass());
            if (list == null) {
                return;
            }
            list.remove(event);
            if (list.isEmpty()) {
                mMap.remove(event.getClass());
            }
        }
    }

    private static class Holder {
        private final static CacheUtils mInstance = new CacheUtils();
    }

}
