package p1;

import java.util.Set;
import java.util.HashSet;

import java.util.List;
import java.util.LinkedList;

public class P1 {
	
	private static final int MAX_PAGES_TO_SEARCH = 100;
	private Set<String> pagesVisited = new HashSet<String>(); //set - fara duplicate!!!!!
	private List<String> pagesToVisit = new LinkedList<String>();
	
	private String nextUrl() 
	{
		String URL;  //elimina duplicatele folosind proprietatea de unicitate a HashSetului
		do
		{
			URL=this.pagesToVisit.remove(0);
		}
		while(this.pagesVisited.contains(URL));
		this.pagesVisited.add(URL);
		return URL;
	}
	public void search(String URL, String searchWord)
	{
		while(this.pagesVisited.size()<MAX_PAGES_TO_SEARCH)
		{
			String curentURL;
			Crawler c=new Crawler();
			if(this.pagesToVisit.isEmpty())
			{
				curentURL=URL;
				this.pagesVisited.add(URL);
			}
			else
				curentURL=this.nextUrl();
			c.crawl(curentURL);
			Boolean success=c.searchForWord(searchWord);
			if(success)
			{
				System.out.println("OK!!!!  -  Word " + searchWord +  " found at " + curentURL + "\n");
				break;
			}
			this.pagesToVisit.addAll(c.getLinks());
			
		}
	}


}
