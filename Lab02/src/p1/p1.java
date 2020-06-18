package p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class p1 
{
	public static void main(String[] args) throws IOException 
	{
		FileWalker fw=new FileWalker();
		ArrayList<HashMap<String,Integer>> list=new ArrayList<HashMap<String,Integer>>();
		//Path dir=Paths.get("dir");
		String[] exceptii={"cuv1", "cuv2"};
		String[] stopw={"cuv1"};
		List<File> fileList=new ArrayList<>();
		fw.walk("dir",fileList);
		for(int i=0;i<fileList.size();i++)
		{
			File file=fileList.get(i);
			if(file.isDirectory())
				System.out.println("Directory: " + file.getName());
			else
			{
		        System.out.println("File: " + file.getName());
		        BufferedReader r=new BufferedReader(new FileReader(file));
		        String line=null;
		        HashMap<String,Integer> dict=new HashMap<String,Integer>();
		        while((line=r.readLine())!=null)
		        {
		        	String[] words=line.split("\\s+");
		        	for(String word:words)
		        	{
		        		if(!Arrays.stream(exceptii).anyMatch(word::equals))
		        		{
		        			if(!Arrays.stream(stopw).anyMatch(word::equals))
		        			{
								if(dict.containsKey(word))
								{
									Integer val=dict.get(word);
									dict.put(word, val+1);
								}
								else
									dict.put(word, 1);
		        			}
		        		}
		        		else
		        		{
							if(dict.containsKey(word))
							{
								Integer val=dict.get(word);
								dict.put(word, val+1);
							}
							else
								dict.put(word, 1);
		        		}
		        	}
		        }
		        list.add(dict);
		       // r.close();
		    }

		for(HashMap<String,Integer> h:list)
		{
			System.out.println("Words from : " + h.toString());
			for(String key:h.keySet())
				System.out.println(key + " : "+ h.get(key));
		}
		}
		}
	}
	
