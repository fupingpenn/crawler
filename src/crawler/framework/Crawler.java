package crawler.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Crawler implements Runnable {
	
	protected final SynchronizedQueue mQueue;
	
	public Crawler(SynchronizedQueue queue) {
		mQueue = queue;
	}
	
	public abstract String[] getUrl(Object object);
	
	public abstract void processResponse(Object object, String[] response);
	
	public abstract void onCrawlSucceed(Object object);

	@Override
	public void run() {
		while(true) {
			Object object = mQueue.get();
			if (object == null) {
				return;
			} else {
				processResponse(object, crawl(getUrl(object)));
				onCrawlSucceed(object);
			}
		}
	}
	
	protected String[] crawl(String urlStrings[]) {
		int number = urlStrings.length;
		String res[] = new String[number];
		for (int i = 0; i < number; ++i) {
			res[i] = crawl(urlStrings[i]);
		}
		return res;
	}
	
	protected String crawl(String urlString) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			int statusCode = connection.getResponseCode();
			if (statusCode < 200 || statusCode >= 300) {
				System.out.println("Fail to crawl: " + url + " status code: " + statusCode);
				return null;
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			StringBuffer body = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				body.append(line);
				body.append('\n');
			}
			return body.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
}
