package crawler.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CrawlerManager {
	
	protected final Crawler[] mCrawlers;
	protected final SynchronizedQueue mQueue;
	private final int mThreadNumber;
	private final Filler mFiller;
	
	public CrawlerManager(
			Crawler[] crawlers, SynchronizedQueue queue, Filler filler) {
		mCrawlers = crawlers;
		mQueue = queue;
		mFiller = filler;
		mThreadNumber = crawlers.length;
	}
	
	public void run() {
		mFiller.fillQueue(mQueue);
		Thread threads[] = new Thread[mThreadNumber];
		for(int i = 0; i < mThreadNumber; ++i) {
			threads[i] = new Thread(mCrawlers[i]);
			threads[i].start();
		}
		
		for (int i = 0; i < mThreadNumber; ++i) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
