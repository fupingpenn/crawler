package crawler.theKnot;

import crawler.framework.Crawler;
import crawler.framework.CrawlerManager;
import crawler.framework.SynchronizedQueue;

public class crawlerMain {
	public static void main(String args[]) {
		SynchronizedQueue<Coordinate> queue = new SynchronizedQueue<Coordinate>();
		TheKnotFiller filler = new TheKnotFiller();
		int threadNumber = 5;
		Crawler crawlers[] = new Crawler[threadNumber];
		for (int i = 0; i < threadNumber; ++i) {
			crawlers[i] = new TheKnotCrawler(queue);
		}
		CrawlerManager manager = new CrawlerManager(crawlers, queue, filler);
		manager.run();
	}
}
