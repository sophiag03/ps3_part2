/* File: MyArray
 * Date: 3 October 2023
 * Author:  Sophia Guilbeault 
 * Purpose: Creating and manipulating a MyArray object
 */

import java.util.*;                    

public class MyArray  {

    private static final int SENTINEL = -999;  // signalizes end of input
    private static final int DEFAULT_SIZE = 20; 
    private static final int LOWER = 10;
    private static final int UPPER = 50;

    private int[] arr; //MyArray object
    private int numElements = 0; // number of elements entered into an array
    private int sum = 0;
    private int min = 0;
    private int max = 0;
    private int avg = 0;
    private int mid = 0;
    Scanner input = new Scanner(System.in);

// CONSTRUCTORS

    /* CONSTRUCTOR
     * 
     * if no input...
     * initializes a new MyArray object with the default size
     * 
     */
    public MyArray() {
       this.arr = new int[DEFAULT_SIZE];
       numElements = 0;
       inputElements();

       computeStatistics();
    }

    /* CONSTRUCTOR
     * 
     * with integer input...
     * takes in an integer and initializes a MyArray object
     * that is the length of the inputted integer
     * 
     * then asks user to input integers if wanted
     * 
     */
    public MyArray(int n) {
        if(n < 0)
            throw new IllegalArgumentException();
        this.arr = new int[n];
        numElements = 0;
        inputElements();

        computeStatistics();
    }

     /* CONSTRUCTOR 
     * 
     * with array input...
     * takes in an array and initializes the MyArray object
     * as a copy of the inputted array
     * 
     */
    public MyArray(int[] arr) {
        if(arr == null)
            throw new IllegalArgumentException();
        this.arr = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            if (validInput(arr[i])) {
                this.arr[i] = arr[i];
                this.numElements += 1;
            }
        }
        computeStatistics();
    }

//METHODS

    /*method inputElements
     * 
     * asks the user for input and cycles elements through the
     * validInput method and adds them to the MyArray object
     * if warranted 
     * computes statistics after final inputs 
     * 
     */
    public void inputElements() {
        System.out.println("Enter up to " + this.arr.length + " integers between " + LOWER + " and " + UPPER + " inclusive. Enter " + SENTINEL + " to end user input: ");
        int element = input.nextInt();

        recurElements(element);

        computeStatistics();
    }

    /*method recurElements - helper method
     * 
     * used during the inputElements method to
     * continue the input reader
     * 
     */
    public void recurElements(int e) {

        if(validInput(e) && numElements < this.arr.length) {
            this.arr[numElements] = e;
            numElements += 1;
            recurElements(input.nextInt());
        }
        else if (e == -999)
            input.close();
        else { 
            recurElements(input.nextInt());
        }

    }

    /*method validInput
     * 
     * takes in a number and tests to see if it is between
     * the LOWER and UPPER bounds 
     * before allowing it to be added to the object
     * 
     */
    public static boolean validInput(double num) {
        boolean bool = false;
        if (num >= LOWER && num <= UPPER){
            bool = true;
        }
        return bool;
    }

    /* method computeStatistics
     * 
     * calculates the statistics of the MyArray object
     * 
     */
    public void computeStatistics() {
        sum = 0;
        min = 0;
        max = 0;
        avg = 0;
        mid = 0;
        if(numElements  != 0) {
        //SUM
        for (int i = 0; i < numElements; i++) {
            
            sum =  sum + arr[i];
        }

        //MIN
        for (int i = 0; i < numElements; i++) {
            if(arr[i] < min) {
                min = arr[i];
            }
        }

        //MAX
        for (int i = 0; i < numElements; i++) {
            if (arr[i] > max)
                max = arr[i];
        }

        //AVG
        for (int i = 0; i < numElements; i++) {
            avg += this.arr[i];
        }
        if (numElements > 0)
            avg = avg/numElements;

        //MID
        if (numElements % 2 != 0) {
            int pos = numElements/2;
            mid = arr[pos];
        }
        if (numElements % 2 == 0) {
            int pos1 = numElements/2;
            int pos2 = pos1 - 1;
            mid = (arr[pos1] + arr[pos2])/2;
        }          
    }  
    }

    /* method toString
     * 
     * returns the MyArray object as a string
     * includes only the user inputted numbers, not the entire array
     * 
     */
    public String toString() {
        int[] newArr = new int[numElements];
        for (int i = 0; i < newArr.length; i++)
            newArr[i] = this.arr[i];
        return Arrays.toString(newArr);
    }

    /*method numOccurrences
     * 
     * takes in an int
     * cycles through elements in the MyArray object and
     * returns how many times the integers occurrs in the array
     * 
     */

    public int numOccurences(int n) {
        int occurr = 0;
        for (int i = 0; i < this.arr.length; i ++) {
            if (this.arr[i] == n)
                occurr += 1;
        }
        return occurr;
    }

    /*method insert
     * 
     * takes in an integer and the position
     * tests to see if the position is within the 
     * currently filled portion of the MyArray object
     * if so, inserts the integer and returns true
     * 
     * otherwise returns false
     * 
     */
    public boolean insert(int n, int position) {
        boolean bool = false;
        if (position >= numElements || position < 0) // position outside filled portion
            bool = false; 
        else if (numElements == this.arr.length) // arr is full
            bool = false;
        else{ 
            bool = true;
            int[] temp = new int[this.arr.length];
            for(int i = 0; i < position; i++) {
                temp[i] = this.arr[i];
            }

            temp[position] = n;

            for(int i = position+1; i < this.arr.length; i++) {
                temp[i] = this.arr[i-1];
            }
        }
        computeStatistics();
        return bool;
    }

    /*method remove
     * 
     * takes in a position and if a valod entry removes the entry 
     * and returns the value removed
     * 
     * if not valid, returns -1
     */
    public int remove(int position) {
        int retInt = -1;
        if (position >= 0 && position < numElements) {
            retInt = this.arr[position];
            int[] temp = new int[this.arr.length - 1];
            for(int i = 0; i < position; i++) {
                temp[i] = this.arr[i];
            }

            for (int i = position + 1; i < this.arr.length; i++) {
                temp[i-1] = this.arr[i];
            } 
            this.arr = temp;
            numElements -= 1;
        }
        computeStatistics();
        return retInt;
    }

    /*method grow
     * 
     * takes in an integer n and "grows" the MyArray 
     * object by n
     * 
     * returns false if n <=0
     * 
     */
    public boolean grow(int n) {
        boolean bool = false;
        if (n <= 0) {
            bool = false;
        }
        else {
            bool = true;
            int[] temp = new int[this.arr.length + n];
            for (int i = 0; i < this.arr.length; i++) {
                temp[i] = this.arr[i];
            }
            this.arr = temp;
        }
        computeStatistics();
        return bool;
    }

    /* method computeHistogram
     * 
     * creates a string representation of the MyArray object
     * using * and returns the string 
     * 
     */
    public String computeHistogram() {
        String s = "";
        for (int i = 0; i < this.arr.length; i++) {
            for (int x = 0; x < this.arr[i]; x++)
                s += "*";
            s += "\n";
        }
        return s;
    }

//ACCESSORS

    //returns the MyArray object
    public int[] getArr(){
        computeStatistics();
        return this.arr;
    }

    // return sum
    public int getSum() {
        computeStatistics();
        return sum;
    }

    //return min
    public int getMin() {
        computeStatistics();
        return min;
    }
    
    //return max
    public int getMax() {
        computeStatistics();
        return max;
    }

    //return avg
    public int getAvg() {
        computeStatistics();
        return avg;
    }

    //return mid
    public int getMid() {
        computeStatistics();
        return mid;
    }

    public int getElements() {
        return numElements;
    }

    public static void main(String [] args) {

        System.out.println("\nUnit Test for MyArray.\n");
        //Scanner input = new Scanner(System.in);
        //int[] a = {3, 6, 7, 9, 11, 23};
        MyArray mine = new MyArray();
        System.out.println(mine.toString());
        System.out.println(mine.getElements());
        System.out.println(mine.getAvg());
        System.out.println(mine.getSum());
        System.out.println(mine.getMid());
        System.out.println(mine.getMax());
        System.out.println(mine.getMin());
        System.out.println(mine.computeHistogram());   

	// TBA

    }
}
