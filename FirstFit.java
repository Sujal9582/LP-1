import java.util.Scanner;

public class FirstFit {
    // Method to perform First-Fit memory allocation
    static void firstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n];  // Array to keep track of which block each process is allocated to
        
        // Initialize all allocations to -1, indicating no allocation initially
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }
        
        // Iterate through each process to allocate memory
        for (int i = 0; i < n; i++) {
            // Try to find the first block that can fit the current process
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {  // Check if block j can fit process i
                    allocation[i] = j;  // Assign block j to process i
                    blockSize[j] -= processSize[i];  // Update the remaining size of block j
                    remblockSize[i] = blockSize[j];  // Store the remaining block size after allocation
                    break;  // Break out of the loop after allocating
                }
            }
        }
        
        // Display the result of allocation
        System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaining Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");  // Print process number and its size
            if (allocation[i] != -1) {  // Check if the process has been allocated a block
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);  // Print allocated block and remaining size
            } else {
                System.out.print("Not Allocated" + "\t" + remblockSize[i]);  // Print "Not Allocated" if no block was found
            }
            System.out.println();  // Move to the next line for each process
        }
    }

    // Main method to take input and invoke the firstFit method
    public static void main(String[] args) {
        int m, n, num;  // Declare variables for number of blocks, number of processes, and temporary input storage
        Scanner in = new Scanner(System.in);  // Create Scanner object for user input
        
        // Input the number of memory blocks and their sizes
        System.out.print("Enter how many number of blocks you want to enter: ");
        m = in.nextInt();  // Number of memory blocks
        int blockSize[] = new int[m];  // Array to store each block's size
        int remblockSize[] = new int[m];  // Array to keep track of remaining sizes of blocks after allocation
        
        for (int i = 0; i < m; i++) {
            System.out.print("Enter Data " + (i + 1) + ": ");
            num = in.nextInt();  // Input the size of each block
            blockSize[i] = num;  // Store the size in blockSize array
        }
        
        // Input the number of processes and their memory requirements
        System.out.print("Enter how many number of processes you want to enter: ");
        n = in.nextInt();  // Number of processes
        int processSize[] = new int[n];  // Array to store each process's memory requirement
        
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Data " + (i + 1) + ": ");
            num = in.nextInt();  // Input the memory required by each process
            processSize[i] = num;  // Store the size in processSize array
        }
        
        // Call firstFit method with block sizes, process sizes, and the arrays for remaining sizes
        firstFit(blockSize, m, processSize, n, remblockSize);
        
        in.close();  // Close the scanner to release resources
    }
}

/*
 * The program implements the First-Fit Memory Allocation algorithm.
 * Key components:
 *
 * 1. **User Input**:
 *    - Accepts the number and sizes of memory blocks available.
 *    - Accepts the number and sizes of memory each process requires.
 *
 * 2. **First-Fit Allocation Logic**:
 *    - The `firstFit` method attempts to allocate each process to the first memory block that can fit it.
 *    - If a block is found that fits a process, the remaining size of that block is updated.
 *    - If a block is too small, it moves on to the next block.
 *
 * 3. **Display of Results**:
 *    - The allocation table displays:
 *      - Process Number: Identifies each process.
 *      - Process Size: Shows memory required by each process.
 *      - Block Number: Shows the block assigned to each process.
 *      - Remaining Block Size: Displays any remaining memory in allocated blocks.
 *
 * 4. **Example Execution**:
 *    - If 3 blocks have sizes `[100, 500, 200]` and 3 processes require `[90, 420, 200]`, 
 *      the program simulates allocating processes to blocks.
 *
 *    - Output Example:
 *    ```
 *    Process No.  Process Size    Block No.    Remaining Block Size
 *    1            90              1            10
 *    2            420             2            80
 *    3            200             3            0
 *    ```
 *
 *    - This helps visualize memory allocation and remaining space after each process is assigned.
 */
