import java.util.Scanner;

class PriorityScheduling {

    public static void main(String[] args) {

        System.out.println("*** Priority Scheduling (Preemptive) ***");
        System.out.print("Enter Number of Process: ");
        Scanner sc = new Scanner(System.in); // Initialize scanner for user input
        int n = sc.nextInt(); // Number of processes
        int process[] = new int[n]; // Array to hold process IDs
        int arrivaltime[] = new int[n]; // Array for arrival times
        int burstTime[] = new int[n]; // Array for burst times
        int completionTime[] = new int[n]; // Array to store completion times
        int priority[] = new int[n + 1]; // Array for priorities, with an extra index to handle comparisons
        int TAT[] = new int[n]; // Array for Turn Around Time (TAT)
        int waitingTime[] = new int[n]; // Array for waiting times
        int burstTimecopy[] = new int[n]; // Copy of burst times for later calculations
        int min = 0, count = 0; // Variables for minimum priority and process completion count
        int temp, time = 0, end; // Temporary variable, current time, and end time for a process
        double avgWT = 0, avgTAT = 0; // Variables for average waiting and turnaround times

        for (int i = 0; i < n; i++) { // Loop to gather process details from the user
            process[i] = (i + 1); // Assign process ID
            System.out.println("");
            System.out.print("Enter Arrival Time for processor " + (i + 1) + ": ");
            arrivaltime[i] = sc.nextInt(); // Get arrival time
            System.out.print("Enter Burst Time for processor " + (i + 1) + " : ");
            burstTime[i] = sc.nextInt(); // Get burst time
            System.out.print("Enter Priority for " + (i + 1) + " process: ");
            priority[i] = sc.nextInt(); // Get priority level
        }

        // Sort processes by arrival time and priority
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrivaltime[i] > arrivaltime[j]) { // Sort by arrival time if greater
                    // Swap process data if necessary
                    temp = process[i];
                    process[i] = process[j];
                    process[j] = temp;
                    temp = arrivaltime[j];
                    arrivaltime[j] = arrivaltime[i];
                    arrivaltime[i] = temp;
                    temp = priority[j];
                    priority[j] = priority[i];
                    priority[i] = temp;
                    temp = burstTime[j];
                    burstTime[j] = burstTime[i];
                    burstTime[i] = temp;
                }
                if (arrivaltime[i] == arrivaltime[j] && priority[j] > priority[i]) {
                    // Sort by priority if arrival time is the same
                    temp = process[i];
                    process[i] = process[j];
                    process[j] = temp;
                    temp = arrivaltime[j];
                    arrivaltime[j] = arrivaltime[i];
                    arrivaltime[i] = temp;
                    temp = priority[j];
                    priority[j] = priority[i];
                    priority[i] = temp;
                    temp = burstTime[j];
                    burstTime[j] = burstTime[i];
                    burstTime[i] = temp;
                }
            }
        }

        System.arraycopy(burstTime, 0, burstTimecopy, 0, n); // Make a copy of burst times for later use
        priority[n] = 999; // Set the last element in priority array as max to handle boundary conditions

        for (time = 0; count != n; time++) { // Main scheduling loop, continues until all processes are done
            min = n; // Reset min to track process with highest priority available
            for (int i = 0; i < n; i++) {
                if (arrivaltime[i] <= time && priority[i] < priority[min] && burstTime[i] > 0)
                    min = i; // Find process with highest priority that has arrived and is not finished
            }

            burstTime[min]--; // Execute selected process for 1 time unit
            if (burstTime[min] == 0) { // Check if process is finished
                count++; // Increment completed process count
                end = time + 1; // Calculate end time for this process
                completionTime[min] = end; // Store completion time
                waitingTime[min] = end - arrivaltime[min] - burstTimecopy[min]; // Calculate and store waiting time
                TAT[min] = end - arrivaltime[min]; // Calculate and store Turnaround Time
            }
        }

        for (int i = 0; i < n; i++) {
            avgTAT += TAT[i]; // Sum of all Turnaround Times
            avgWT += waitingTime[i]; // Sum of all Waiting Times
        }

        // Display process information and calculated times
        System.out.println("\n*** Priority Scheduling (Preemptive) ***");
        System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\tTurnaround time\tWaiting time");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t\t" + arrivaltime[i] + "ms\t\t" + burstTimecopy[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t" + TAT[i] + "ms\t\t" + waitingTime[i] + "ms");
        }

        avgWT /= n; // Calculate average waiting time
        avgTAT /= n; // Calculate average turnaround time
        System.out.println("\nAverage Waiting Time: " + avgWT);
        System.out.println("Average Turn Around Time: " + avgTAT);

        sc.close(); // Close the scanner
    }
}
