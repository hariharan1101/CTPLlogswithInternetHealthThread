package SqsLogPoller.CTPL;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class InternetConnectivityChecker extends Thread{
	
	public static boolean isOnline() {
	    try {
	        final URL url = new URL("https://www.google.com");				
	        final URLConnection conn = url.openConnection();				//establishes connection with the specified URL 
	        conn.connect();
	        conn.getInputStream().close();
	        return true;													// if connection can be established = internet is present
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;													// if connection cant be made = no internet
	    }	// there is a possibility that the google servers are down (highly unlikely) in that case we can set up the function to send requests to multiple URLs to be sure.
	}
	
	public void run()
	{
		while(true)
		{
			if(isOnline())
			{
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			else
			{
				MainRunner.sp.executor.stop();
				MainRunner.dr.stop();
				System.out.println("Executor stopped, check internet connection.");	
				MainRunner.icc.stop();
			}
		}
	}
}
