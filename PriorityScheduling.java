import java.util.Scanner;

public class PriorityScheduling {

    public static void main(String[] args) {

        System.out.println("*** Priority Scheduling (Non Preemptive) ***");

        System.out.print("Enter Number of Process: ");
        Scanner sc = new Scanner(System.in); // Initialize scanner for user input
        int n = sc.nextInt(); // Number of processes
        int process[] = new int[n]; // Array to hold process IDs
        int arrivaltime[] = new int[n]; // Array for arrival times
        int burstTime[] = new int[n]; // Array for burst times
        int completionTime[] = new int[n]; // Array to store completion times
        int priority[] = new int[n]; // Array for priorities
        int TAT[] = new int[n]; // Array for Turn Around Time (TAT)
        int waitingTime[] = new int[n]; // Array for waiting times
        int arrivaltimecopy[] = new int[n]; // Copy of arrival times for later use
        int burstTimecopy[] = new int[n]; // Copy of burst times for later calculations
        int max = -1, min = 9999; // Variables for max and min arrival times
        int totalTime = 0, tLap, temp; // Variables for total time, current elapsed time, and temporary storage
        int minIndex = 0, currentIndex = 0; // Indices for processes with highest priority and current process
        double avgWT = 0, avgTAT = 0; // Variables for average waiting and turnaround times

        // Loop to gather process details from the user
        for (int i = 0; i < n; i++) {
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
                } else if (arrivaltime[i] == arrivaltime[j] && priority[j] > priority[i]) {
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

        System.arraycopy(arrivaltime, 0, arrivaltimecopy, 0, n); // Copy arrival times
        System.arraycopy(burstTime, 0, burstTimecopy, 0, n); // Copy burst times for later use

        // Calculate total burst time and find initial min arrival time
        for (int i = 0; i < n; i++) {
            totalTime += burstTime[i]; // Accumulate total burst time
            if (arrivaltime[i] < min) {
                max = arrivaltime[i]; // Track minimum arrival time
            }
        }

        // Find the first arriving process to start scheduling
        for (int i = 0; i < n; i++) {
            if (arrivaltime[i] < min) {
                min = arrivaltime[i];
                minIndex = i; // Track process with minimum arrival time
                currentIndex = i; // Set as the current process
            }
        }

        totalTime = min + totalTime; // Update total time including initial arrival
        tLap = min; // Initialize elapsed time to the minimum arrival time
        int tot = 0; // Variable to accumulate priorities (not used further)

        // Non-preemptive scheduling based on priority
        while (tLap < totalTime) {
            for (int i = 0; i < n; i++) {
                if (arrivaltimecopy[i] <= tLap) { // Check if process has arrived
                    if (priority[i] < priority[minIndex]) { // Find process with highest priority
                        minIndex = i;
                        currentIndex = i;
                    }
                }
            }

            tLap += burstTimecopy[currentIndex]; // Execute process and increment elapsed time
            completionTime[currentIndex] = tLap; // Record completion time for process
            priority[currentIndex] = 9999; // Mark process as completed by setting high priority
            for (int i = 0; i < n; i++) {
                tot += priority[i]; // Accumulate priorities for checking (not used further)
            }
        }

        // Calculate Turnaround Time and Waiting Time for each process
        for (int i = 0; i < n; i++) {
            TAT[i] = completionTime[i] - arrivaltime[i]; // Calculate Turnaround Time
            waitingTime[i] = TAT[i] - burstTime[i]; // Calculate Waiting Time
            avgTAT += TAT[i]; // Accumulate total Turnaround Time
            avgWT += waitingTime[i]; // Accumulate total Waiting Time
        }

        // Display process information and calculated times
        System.out.println("\n*** Priority Scheduling (Non Preemptive) ***");
        System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\tTurnaround time\tWaiting time");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t\t" + arrivaltime[i] + "ms\t\t" + burstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t" + TAT[i] + "ms\t\t" + waitingTime[i] + "ms");
        }

        avgWT /= n; // Calculate average waiting time
        avgTAT /= n; // Calculate average turnaround time
        System.out.println("\nAverage Waiting Time: " + avgWT);
        System.out.println("Average Turn Around Time: " + avgTAT);

        sc.close(); // Close the scanner
    }
}
