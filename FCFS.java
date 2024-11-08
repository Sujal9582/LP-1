import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Scanner object for taking user input
        int n, temp;  // Variables for storing the number of processes and a temporary value for swapping
        float avgtat = 0, avgwt = 0;  // Variables for calculating average turnaround time and waiting time
        
        System.out.println("*** First Come First Serve Scheduling ***");
        System.out.print("Enter Number of Process: ");
        n = sc.nextInt();  // Taking input for the number of processes
        
        int process[] = new int[n];  // Array to store process IDs
        int arrivaltime[] = new int[n];  // Array to store arrival times of each process
        int burstTime[] = new int[n];  // Array to store burst times of each process
        int completionTime[] = new int[n];  // Array to store completion times of each process
        int TAT[] = new int[n];  // Array to store turnaround times of each process
        int waitingTime[] = new int[n];  // Array to store waiting times of each process

        // Loop to input arrival and burst time for each process
        for (int i = 0; i < n; i++) {
            process[i] = (i + 1);  // Assigning process IDs starting from 1
            System.out.print("\nEnter Arrival Time for processor " + (i + 1) + ":");
            arrivaltime[i] = sc.nextInt();  // Taking input for arrival time of process
            System.out.print("Enter Burst Time for processor " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();  // Taking input for burst time of process
        }

        // Sorting processes based on arrival time using a simple bubble sort technique
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                // Check if the arrival time of process `i` is greater than process `j`
                if (arrivaltime[i] > arrivaltime[j]) {
                    // Swapping process IDs to maintain the order after sorting
                    temp = process[j];
                    process[j] = process[i];
                    process[i] = temp;
                    
                    // Swapping arrival times to sort
                    temp = arrivaltime[j];
                    arrivaltime[j] = arrivaltime[i];
                    arrivaltime[i] = temp;
                    
                    // Swapping burst times to keep alignment with process order
                    temp = burstTime[j];
                    burstTime[j] = burstTime[i];
                    burstTime[i] = temp;
                }
            }
        }

        // Calculating completion time for each process
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                // For the first process, completion time is its arrival time + burst time
                completionTime[i] = arrivaltime[i] + burstTime[i];
            } else {
                // For other processes, if arrival time is greater than previous process's completion time
                if (arrivaltime[i] > completionTime[i - 1]) {
                    completionTime[i] = arrivaltime[i] + burstTime[i];  // Start when process arrives
                } else {
                    completionTime[i] = completionTime[i - 1] + burstTime[i];  // Start after previous completes
                }
            }
        }

        // Display the table header for process scheduling results
        System.out.println("\n*** First Come First Serve Scheduling ***");
        System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\t\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        
        // Loop to calculate turnaround time and waiting time for each process and display results
        for (int i = 0; i < n; i++) {
            TAT[i] = completionTime[i] - arrivaltime[i];  // Calculate Turnaround Time (TAT) = Completion - Arrival
            waitingTime[i] = TAT[i] - burstTime[i];  // Calculate Waiting Time = TAT - Burst Time
            avgtat += TAT[i];  // Accumulate TAT to calculate average TAT later
            avgwt += waitingTime[i];  // Accumulate Waiting Time to calculate average Waiting Time later
            
            // Display process details in formatted output
            System.out.println("P" + process[i] + "\t\t" + arrivaltime[i] + "ms\t\t" + burstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");
        }
        
        // Calculate and display the average turnaround time and average waiting time
        System.out.println("\nAverage turn around time of processor: " + (avgtat / n)
                + "ms\nAverage waiting time of processor: " + (avgwt / n) + "ms");

        sc.close();  // Close the scanner object to prevent memory leak
    }
}

/*
 * This Java program implements the **First-Come, First-Serve (FCFS) scheduling** algorithm, which is one of the simplest CPU scheduling algorithms. In FCFS scheduling, the process that arrives first is the one that gets executed first. This program calculates the **completion time**, **turnaround time**, and **waiting time** for each process based on their arrival and burst times.
 * 
 * Here’s a breakdown of how the program works:
 * 
 * 1. **Getting Inputs**:
 *    - The program first takes the number of processes as input.
 *    - For each process, it collects the **arrival time** (when the process arrives) and **burst time** (the time needed for the process to complete).
 * 
 * 2. **Sorting Processes by Arrival Time**:
 *    - The program sorts the processes based on their arrival times. This ensures that processes are executed in the order they arrive (the FCFS rule).
 *    - If a process has an earlier arrival time, it will be scheduled first.
 * 
 * 3. **Calculating Completion Times**:
 *    - For each process:
 *      - If it’s the first process, its **completion time** is simply its arrival time plus its burst time.
 *      - For subsequent processes, the program checks if the current process arrived after the previous process finished. If it did, it waits until its arrival time to start, else it starts right after the previous process.
 *      - The **completion time** is calculated by adding the burst time to the time when the process starts executing.
 * 
 * 4. **Calculating Turnaround and Waiting Times**:
 *    - **Turnaround Time (TAT)** for each process is calculated as:  
 *      \( \text{TAT} = \text{Completion Time} - \text{Arrival Time} \)
 *    - **Waiting Time** for each process is calculated as:
 *      \( \text{Waiting Time} = \text{Turnaround Time} - \text{Burst Time} \)
 *    - The program also calculates the **average turnaround time** and **average waiting time** for all processes by summing up the respective times and dividing by the number of processes.
 * 
 * 5. **Output**:
 *    - The program prints a table displaying each process along with its arrival time, burst time, completion time, turnaround time, and waiting time.
 *    - It also outputs the average turnaround time and waiting time for all processes.
 */
