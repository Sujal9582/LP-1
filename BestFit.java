import java.util.Scanner;

public class BestFit {
    // Function to implement Best Fit Memory Allocation
    static void bestFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n];  // Array to store the block number allocated to each process
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;  // Initially, no process is allocated to any block (marked as -1)
        }
        
        // Iterate over all processes to allocate them to memory blocks
        for (int i = 0; i < n; i++) {
            int bestIdx = -1;  // To track the index of the best block for the current process
            // Iterate over all blocks to find the best fit for the current process
            for (int j = 0; j < m; j++) {
                // Check if the current block can fit the process
                if (blockSize[j] >= processSize[i]) {
                    // If this is the first block that fits, or it is better than the previous best fit
                    if (bestIdx == -1)
                        bestIdx = j;  // Assign this block as the best fit
                    else if (blockSize[bestIdx] > blockSize[j])
                        bestIdx = j;  // Assign a new best fit if the current block is smaller and fits
                }
            }
            // If a block has been found for the process, allocate it
            if (bestIdx != -1) {
                allocation[i] = bestIdx;  // Allocate the block to the process
                blockSize[bestIdx] -= processSize[i];  // Reduce the block size by the size of the allocated process
                remblockSize[i] = blockSize[bestIdx];  // Store the remaining size in the block
            }
        }
        
        // Print the allocation results
        System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaining Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1) {  // If the process was allocated to a block
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);  // Print block number and remaining space
            } else {  // If no block was suitable
                System.out.print("Not Allocated" + "\t" + remblockSize[i]);  // Mark as not allocated
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int m, n, num;
        Scanner in = new Scanner(System.in);
        
        // Ask for the number of memory blocks
        System.out.print("Enter how many number of blocks you want to enter:");
        m = in.nextInt();
        
        // Arrays to store block sizes and remaining block sizes
        int remblockSize[] = new int[m];
        int blockSize[] = new int[m];
        
        // Input block sizes from the user
        for (int i = 0; i < m; i++) {
            System.out.print("Enter Data " + (i + 1) + ":");
            num = in.nextInt();
            blockSize[i] = num;  // Store the block size
        }
        
        // Ask for the number of processes
        System.out.print("Enter how many number of processes you want to enter:");
        n = in.nextInt();
        
        // Array to store process sizes
        int processSize[] = new int[n];
        
        // Input process sizes from the user
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Data " + (i + 1) + ":");
            num = in.nextInt();
            processSize[i] = num;  // Store the process size
        }
        
        // Call the bestFit function to perform memory allocation
        bestFit(blockSize, m, processSize, n, remblockSize);
        
        // Close the scanner to prevent memory leak
        in.close();
    }
}

/*
 * This Java program implements the Best Fit memory allocation strategy, which
 * is used to assign processes to memory blocks efficiently. Here’s how it
 * works, step-by-step:
 * 
 * Key Concepts:
 * - Memory Blocks: These are fixed-sized memory units where processes can be
 *   stored.
 * - Processes: Each process has a certain memory requirement (size) that needs
 *   to be allocated to a block that fits it best.
 * 
 * How the Best Fit Strategy Works:
 * - For each process, the program searches through all blocks and finds the
 *   smallest block that can fit the process, ensuring minimal wastage of memory.
 * 
 * Output:
 * The program outputs a table displaying:
 * - Process Number
 * - Process Size
 * - Block number assigned to the process (or "Not Allocated" if no block fits)
 * - Remaining block size after allocation.
 */
