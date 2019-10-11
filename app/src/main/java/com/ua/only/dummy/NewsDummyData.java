package com.ua.only.dummy;

import com.ua.only.models.NewsItem;
import java.util.Arrays;
import java.util.List;

public class NewsDummyData {
	public static List<NewsItem> getNewsItems() {
		return toList(
			new NewsItem().withIdentifier(1L).withTitle("News title text")
						.withDescription("News description text"),
			new NewsItem().withIdentifier(2L).withTitle("Title news text")
						.withDescription("Description news text")
		);
	}

	private static List<NewsItem> toList(NewsItem... newsItems) {
        return Arrays.asList(newsItems);
    }
}
