import java.util.*;

class Process {
    int processID; // Process identifier
    int arrival, burst, waiting, turnAround, remainingTime; // Arrival time, burst time, waiting time, TAT, and remaining burst time for each process
    int finish, completionTime; // Flags to indicate if a process is finished and the completion time
}

public class RRScheduling {

    public static void main(String[] args) {
        int n, sumBurst = 0, quantum, time; // Declare variables: number of processes, total burst time, time quantum, and current time
        double avgWAT = 0, avgTAT = 0; // Variables to store average waiting and turnaround times
        Scanner sc = new Scanner(System.in); // Initialize scanner for user input
        Queue<Integer> q = new LinkedList<>(); // Initialize a queue for Round Robin scheduling

        System.out.println("*** RR Scheduling (Preemptive) ***");
        System.out.print("Enter Number of Process: ");
        n = sc.nextInt(); // Get the number of processes from the user
        Process[] p = new Process[n]; // Create an array of `Process` objects

        // Input details for each process
        for (int i = 0; i < n; i++) {
            p[i] = new Process(); // Initialize each `Process` object
            p[i].processID = i + 1; // Assign a unique process ID
            System.out.print("Enter the arrival time for P" + (i + 1) + ": ");
            p[i].arrival = sc.nextInt(); // Get the arrival time
            System.out.print("Enter the burst time for P" + (i + 1) + ": ");
            p[i].burst = sc.nextInt(); // Get the burst time
            p[i].remainingTime = p[i].burst; // Initialize remaining time to burst time
            p[i].finish = 0; // Mark process as not finished initially
            sumBurst += p[i].burst; // Accumulate total burst time
            System.out.println();
        }

        System.out.print("\nEnter time quantum: ");
        quantum = sc.nextInt(); // Get the time quantum for RR scheduling

        // Sort processes by arrival time
        Process pTemp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p[i].arrival > p[j].arrival) { // Check if a process has an earlier arrival time
                    pTemp = p[i];
                    p[i] = p[j];
                    p[j] = pTemp; // Swap processes to sort by arrival time
                }
            }
        }

        q.add(0); // Enqueue the first process based on sorted arrival time

        // Main loop to execute processes in Round Robin fashion until all are complete
        for (time = p[0].arrival; time < sumBurst;) {
            Integer I = q.remove(); // Get the index of the next process to run
            int i = I.intValue(); // Convert Integer to int

            if (p[i].remainingTime <= quantum) { // Check if the process can finish within the time quantum
                time += p[i].remainingTime; // Advance the current time by the remaining burst time
                p[i].remainingTime = 0; // Mark remaining time as zero since it's finished
                p[i].finish = 1; // Mark process as finished
                p[i].completionTime = time; // Record completion time
                p[i].waiting = time - p[i].arrival - p[i].burst; // Calculate waiting time
                p[i].turnAround = time - p[i].arrival; // Calculate turnaround time

                // Check for newly arrived processes and enqueue them
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if ((p[j].arrival <= time) && (p[j].finish != 1) && (!q.contains(J)))
                        q.add(j); // Enqueue processes that have arrived and are not finished
                }
            } else {
                time += quantum; // Advance the current time by the time quantum
                p[i].remainingTime -= quantum; // Reduce the remaining time by quantum

                // Check for other processes that have arrived and enqueue them if not finished
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if (p[j].arrival <= time && p[j].finish != 1 && i != j && (!q.contains(J)))
                        q.add(j); // Enqueue any newly arrived and unfinished processes
                }
                q.add(i); // Re-enqueue the current process since it’s not finished
            }
        }

        // Display process details after scheduling
        System.out.println("\n*** RR Scheduling (Preemptive) ***");
        System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            // Print details for each process
            System.out.println("P" + p[i].processID + "\t\t" + p[i].arrival + "ms\t\t" + p[i].burst + "ms\t\t"
                    + p[i].completionTime + "ms\t\t" + p[i].turnAround + "ms\t\t" + p[i].waiting + "ms");
            avgWAT += p[i].waiting; // Accumulate waiting times for average calculation
            avgTAT += p[i].turnAround; // Accumulate turnaround times for average calculation
        }

        // Print average turnaround time and waiting time
        System.out.println("\nAverage turn around time of processor: " + (avgTAT / n)
                + "ms\nAverage waiting time of processor: " + (avgWAT / n) + "ms");
    }
}
