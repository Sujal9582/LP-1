import java.util.Scanner;  // Importing Scanner class for user input

public class WorstFit {

	static void worstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
		int allocation[] = new int[n];  // Array to store allocated block index for each process
		
		// Initialize all allocations as -1 (indicating not allocated)
		for (int i = 0; i < allocation.length; i++) {
			allocation[i] = -1;
		}
		
		// Loop through each process to allocate memory blocks
		for (int i = 0; i < n; i++) {
			int wstIdx = -1;  // Initialize the worst fit block index as -1 (no block chosen yet)
			
			// Find the largest block that can accommodate the current process
			for (int j = 0; j < m; j++) {
				if (blockSize[j] >= processSize[i]) {  // Check if block size is sufficient
					// Update worst fit index if it's the largest so far
					if (wstIdx == -1)
						wstIdx = j;
					else if (blockSize[wstIdx] < blockSize[j])
						wstIdx = j;
				}
			}
			
			// Allocate block to process if suitable block was found
			if (wstIdx != -1) {
				allocation[i] = wstIdx;  // Assign block index to process
				blockSize[wstIdx] -= processSize[i];  // Reduce block size by process size
				remblockSize[i] = blockSize[wstIdx];  // Update remaining block size after allocation
			}
		}

		// Display allocation results
		System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaining Block Size");
		for (int i = 0; i < n; i++) {
			System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
			
			// Print allocated block or indicate that allocation failed
			if (allocation[i] != -1)
				System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
			else
				System.out.print("Not Allocated" + "\t" + remblockSize[i]);
			
			System.out.println();  // New line for the next process
		}
	}

	public static void main(String[] args) {
		int m, n, num;  // Variables for the number of blocks, processes, and input values
		Scanner in = new Scanner(System.in);  // Create Scanner object for input

		// Get number of memory blocks and their sizes
		System.out.print("Enter how many number of blocks you want to enter:");
		m = in.nextInt();  // Read number of blocks
		int remblockSize[] = new int[m];  // Array to store remaining sizes after allocation
		int blockSize[] = new int[m];  // Array to store original block sizes
		
		// Input each block size
		for (int i = 0; i < m; i++) {
			System.out.print("Enter Data " + (i + 1) + ":");
			num = in.nextInt();  // Read block size
			blockSize[i] = num;  // Assign block size to array
		}

		// Get number of processes and their sizes
		System.out.print("Enter how many number of processes you want to enter:");
		n = in.nextInt();  // Read number of processes
		int processSize[] = new int[n];  // Array to store sizes of each process
		
		// Input each process size
		for (int i = 0; i < n; i++) {
			System.out.print("Enter Data " + (i + 1) + ":");
			num = in.nextInt();  // Read process size
			processSize[i] = num;  // Assign process size to array
		}

		// Call worstFit method to allocate memory blocks to processes
		worstFit(blockSize, m, processSize, n, remblockSize);
		in.close();  // Close the scanner to release resources
	}

}
