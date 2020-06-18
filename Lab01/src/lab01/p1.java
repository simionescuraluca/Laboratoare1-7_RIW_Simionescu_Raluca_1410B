package lab01;
import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class p1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File input=new File("temp.html");
		Document doc=Jsoup.parse(input, "UTF-8");
		if(doc.title()!=null)
			System.out.printf("Titlul este: " + doc.title());
		String keywords=doc.select("meta[name=keywords]").first().attr("content");
		System.out.printf("\nContinutul atributului content cu atr keywords este: " + keywords);
		
		String content=doc.select("meta[name=description]").first().attr("content");
		System.out.printf("\nContinutul atributului content cu atr description este: " + content);
	
		String robots=doc.select("meta[name=robots]").first().attr("content");
		System.out.printf("\nContinutul atributului content cu atr description este: " + robots);
	
		Elements elements=doc.body().select("a");
		for(Element e:elements)
		{
			String href=e.attr("href");
			if(href.contains("#"))
			{
				String href2=href.split("#",2)[0];	
				System.out.printf("\na href : " +href2);
			}
			
		}
		String text=doc.body().wholeText();
		System.out.printf("\nContinutul  este: " + text);
	}

}
