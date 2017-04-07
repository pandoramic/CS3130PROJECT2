/*
 * Cassie Short CS3130
 */

import java.util.*;

public class CS3130PROJECT2 {
    public static void main(String[] args) {

        // Create arrays and populate with random integers between 1 and 1000, separated by commas
        int randArr[] = new int[1000];
        int descArr[] = new int[1000];
        int ascArr[] = new int[1000];

        // Create random number generator for random array and variable to reverse for-loop index for descending array
        Random rand = new Random();
        int reverse = 1000;

        // Fill arrays with data
        System.out.println("Unsorted Array:");
        for (int i = 0; i < randArr.length; i++) {
            randArr[i] = (int)(Math.random() * 1000) + 1;
            // Use index + 1 to fill ascending array with consecutive integers between 1 and 1000
            ascArr[i] = i + 1;
            descArr[i] = reverse;
            // Print unsorted random array 
            if (i % 20 == 0 && i != 0) {
                System.out.print("\n");
            }
            System.out.format("%4d, ", randArr[i]);
            reverse--;
        }
        System.out.print("\n\n");

        // Create objects for holding information about each type of sort on each type of array input
        // Assign each a title string to display in print function
        ArrayType quickSort = new ArrayType();
        quickSort.title = "Original Quicksort ";
        ArrayType randomSort = new ArrayType();
        randomSort.title = "Random Quicksort ";
        ArrayType medOfThree = new ArrayType();
        medOfThree.title = "Median of Three Quicksort ";
        ArrayType medOfFive = new ArrayType();
        medOfFive.title = "Median of Five Quicksort ";

        // Fill each object's array attributes with appropriate pre-generated input
        copyArrs(quickSort, randArr, ascArr, descArr);
        copyArrs(randomSort, randArr, ascArr, descArr);
        copyArrs(medOfThree, randArr, ascArr, descArr);
        copyArrs(medOfFive, randArr, ascArr, descArr);

        // Pass each type of array to basic quicksort function, which uses last array element as pivot
        qSort(quickSort.random, 0, quickSort.random.arr.length - 1);
        qSort(quickSort.ascending, 0, quickSort.ascending.arr.length - 1);
        qSort(quickSort.descending, 0, quickSort.descending.arr.length - 1);
        print(quickSort);

        // Pass each type of array to random quicksort, which uses a random element as the pivot
        randSort(randomSort.random, 0, randomSort.random.arr.length - 1);
        randSort(randomSort.ascending, 0, randomSort.ascending.arr.length - 1);
        randSort(randomSort.descending, 0, randomSort.descending.arr.length - 1);
        print(randomSort);

        // Pass each array to the median of three quicksort, which chooses the median element of
        // three randomly chosen indices to use as the pivot
        medianThree(medOfThree.random, 0,medOfThree.random.arr.length - 1);
        medianThree(medOfThree.ascending, 0, medOfThree.ascending.arr.length - 1);
        medianThree(medOfThree.descending, 0, medOfThree.descending.arr.length - 1);
        print(medOfThree);

        // Pass each array to the median of five quicksort, which chooses the median element of
        // five randomly chosen indices to use as the pivot
        medianFive(medOfFive.random, 0, medOfFive.random.arr.length - 1);
        medianFive(medOfFive.ascending, 0, medOfFive.ascending.arr.length - 1);
        medianFive(medOfFive.descending, 0, medOfFive.descending.arr.length - 1);
        print(medOfFive);

        System.out.println("\nSummarized Quicksort Data:\n");
        summary(quickSort);
        summary(randomSort);
        summary(medOfThree);
        summary(medOfFive);
    }

    private static void qSort(Counter obj, int low, int high)    {
        long startTime = System.nanoTime();
        if(low < high) {
            int partition = sort(obj, low, high);
            qSort(obj, low, (partition - 1));
            qSort(obj, (partition + 1), high);
        }
        long endTime = System.nanoTime();
        obj.time = endTime - startTime;
    }

    private static void randSort(Counter obj, int low, int high) {
        long startTime = System.nanoTime();
        if(low < high) {
            int pivIndex = (int)(Math.random() * (high - low + 1)) + low;
            swap(obj, pivIndex, high);
            int partition = sort(obj, low, high);
            randSort(obj, low, (partition - 1));
            randSort(obj, (partition + 1), high);
        }
        long endTime = System.nanoTime();
        obj.time = endTime - startTime;
    }

    private static void medianThree(Counter obj, int low, int high) {
        long startTime = System.nanoTime();
        if((high - low) >= 3) {
            int pivots[] = new Random().ints(low, high + 1).distinct().limit(3).toArray();
            if (obj.arr[pivots[0]] > obj.arr[pivots[1]]) {
                swap(obj, pivots[0], pivots[1]);
            }
            obj.comparisons++;
            if (obj.arr[pivots[0]] > obj.arr[pivots[2]]) {
                swap(obj, pivots[0], pivots[2]);
            }
            obj.comparisons++;
            if (obj.arr[pivots[1]] > obj.arr[pivots[2]]) {
                swap(obj, pivots[1], pivots[2]);
            }
            obj.comparisons++;
            swap(obj, pivots[1], high);
        }
        if(low < high) {
            int partition = sort(obj, low, high);
            medianThree(obj, low, (partition - 1));
            medianThree(obj, (partition + 1), high);
        }
        long endTime = System.nanoTime();
        obj.time = endTime - startTime;
    }

    private static void medianFive(Counter obj, int low, int high)  {
        long startTime = System.nanoTime();
        if((high - low) >= 5) {
            int pivots[] = new Random().ints(low, high + 1).distinct().limit(5).toArray();
            if (obj.arr[pivots[0]] > obj.arr[pivots[1]]) {
                swap(obj, pivots[0], pivots[1]);
            }
            obj.comparisons++;
            if (obj.arr[pivots[2]] > obj.arr[pivots[3]]) {
                swap(obj, pivots[2], pivots[3]);
            }
            obj.comparisons++;
            if (obj.arr[pivots[0]] > obj.arr[pivots[2]]) {
                swap(obj, pivots[0], pivots[2]);
                swap(obj, pivots[1], pivots[3]);
            }
            obj.comparisons++;
            if (obj.arr[pivots[1]] > obj.arr[pivots[4]]) {
                swap(obj, pivots[1], pivots[4]);
            }
            obj.comparisons++;
            if (obj.arr[pivots[1]] < obj.arr[pivots[2]]) {
                if (obj.arr[pivots[2]] < obj.arr[pivots[4]]) {
                    swap(obj, high, pivots[2]);
                } else {
                    swap(obj, high, pivots[4]);
                }
                obj.comparisons++;
            } else {
                if (obj.arr[pivots[1]] < obj.arr[pivots[3]]) {
                    swap(obj, high, pivots[1]);
                } else {
                    swap(obj, high, pivots[3]);
                }
                obj.comparisons++;
            }
            obj.comparisons++;
        }
        if(low < high) {
            int partition = sort(obj, low, high);
            medianFive(obj, low, (partition - 1));
            medianFive(obj, (partition + 1), high);
        }
        long endTime = System.nanoTime();
        obj.time = endTime - startTime;
    }

    private static int sort(Counter obj, int low, int high)   {
        int pivot = obj.arr[high];
        int partition = low - 1;
        for (int j = low; j < high; j++) {
            if (obj.arr[j] <= pivot) {
                partition += 1;
                swap(obj, partition, j);
            }
            obj.comparisons++;
        }
        swap(obj, (partition + 1), high);
        return (partition + 1);
    }

    // Perform swaps using object and indices to swap
    private static void swap (Counter obj, int x, int y) {
        int temp = obj.arr[x];
        obj.arr[x] = obj.arr[y];
        obj.arr[y] = temp;
        obj.swaps++;
    }

    // Check that each possible pivot is unique. Call recursively to check indices that have already been checked when pivot is regenerated
    private static int redundancyCheck(int[] pivots, int possiblePiv, int high, int low) {
        for (int i = 0; i < pivots.length; i++) {
            if (pivots[i] == possiblePiv) {
                System.out.println(pivots[i] + " Redundancy Pre " + i + " " + possiblePiv);
                possiblePiv = (int)(Math.random() * (high - low + 1)) + low;
                System.out.println(pivots[i] + " Redundancy Post " + i + " " + possiblePiv);
                redundancyCheck(pivots, possiblePiv,  high, low);
            }
        }
        return possiblePiv;
    }

    // Copy arrays populated in main into each objects random, ascending, and descending attributes
    private static void copyArrs(ArrayType obj, int[] rArr, int[] dArr, int[] aArr)  {
        obj.random.arr = rArr.clone();
        obj.ascending.arr = aArr.clone();
        obj.descending.arr = dArr.clone();
    }

    // Print attributes of objects and array with 20 elements per line, separated by commas
    private static void print(ArrayType obj) {
        System.out.println(obj.title + "on Random Array: ");
        System.out.println("Comparisons: " + obj.random.comparisons + "\nSwaps: " + obj.random.swaps + "\n");
        System.out.println("This sort took " + obj.random.time + " nanoseconds to complete.\nSorted Array:");
        for (int i = 0; i < obj.random.arr.length; i++) {
            if (i % 20 == 0 && i != 0) {
                System.out.print("\n");
            }
            System.out.format("%4d, ", obj.random.arr[i]);
        }
        System.out.println("\n");
        System.out.println(obj.title + "on Ascending Array: ");
        System.out.println("Comparisons: " + obj.ascending.comparisons + "\nSwaps: " + obj.ascending.swaps);
        System.out.println("This sort took " + obj.ascending.time + " nanoseconds to complete.\nSorted Array:");
        for (int i = 0; i < obj.ascending.arr.length; i++) {
            if (i % 20 == 0 && i != 0) {
                System.out.print("\n");
            }
            System.out.format("%4d, ", obj.ascending.arr[i]);
        }
        System.out.print("\n\n");
        System.out.println(obj.title + "on Descending Array: ");
        System.out.println("Comparisons: " + obj.descending.comparisons + "\nSwaps: " + obj.descending.swaps);
        System.out.println("This sort took " + obj.descending.time + " nanoseconds to complete.\nSorted Array:");
        for (int i = 0; i < obj.descending.arr.length; i++) {
            if (i % 20 == 0 && i != 0) {
                System.out.print("\n");
            }
            System.out.format("%4d, ", obj.descending.arr[i]);
        }
        System.out.println("\n");
        for(int j = 0; j < 2; j++)  {
            for(int i = 0; i < 20; i++)    {
                System.out.print("~~~~~~");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static void summary(ArrayType obj)   {
        System.out.println("\n" + obj.title + "Random Array: ");
        System.out.println("Time: " + (obj.random.time/1000000.0) + "\nComparisons: " + obj.random.comparisons + "\nSwaps: " + obj.random.swaps);
        System.out.println("\n" + obj.title + "Ascending Array: ");
        System.out.println("Time: " + (obj.ascending.time/1000000.0) + "\nComparisons: " + obj.ascending.comparisons + "\nSwaps: " + obj.ascending.swaps);
        System.out.println("\n" + obj.title + "Descending Array: ");
        System.out.println("Time: " + (obj.descending.time/1000000.0) + "\nComparisons: " + obj.descending.comparisons + "\nSwaps: " + obj.descending.swaps);
    }
}


// Defines attributes of each type of sort object used, with Counter attributes to define each type of array
class ArrayType  {
    Counter random;
    Counter ascending;
    Counter descending;
    String title;

    ArrayType()    {
        random = new Counter();
        ascending = new Counter();
        descending = new Counter();
        title = "";
    }
}

// Define attributes of each type of array object, including comparison/swap counts, array, and duration
class Counter {
    int comparisons;
    int swaps;
    int arr[];
    long time;

    Counter() {
        comparisons = 0;
        swaps = 0;
        arr = new int[1000];
        time = 0;
    }
}