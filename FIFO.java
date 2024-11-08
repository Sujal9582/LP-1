import java.util.Scanner;

public class FIFO {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);  // Create Scanner object for taking user input
        
        int noofpages, capacity, index = 0;  // Declare variables for number of pages, frame capacity, and current index
        int hit = 0, fault = 0;  // Initialize counters for hits and faults
        double faultRatio, hitRatio;  // Variables to store fault and hit ratios
        
        System.out.print("Enter the number of pages you want to enter: ");
        noofpages = sc.nextInt();  // Input the total number of pages
        
        int pages[] = new int[noofpages];  // Array to store page numbers
        
        // Input page numbers from the user
        for (int i = 0; i < noofpages; i++) {
            pages[i] = sc.nextInt();  // Store each page in the pages array
        }
        
        System.out.print("Enter the capacity of frame: ");
        capacity = sc.nextInt();  // Input the frame capacity (number of frames)
        
        int frame[] = new int[capacity];  // Array to represent the frames in memory
        int table[][] = new int[noofpages][capacity];  // 2D array to keep track of frame state over time
        
        // Initialize each frame slot with -1 to indicate empty slots
        for (int i = 0; i < capacity; i++) {
            frame[i] = -1;
        }
        
        System.out.println("\n----------------------------------------------------------------------");
        
        // Main loop for handling each page in the pages array
        for (int i = 0; i < noofpages; i++) {
            int search = -1;  // Variable to check if the current page is already in the frame
            
            // Loop through the frame to check if the page is already loaded (search for a hit)
            for (int j = 0; j < capacity; j++) {
                if (frame[j] == pages[i]) {  // Check if current page matches frame page
                    search = j;  // Set search to the index of the page if found
                    hit++;  // Increment hit counter for a successful search (hit)
                    System.out.printf("%4s", "H");  // Print "H" to indicate a hit
                    break;  // Exit loop as page is found
                }
            }
            
            // If the page is not found in the frame (indicating a fault)
            if (search == -1) {
                frame[index] = pages[i];  // Insert page at the current index
                fault++;  // Increment fault counter for a page fault
                System.out.printf("%4s", "F");  // Print "F" to indicate a fault
                index++;  // Increment index to the next position in the frame
                
                // If index reaches the capacity, reset it to 0 (FIFO behavior)
                if (index == capacity) {
                    index = 0;
                }
            }
            
            // Copy the current frame state to the table array for future display
            System.arraycopy(frame, 0, table[i], 0, capacity);
        }
        
        System.out.println("\n----------------------------------------------------------------------");
        
        // Loop to print the table showing frame content at each step
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < noofpages; j++) {
                System.out.printf("%3d ", table[j][i]);  // Print each frame's content at each step
            }
            System.out.println();  // New line after each frame column
        }
        
        System.out.println("----------------------------------------------------------------------");
        
        // Calculate fault and hit ratios as percentages
        faultRatio = ((double) fault / noofpages) * 100;
        hitRatio = ((double) hit / noofpages) * 100;
        
        // Print the total page faults, hits, hit ratio, and fault ratio
        System.out.println("Page Fault: " + fault + "\nPage Hit: " + hit);
        System.out.printf("Hit Ratio:%.2f \nFault Ratio:%.2f ", hitRatio, faultRatio);
        
        sc.close();  // Close the scanner to prevent resource leaks
    }
}

/*
 * This program implements the First-In-First-Out (FIFO) page replacement algorithm,
 * commonly used in operating systems for managing memory pages in virtual memory.
 * It calculates and displays the number of page faults and hits that occur
 * as pages are loaded into a memory frame of specified capacity.
 * 
 * Steps involved:
 * 
 * 1. Input Section:
 *    - User inputs the total number of pages and the page sequence (stored in pages array).
 *    - User inputs the frame capacity (maximum number of pages memory can hold at any time).
 * 
 * 2. Frame Initialization:
 *    - The frame array of size 'capacity' holds the pages currently in memory.
 *    - Initially, each frame slot is set to -1 (empty).
 * 
 * 3. Page Replacement Logic:
 *    - For each page in pages[]:
 *      - Check if it is already present in the frame (searching for a hit).
 *      - If found, increment the hit counter and display "H".
 *      - If not found, replace the page at the current index (FIFO order), increment fault counter, and display "F".
 *      - Increment index after each insertion and reset if it reaches frame capacity.
 * 
 * 4. Storing Frame State for Display:
 *    - Each page request's frame state is stored in a table 2D array for later display.
 * 
 * 5. Output Display:
 *    - Display the contents of each frame over time.
 *    - Display the total page faults, hits, and ratios of page faults and hits.
 * 
 * 6. Calculations:
 *    - Fault Ratio: (fault / noofpages) * 100
 *    - Hit Ratio: (hit / noofpages) * 100
 * 
 * Example Execution:
 * - Entering a sequence of pages and frame capacity produces a table showing frame content changes and a summary of hits/faults.
 */
