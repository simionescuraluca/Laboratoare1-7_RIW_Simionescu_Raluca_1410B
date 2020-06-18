package p1;


import java.util.List;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.text.Document;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	private List<String> links=new LinkedList<String>();
	private org.jsoup.nodes.Document htmlDoc;
	private static final String USER_AGENT="CLIENTRIW";
	public void crawl(String URL) {
		try
		{
			Connection conn=Jsoup.connect(URL).userAgent(USER_AGENT);
			org.jsoup.nodes.Document htmlDocument=conn.get();
			this.htmlDoc=htmlDocument;
			
			if(conn.response().statusCode() == 200) // 200 is the HTTP OK status code
                // indicating that everything is great.
			{
				System.out.println("\n**Visiting** Received web page at " + URL);
			}
			
			Elements linksOnPage=((Element) htmlDocument).select("a[href]");
			System.out.println("Found (" + linksOnPage.size() + ") links");
			for(Element link:linksOnPage)
				this.links.add(link.absUrl("href"));
		}
		catch(IOException ioe)
		{
			System.out.println("ERROR in HTTP request: " + ioe);
		}
	} 
	public boolean searchForWord(String searchWord) {
		System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = ((org.jsoup.nodes.Document) this.htmlDoc).body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
	} 
	public List<String> getLinks() {
		return this.links;
	} 

}
