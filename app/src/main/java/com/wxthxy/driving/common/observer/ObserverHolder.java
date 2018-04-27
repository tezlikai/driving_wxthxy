package com.wxthxy.driving.common.observer;

/**
 * 订阅模式管理类
 */
public class ObserverHolder {

    /**
     * 接收到信息
     */
    public static final int RECEIVER_MESSAGE_TAG = 1;


    private static ObserverHolder instance;

    private EventObservable eventObservable;

    private ObserverHolder() {
        eventObservable = new EventObservable();
    }

    public static ObserverHolder getInstance() {
        if (instance == null) {
            synchronized (ObserverHolder.class) {
                if (instance == null) {
                    instance = new ObserverHolder();
                }
            }
        }
        return instance;
    }

    /**
     * 将消息接收者注册进来
     * (如果将Activity作为订阅者在此注册的时候，切记在onDestroy()里面移除注册，否则可能导致内存泄露)
     * @param observer
     */
    public void register(IObserver observer){
        eventObservable.addObserver(observer);
    }

    /**
     * 将消息接收者移除注册
     */
    public void unregister(IObserver observer){
        eventObservable.deleteObserver(observer);
    }

    /**
     * 给订阅者发生消息
     * @param data
     */
    public void notifyObservers(String data, int flag){
        eventObservable.notifyObservers(data,flag);
    }

    /**
     * 用于传递bean实体类
     * @param data
     * @param flag
     */
    public void notifyObservers(Object data, int flag){
        eventObservable.notifyObservers(data,flag);
    }

}
