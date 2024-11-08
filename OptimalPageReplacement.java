import java.util.Scanner;

public class OptimalPageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Create a Scanner object for user input
        int noofpages, capacity, ptr = 0, hit = 0, fault = 0; // Variables for number of pages, frame capacity, and counters
        boolean isFull = false; // Flag to check if the frame is fully utilized
        double hitRatio, faultRatio; // Variables for calculating hit and fault ratios

        System.out.print("Enter the number of pages you want to enter: ");
        noofpages = sc.nextInt(); // Input the number of pages

        int pages[] = new int[noofpages]; // Array to store page requests
        for (int i = 0; i < noofpages; i++) { // Loop to input each page number
            pages[i] = sc.nextInt();
        }

        System.out.print("Enter the capacity of frame: ");
        capacity = sc.nextInt(); // Input the frame capacity (number of slots)

        int frame[] = new int[capacity]; // Array representing the memory frame
        int table[][] = new int[noofpages][capacity]; // Table to store frame states after each page request for display

        for (int i = 0; i < capacity; i++) {
            frame[i] = -1; // Initialize all frame slots to -1 (indicating empty)
        }

        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < noofpages; i++) { // Loop over each page request
            int search = -1; // Variable to store index if the page is found in frame

            // Check if the page is already in the frame (page hit)
            for (int j = 0; j < capacity; j++) {
                if (frame[j] == pages[i]) { // If page is found in frame
                    search = j;
                    hit++; // Increment the hit count
                    System.out.printf("%4s", "H"); // Print "H" for hit
                    break;
                }
            }

            // If page is not found in frame (page fault)
            if (search == -1) {
                if (isFull) { // If frame is full, find optimal page to replace
                    int[] index = new int[capacity]; // Array to store future use index of each frame page
                    boolean[] index_flag = new boolean[capacity]; // Flag array to track if a page’s future use has been recorded

                    // Check future occurrences of each page in the frame to determine optimal replacement
                    for (int j = i + 1; j < noofpages; j++) { // Loop over remaining pages
                        for (int k = 0; k < capacity; k++) { // Loop over frame slots
                            if ((pages[j] == frame[k]) && (!index_flag[k])) { // If page in frame will be used later
                                index[k] = j; // Record its future index in `index` array
                                index_flag[k] = true; // Mark it as found
                                break;
                            }
                        }
                    }

                    // Find the page with the longest future use or not needed again
                    int max = index[0];
                    ptr = 0; // Pointer for replacement position
                    if (max == 0) max = 200; // If no future use, set max to a high value

                    for (int j = 0; j < capacity; j++) { // Loop over indices to find the optimal page to replace
                        if (index[j] == 0) index[j] = 200; // Set to high if page not found in future
                        if (index[j] > max) { // Find page with longest future use
                            max = index[j];
                            ptr = j; // Update replacement pointer
                        }
                    }
                }

                frame[ptr] = pages[i]; // Replace the selected page with the current page request
                fault++; // Increment the fault count
                System.out.printf("%4s", "F"); // Print "F" for fault

                if (!isFull) { // If frame wasn't full, move pointer to next empty slot
                    ptr++;
                    if (ptr == capacity) { // Once frame is full, set `isFull` to true
                        ptr = 0;
                        isFull = true;
                    }
                }
            }

            System.arraycopy(frame, 0, table[i], 0, capacity); // Copy the current frame state to table for display
        }

        System.out.println("\n----------------------------------------------------------------------");

        // Print the frame table showing the contents of each slot after each page request
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < noofpages; j++) {
                System.out.printf("%3d ", table[j][i]); // Print each frame state
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------------------------");

        hitRatio = ((double) hit / noofpages) * 100; // Calculate hit ratio as a percentage
        faultRatio = ((double) fault / noofpages) * 100; // Calculate fault ratio as a percentage

        System.out.println("Page Fault: " + fault + "\nPage Hit: " + hit); // Print total faults and hits
        System.out.printf("Hit Ratio:%.2f \nFault Ratio:%.2f ", hitRatio, faultRatio); // Print hit and fault ratios

        sc.close(); // Close the Scanner to release resources
    }
}

/*
 * The OptimalPageReplacement program demonstrates the optimal page replacement algorithm for memory management.
 * Key points:
 * 1. **User Input**:
 *    - Prompts for page requests (pages) and frame capacity (frame slots).
 * 2. **Optimal Replacement Logic**:
 *    - If a page fault occurs and the frame is full, it replaces the page that will not be used for the longest time in the future.
 * 3. **Output**:
 *    - Prints "H" for hits and "F" for faults after each page request.
 *    - Shows the frame state after each request and calculates hit/fault ratios.
 *
 * Example Flow:
 * Given pages `[1, 2, 3, 4, 2]` with frame capacity of `3`:
 * - Loads pages into frame, replacing based on future needs, which minimizes faults.
 *
 * This program is efficient for scenarios with known future page requests, minimizing faults by choosing optimal replacements.
 */
