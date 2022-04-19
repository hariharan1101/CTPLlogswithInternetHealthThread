package SqsLogPoller.CTPL;

import java.util.List;
import com.amazonaws.services.cloudtrail.processinglibrary.exceptions.CallbackException;
import com.amazonaws.services.cloudtrail.processinglibrary.interfaces.EventsProcessor;
import com.amazonaws.services.cloudtrail.processinglibrary.model.CloudTrailEvent;

public class EventProcessor implements EventsProcessor{
	SingletonPoller sp;
	public void process(List<CloudTrailEvent> events) throws CallbackException {   // method is called by the "executor" implicitly 
		
			sp = MainRunner.sp;								//getting singleton instance to use the Htree map
			
        for (CloudTrailEvent event : events) {															
        	String eventtime = event.getEventData().getEventTime().toString();						// acquiring necessary details 
        	String ip = event.getEventData().getSourceIPAddress();
        	String name = event.getEventData().getUserIdentity().getUserName();
        	String region = event.getEventData().getAwsRegion();
        	
        	String finalstring =String.valueOf(sp.i)+"                      "+ eventtime+"                        "+ip+"                                       "+name+"                                                        "+region;
            
        	//combining the seperate details into a whole string
        	
        	sp.ReportDetails.put(sp.i, finalstring);   //storing the data into a Htree map with keys ranging from 0->....
            sp.i=sp.i+1;
        }
		
	}

}

