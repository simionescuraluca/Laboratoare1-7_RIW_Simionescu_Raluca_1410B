package lab01p2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class p2 {

	public static void main(String[] args) throws IOException {
		BufferedReader r=new BufferedReader(new FileReader(new File("p2.html")));
		String line=null;
		HashMap<String,Integer> dictionary=new HashMap<String,Integer>();
		while((line=r.readLine())!=null)
		{
			String[] words=line.split("\\s+"); // s+ - regex, separa dupa toate spatiile.
			for(String word:words)
			{
				word=word.replace(".", " ");
				word=word.replace(",", " ");
				word=word.replace("<", " ");
				word=word.replace(">", " ");
				word=word.replace("/", " ");
				word=word.replace("!", " ");
				word=word.replace("?", " ");
				word=word.replace("=", " ");
				if(dictionary.containsKey(word))
				{
					Integer val=dictionary.get(word);
					dictionary.put(word, val+1);
				}
				else
					dictionary.put(word, 1);
			}
		}
		for(String key:dictionary.keySet())
			System.out.println(key + " : "+ dictionary.get(key));
	}

}
