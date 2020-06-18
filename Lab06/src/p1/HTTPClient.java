package p1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTPClient {
	public String userAgent="";
	public String resourceFolder;
	public String dir="";
    public HTTPClient(String userAgent, String resSaveFolder)
    {
        this.userAgent = userAgent;
        resourceFolder = resSaveFolder;
    }
	public boolean getResource(String ResName, String DomName,String host,int port) throws UnknownHostException, IOException
	{
		StringBuilder request=new StringBuilder();
		request.append("GET " + ResName + " HTTP/1.1\r\n");
		
		request.append("Host:" + DomName  +"\r\n");
		
		request.append("User-Agent: " + userAgent  +"\r\n");
		
		request.append("Connection: close\r\n");
		
		request.append("\r\n");
		
		String HTTPRequest=request.toString();
		
		System.out.println("HTTP Request: \n" + HTTPRequest);
		
		Socket tcpSocket=new Socket(host,port);
		
		DataOutputStream ToServer= new DataOutputStream(tcpSocket.getOutputStream());
		
		BufferedReader FromServer=new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
		
		ToServer.writeBytes(HTTPRequest);
		
		System.out.println("Request sent to seerver.");
		
		System.out.println("Answer ffrom server: \n");
		
		String response=FromServer.readLine();
		boolean responseFlag=false;
		
		if(response.contains("200 OK"))
			responseFlag=true;
		System.out.println(response);
		
		//write other things in antet
		while((response=FromServer.readLine()) != null)
		{
			if(response.equals("")) //end of antet - answer follows
				break;
			System.out.println(response);
		}
		
		if(responseFlag==true)
		{
			// construim continutul paginii trimise de server
            StringBuilder pageBuilder = new StringBuilder();
            while ((response = FromServer.readLine()) != null)
            {
                pageBuilder.append(response + System.lineSeparator());
            }

            // construim calea de salvare a resursei
            String htmlFilePath = resourceFolder + "/" + DomName + ResName;
            if (!(htmlFilePath.endsWith(".html") || htmlFilePath.endsWith("htm")) && !ResName.equals("/robots.txt")	)
            {
                if (!htmlFilePath.endsWith("/"))
                {
                    htmlFilePath += "/";
                }
                htmlFilePath += "index.html";
            }

            File file = new File(htmlFilePath);
            File parentDirectory = file.getParentFile();
            if (!parentDirectory.exists())
            {
                parentDirectory.mkdirs();
            }

            // salvam resursa in calea construita
            BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFilePath));
            writer.write(pageBuilder.toString());
            writer.close();
            
        }
		System.out.println("DONE!");
        tcpSocket.close();

        return true;
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		HTTPClient c=new HTTPClient("CLIENT RIW", "./http");

			c.getResource("/crawl", "riweb.tibeica.com", "67.207.88.228", 80);

	}

}
