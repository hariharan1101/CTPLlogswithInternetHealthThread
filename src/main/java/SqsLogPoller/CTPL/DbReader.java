package SqsLogPoller.CTPL;

import com.sun.tools.javac.Main;

import java.io.IOException;

public class DbReader extends Thread {
	SingletonPoller sp = null;

	public void run() {
		while (true)                             //infinite loop that has a 5 second sleep window (adjustable)
		{
				sp = MainRunner.sp;      //getting singleton instance
				if (sp.ReportDetails.get(sp.iterate) != null)         // if data is available then display data
				{
					System.out.println(sp.ReportDetails.get(sp.iterate));
					sp.iterate++;
				}
				else                                            // else the thread waits for 5 seconds (adjustable) for the DB to be filled with data
				{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					MainRunner.sp.iterate++;
				}
			}

		}
	}

