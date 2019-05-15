package pak;

import java.util.ArrayList;
import java.util.Random;

public class Program
{

	final static int NUM_PROCS = 6; // How many concurrent processes
	final static int TOTAL_RESOURCES = 30; // Total resources in the system
	final static int MAX_PROC_RESOURCES = 13; // Highest amount of resources any process could need
	final static int ITERATIONS = 30; // How long to run the program
    private static int totalHeldResources = 0;
	static Random rand = new Random();
	
	public static void main(String[] args)
	{
		
		// The list of processes:
		ArrayList<Proc> processes = new ArrayList<Proc>();
		
		for (int i = 0; i < NUM_PROCS; i++)
			processes.add(new Proc(MAX_PROC_RESOURCES - rand.nextInt(3))); // Initialize to a new Proc, with some small range for its max
		
		// Run the simulation:
		for (int i = 0; i < ITERATIONS; i++)
		{
			// loop through the processes and for each one get its request
			for (int j = 0; j < processes.size(); j++)
			{
				// Get the request
				int currRequest = processes.get(j).resourceRequest(TOTAL_RESOURCES - totalHeldResources);
                
				// just ignore processes that don't ask for resources
				if (currRequest == 0)
					continue;
				
					
				
				if(currRequest < 0)
				{
					totalHeldResources += currRequest;
				    System.out.println("Process " + j + " completed. " + j + " resources released.");
				}
				else 
				{ 
					System.out.println("Process " + j + " requesting " + currRequest + " resources...");
					int needed = processes.get(j).getMaxResources() - processes.get(j).getHeldResources();
					if(currRequest == needed)
					{
						processes.get(j).addResources(currRequest);
						totalHeldResources += currRequest;
						System.out.println("Request granted.");
					}
					else
						System.out.println("Request denied.");
				}
				
			
				// At the end of each iteration, give a summary of the current status:
				System.out.println("\n***** STATUS *****");
				System.out.println("Total Available: " + (TOTAL_RESOURCES - totalHeldResources));
				for (int k = 0; k < processes.size(); k++)
					System.out.println("Process " + k + " holds: " + processes.get(k).getHeldResources() + ", max: " +
							processes.get(k).getMaxResources() + ", claim: " + 
							(processes.get(k).getMaxResources() - processes.get(k).getHeldResources()));
				System.out.println("***** STATUS *****\n");
				
			}
		}

	}

}
