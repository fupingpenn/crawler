package crawler.framework;

import java.util.LinkedList;

public class SynchronizedQueue<T> {
	
	private final Object mLock;
	private final LinkedList<T> mList;
	
	public SynchronizedQueue() {
		mLock = new Object();
		mList = new LinkedList<T>();
	}
	
	public T get() {
		synchronized(mLock) {
			if (!mList.isEmpty()) {
				return mList.remove();
			}
			return null;
		}
	}
	
	public void add(T object) {
		synchronized(mLock) {
			mList.add(object);
		}
	}
}
