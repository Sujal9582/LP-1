import java.util.*;  // Importing Java utilities for input and data structure usage

public class SJF {

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);  // Scanner for reading input from the user
    System.out.println("*** Shortest Job First Scheduling (Preemptive) ***");
    System.out.print("Enter no of process:");
    int n = sc.nextInt();  // Number of processes

    int process[] = new int[n];  // Array to store process IDs
    int arrivaltime[] = new int[n];  // Array for arrival times of each process
    int burstTime[] = new int[n];  // Array for the burst times (total CPU time required) of each process
    int completionTime[] = new int[n];  // Array for storing completion times of each process
    int TAT[] = new int[n];  // Array to store Turn Around Time of each process
    int waitingTime[] = new int[n];  // Array to store waiting times of each process
    int visit[] = new int[n];  // Array to track if a process has completed execution
    int remburstTime[] = new int[n];  // Array for remaining burst times for preemptive scheduling

    int temp, start = 0, total = 0;  // Temporary variables, start (current time), total (completed processes count)
    float avgwt = 0, avgTAT = 0;  // Variables for average waiting time and average turn-around time

    // Loop to input arrival and burst times for each process
    for (int i = 0; i < n; i++) {
      System.out.println(" ");
      process[i] = (i + 1);  // Assign process IDs
      System.out.print("Enter Arrival Time for processor " + (i + 1) + ":");
      arrivaltime[i] = sc.nextInt();  // Read arrival time
      System.out.print("Enter Burst Time for processor " + (i + 1) + ": ");
      burstTime[i] = sc.nextInt();  // Read burst time
      remburstTime[i] = burstTime[i];  // Initialize remaining burst time to initial burst time
      visit[i] = 0;  // Mark process as not yet completed
    }

    // Sort processes based on arrival times
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (arrivaltime[i] < arrivaltime[j]) {  // Check for earlier arrival
          temp = process[j];
          process[j] = process[i];
          process[i] = temp;

          temp = arrivaltime[j];
          arrivaltime[j] = arrivaltime[i];
          arrivaltime[i] = temp;

          temp = remburstTime[j];
          remburstTime[j] = remburstTime[i];
          remburstTime[i] = temp;

          temp = burstTime[j];
          burstTime[j] = burstTime[i];
          burstTime[i] = temp;
        }
      }
    }

    // Main loop for scheduling processes
    while (true) {
      int min = 99, c = n;  // `min` stores the minimum burst time found, `c` stores index of chosen process

      if (total == n) {  // Break if all processes are completed
        break;
      }

      // Find the process with the shortest burst time that has arrived and is not completed
      for (int i = 0; i < n; i++) {
        if ((arrivaltime[i] <= start) && (visit[i] == 0) && (burstTime[i] < min)) {
          min = burstTime[i];  // Update min burst time
          c = i;  // Index of process with shortest burst time
        }
      }

      if (c == n)  // If no process is ready, increment start time
        start++;

      else {
        burstTime[c]--;  // Reduce burst time of selected process
        start++;  // Increment time

        if (burstTime[c] == 0) {  // Check if process has finished
          completionTime[c] = start;  // Set completion time for the process
          visit[c] = 1;  // Mark process as completed
          total++;  // Increment completed process count
        }
      }
    }

    // Calculate Turn Around Time (TAT) and Waiting Time for each process
    for (int i = 0; i < n; i++) {
      TAT[i] = completionTime[i] - arrivaltime[i];  // TAT = Completion Time - Arrival Time
      waitingTime[i] = TAT[i] - remburstTime[i];  // Waiting Time = TAT - Burst Time
      avgwt += waitingTime[i];  // Add waiting time to total for average calculation
      avgTAT += TAT[i];  // Add TAT to total for average calculation
    }

    // Display the results in a formatted table
    System.out.println("*** Shortest Job First Scheduling (Preemptive) ***");
    System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\tTurn around time\tWaiting time");
    System.out.println("-------------------------------------------------------------------------------------------");
    for (int i = 0; i < n; i++) {
      System.out.println("P" + process[i] + "\t\t" + arrivaltime[i] + "ms\t\t" + remburstTime[i] + "ms\t\t"
          + completionTime[i] + "ms\t\t" + TAT[i] + "ms\t\t" + waitingTime[i] + "ms");
    }

    // Calculate and display average TAT and average waiting time
    avgTAT /= n;  // Average TAT
    avgwt /= n;  // Average Waiting Time

    System.out.println("\nAverage turn around time is " + avgTAT);
    System.out.println("Average waiting time is " + avgwt);
    sc.close();  // Close scanner to avoid resource leaks
  }
}
