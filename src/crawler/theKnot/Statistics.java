package crawler.theKnot;

public class Statistics {
	
	private static Statistics sInstance;
	private static Object sLock = new Object();
	private int mTotal = 0;
	private int mCurrentDone = 0;
	
	public static Statistics getInstance() {
		synchronized(sLock) {
			if (sInstance == null) {
				sInstance = new Statistics();
			}
			return sInstance;
		}
	}
	
	public void setNumber(int total) {
		synchronized(sLock) {
			mTotal = total;
			System.out.println("total set to be " + mTotal);
		}	
	}
	
	public void done() {
		synchronized(sLock) {
			++mCurrentDone;
			int percentage = (int)(((double)mCurrentDone) / mTotal) * 100;
			System.out.println("progress: " + percentage + "%");
		}
	}
}
