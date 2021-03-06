package SqsLogPoller.CTPL;

import java.io.*;

import com.amazonaws.SdkClientException;

public class MainRunner extends Thread{
	static MainRunner rn;
	static DbReader dr;       //made static so that it can be called directly from "DbReader" class 
	static SingletonPoller sp;  // singleton object variable
	static InternetConnectivityChecker icc;
	static PrintStream stream;
	public void run()
	{   		
			sp.executor.start(); // starting the executor that polls data from sqs queue	
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		File file = new File("E:\\sample.txt");
		stream = new PrintStream(file);
		System.setErr(stream);
		if(InternetConnectivityChecker.isOnline())
		{
		sp= SingletonPoller.getInstance();	
		rn = new MainRunner(); // start thread that initiates the executor
		rn.start();
		dr = new DbReader();
		dr.start();             // start thread that reads data from the MapDB Htree
		icc = new InternetConnectivityChecker();
		icc.start();
		}
		else {
			System.out.println("Internet Connectivity Not Found...");
		}
	}		
				
}

