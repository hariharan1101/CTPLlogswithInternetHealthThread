package SqsLogPoller.CTPL;

import java.io.IOException;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.cloudtrail.processinglibrary.AWSCloudTrailProcessingExecutor;
import com.amazonaws.services.cloudtrail.processinglibrary.configuration.ClientConfiguration;


public class SingletonPoller {
	private static SingletonPoller sp = null;
	HTreeMap ReportDetails;
	ClientConfiguration basicConfig;
	AWSCloudTrailProcessingExecutor executor;
	int i=1;											//variable used to iterate through the db and store data
	int iterate=1;                                      //variable used to iterate through the db and display data
	private SingletonPoller() throws IOException 
	{
		DB db = DBMaker.memoryDB().make();              // db created in heap memory
		ReportDetails = db.hashMap("myMap").createOrOpen();			//HTreemap in which data is stored in the form of key-pair value
		
		
		basicConfig = new ClientConfiguration(
			    "https://sqs.us-east-2.amazonaws.com/153935663359/sqss3queuehari",
			    new DefaultAWSCredentialsProviderChain());				//providing aws credentials the access key and secret key are fetched directly from the environment variable 
		
		
		basicConfig.setS3Region("us-east-2");							// region of the s3 bucket
		

		executor = new
			      AWSCloudTrailProcessingExecutor.Builder(new EventProcessor(),
			        basicConfig).build();    // executor which pulls data from the sqs queue it also calls the process function from the EventProcessor class implicitly
		

	}
	
	
	public static SingletonPoller getInstance() throws IOException    // static method to return singleton object
    {
        if (sp == null)
            sp = new SingletonPoller();
 
        return sp;
    }
}



