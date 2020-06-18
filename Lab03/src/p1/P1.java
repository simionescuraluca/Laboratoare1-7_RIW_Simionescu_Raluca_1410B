package p1;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;



public class P1 {
	public static HashMap<String, HashMap<String, Integer>> indirectIndex(ArrayList<HashMap<String,HashMap<String,Integer>>> list) throws IOException
	{
		
		HashMap<String,HashMap<String,Integer>> indiciIndirectiList=new HashMap<String,HashMap<String,Integer>> ();
		for(HashMap<String,HashMap<String,Integer>> h:list)
		{
			
			Iterator<?> it=h.entrySet().iterator();
			while(it.hasNext())
			{
				
				Map.Entry pair=(Map.Entry)it.next();
				String document=(String) pair.getKey();
				//System.out.println(pair.getKey() + " = " + pair.getValue());
				Iterator<?> it1=((HashMap<String, HashMap<String, Integer>>) pair.getValue()).entrySet().iterator();
				while(it1.hasNext())
				{
					Map.Entry pair1=(Map.Entry)it1.next();
					String cuvant=(String) pair1.getKey();
					Integer nrapar=(Integer) pair1.getValue();
					
					HashMap<String,Integer> dict=new HashMap<String,Integer>();
					dict.put(document, nrapar);
					
					if(!indiciIndirectiList.containsKey(cuvant))
						indiciIndirectiList.put(cuvant,dict);
					else 
					{
						HashMap<String,Integer> dict1=indiciIndirectiList.get(cuvant);
						dict1.put(document,nrapar);
						indiciIndirectiList.put(cuvant, dict1);
					}
				}
				it.remove();
			}
		}
		//System.out.println(indiciIndirectiList.toString());
        return indiciIndirectiList;
	}
	public static void main(String[] args) throws IOException {
		FileWalker fw=new FileWalker();
		ArrayList<HashMap<String,HashMap<String,Integer>>> list=new ArrayList<HashMap<String,HashMap<String,Integer>>>();
		//ArrayList <String> numeDocumente=new ArrayList<String>();
		String[] stopw={"are", "nu", "si"};
		List<File> fileList=new ArrayList<>();
		fw.walk("dir",fileList);
		for(int i=0;i<fileList.size();i++)
		{
			File file=fileList.get(i);
			
			System.out.println("File: " + file.getName());
			BufferedReader r=new BufferedReader(new FileReader(file));
	        String line=null;
	        HashMap<String,Integer> dict=new HashMap<String,Integer>();
	        while((line=r.readLine())!=null)
	        {
	        	String[] words=line.split("\\s+");
	        	for(String word:words)
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
	        	HashMap<String,HashMap<String,Integer>> document=new HashMap<String,HashMap<String,Integer>>();
	        	document.put(file.getName(), dict);
	        	
	        	String NewFileName="II"+" "+file.getName();
	        	FileWriter fwritter =new FileWriter(new File(NewFileName));
	        	fwritter.write(document.toString());
	        	fwritter.close();
	        	list.add(document);
	        }
	        
		}
		
		
		
		HashMap<String,HashMap<String,Integer>> indiciIndirectiList=indirectIndex(list);
		FileWriter fwritter =new FileWriter(new File("IndiciIndrecti.txt"));
		Iterator<?> it=indiciIndirectiList.entrySet().iterator();
		while(it.hasNext())
		{
	        Entry<?, ?> pair = (Entry<?, ?>)it.next();
	        fwritter.write(pair.getKey() + " = " + pair.getValue());
	        fwritter.write("\n");
	        it.remove();
		}
		fwritter.close();
		
	}

}
