package com.ua.only.dummy;

import com.ua.only.models.NewsItem;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NewsDummyData {
	public static List<NewsItem> getNewsItems() {
		return toList(
			new NewsItem().withIdentifier(1L).withTitle("Title news one text")
						.withDescription("Description news one text")
						.withImage("https://www.iguides.ru/upload/medialibrary/5dd/5dd5d12fd192bb2da2135aca0e04dc9a.jpg"),
			new NewsItem().withIdentifier(2L).withTitle("Title news two text")
						.withDescription("Description news two text")
						.withImage("https://www.iguides.ru/upload/medialibrary/5dd/5dd5d12fd192bb2da2135aca0e04dc9a.jpg"),
			new NewsItem().withIdentifier(3L).withTitle("Title news three text")
						.withDescription("Description news three text"),
			new NewsItem().withIdentifier(4L).withTitle("Title news four text")
						.withDescription("Description news four text")
						.withImage("https://www.iguides.ru/upload/medialibrary/5dd/5dd5d12fd192bb2da2135aca0e04dc9a.jpg"),
			new NewsItem().withIdentifier(5L).withTitle("Title news one text")
						.withDescription("Description news one text")
						.withImage("https://www.iguides.ru/upload/medialibrary/5dd/5dd5d12fd192bb2da2135aca0e04dc9a.jpg"),
			new NewsItem().withIdentifier(6L).withTitle("Title news two text")
						.withDescription("Description news two text")
						.withImage("https://www.iguides.ru/upload/medialibrary/5dd/5dd5d12fd192bb2da2135aca0e04dc9a.jpg"),
			new NewsItem().withIdentifier(7L).withTitle("Title news three text")
						.withDescription("Description news three text"),
			new NewsItem().withIdentifier(8L).withTitle("Title news four text")
						.withDescription("Description news four text")
						.withImage("https://www.iguides.ru/upload/medialibrary/5dd/5dd5d12fd192bb2da2135aca0e04dc9a.jpg")
		);
	}
	
	public static NewsItem getDummyItem() {
        int ran = new Random().nextInt(3);
        if (ran == 0) {
            return new NewsItem().withTitle("NEW News").withDescription("News added item").withImage("https://raw.githubusercontent.com/mikepenz/earthview-wallpapers/develop/thumb/yang_zhuo_yong_cuo,_tibet-china-63.jpg");
        } else if (ran == 1) {
            return new NewsItem().withTitle("NEW News").withDescription("News added item").withImage("https://raw.githubusercontent.com/mikepenz/earthview-wallpapers/develop/thumb/yellowstone-united_states-17.jpg");
        } else {
            return new NewsItem().withTitle("NEW News").withDescription("News added item").withImage("https://raw.githubusercontent.com/mikepenz/earthview-wallpapers/develop/thumb/victoria-australia-31.jpg");
        }
    }

	private static List<NewsItem> toList(NewsItem... newsItems) {
        return Arrays.asList(newsItems);
    }
}
