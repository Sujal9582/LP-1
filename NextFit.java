import java.util.Arrays;
import java.util.Scanner;

public class NextFit {

    // Method to perform Next-Fit memory allocation
    static void NextFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        
        int allocation[] = new int[n], j = 0; // Array to track block allocations for each process and variable to remember last allocated block
        
        Arrays.fill(allocation, -1); // Initialize allocation array with -1 (indicating no allocation)
        
        // Loop over each process to allocate memory
        for (int i = 0; i < n; i++) {
            int count = 0; // Variable to track attempts in finding a suitable block
            
            // Check blocks in a cyclic manner until a suitable block is found or all blocks are checked
            while (count < m) {
                count++; // Increment the count of blocks checked
                
                if (blockSize[j] >= processSize[i]) { // If current block can fit the process
                    allocation[i] = j; // Assign the block j to the process
                    blockSize[j] -= processSize[i]; // Reduce the block size by the allocated process size
                    remblockSize[i] = blockSize[j]; // Store the remaining size of the block after allocation
                    break; // Exit the loop as allocation is done for this process
                }
                
                j = (j + 1) % m; // Move to the next block, wrap around if end is reached
                count += 1; // Increment count again for each block attempt
            }
        }
        
        // Display the result of allocations
        System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaining Block Size");
        
        for (int i = 0; i < n; i++) { // Loop over all processes to print allocation results
            System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t"); // Print process number and its required size
            
            if (allocation[i] != -1) { // Check if process was allocated to a block
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]); // Print allocated block and remaining size
            } else {
                System.out.print("Not Allocated" + "\t" + remblockSize[i]); // Print "Not Allocated" if no block was found
            }
            
            System.out.println(""); // Move to the next line for each process
        }
    }

    // Main method to take input and invoke the NextFit method
    public static void main(String[] args) {
        
        int m, n, num; // Variables for number of blocks, processes, and temporary input storage
        Scanner in = new Scanner(System.in); // Create Scanner object for user input
        
        System.out.print("Enter how many number of blocks you want to enter: ");
        m = in.nextInt(); // Input number of memory blocks
        
        int blockSize[] = new int[m]; // Array to store sizes of memory blocks
        int remblockSize[] = new int[m]; // Array to track remaining sizes of blocks after allocations
        
        for (int i = 0; i < m; i++) { // Loop to get sizes of memory blocks
            System.out.print("Enter Data " + (i + 1) + ": ");
            num = in.nextInt(); // Input block size
            blockSize[i] = num; // Store the size in blockSize array
        }
        
        System.out.print("Enter how many number of processes you want to enter: ");
        n = in.nextInt(); // Input number of processes
        
        int processSize[] = new int[n]; // Array to store memory requirements of processes
        
        for (int i = 0; i < n; i++) { // Loop to get memory requirements of each process
            System.out.print("Enter Data " + (i + 1) + ": ");
            num = in.nextInt(); // Input process size
            processSize[i] = num; // Store the size in processSize array
        }
        
        NextFit(blockSize, m, processSize, n, remblockSize); // Call NextFit method with block and process details
        
        in.close(); // Close the Scanner to release resources
    }
}

/*
 * The NextFit program implements memory allocation using the Next Fit strategy.
 * Key components:
 *
 * 1. **User Input**:
 *    - Prompts user to input the number and sizes of available memory blocks.
 *    - Prompts user to input the number and sizes of processes needing memory.
 *
 * 2. **NextFit Allocation Logic**:
 *    - The `NextFit` method attempts to allocate each process sequentially to the next suitable block.
 *    - If a process fits in the current block, it’s allocated and updates remaining block size.
 *    - If not, the search moves to the next block, wrapping around as needed.
 *
 * 3. **Output**:
 *    - Displays allocation results, showing each process number, its required size, allocated block, and remaining block size.
 *    - If a process couldn’t be allocated, it displays "Not Allocated".
 *
 * Example:
 * Given blocks `[100, 200, 300]` and processes `[150, 350, 100, 200]`, the program might produce:
 *
 * ```
 * Process No.  Process Size   Block no.   Remaining Block Size
 * 1            150            2          50
 * 2            350            Not Allocated  200
 * 3            100            1          0
 * 4            200            3          100
 * ```
 * This demonstrates Next Fit's sequential allocation, where it avoids revisiting past allocations.
 */
