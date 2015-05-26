import java.util.*;

public class MatrixHWTester {
    
    private static int multCount = 0; 
    private static int addCount = 0;
    
    public static void main(String[] args) {
        double[][] A = {{1,2}, {3,4}};
        double[][] B = {{1,2,3}, {4,5,6}, {7,8,9}};
        double[][] C = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,16}};
        double[][] D = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        
        System.out.println("mult(A,A)");
        double[][] A1 = mult(A,A);
        printArray(A1);
        testMult(A,A);
        System.out.println("multRec(A,A)");
        double[][] A2 = multRec(A,A);
        printArray(A2);
        testMultRec(A,A);
        System.out.println("multStrassen(A,A)");
        double[][] A3 = multStrassen(A,A);
        printArray(A3);
        testMultStrassen(A,A);

        System.out.println("mult(B,B)");
        printArray(mult(B,B));
        testMult(B,B);
        System.out.println("multRec(B,B)");
        printArray(multRec(B,B));
        testMultRec(B,B);
        System.out.println("multStrassen(B,B)");
        printArray(multStrassen(B,B));
        testMultStrassen(B,B);
        
        System.out.println("mult(C,C)");
        printArray(mult(C,C));
        testMult(C,C);
        System.out.println("multRec(C,C)");
        printArray(multRec(C,C));
        testMultRec(C,C);
        System.out.println("multStrassen(C,C)");
        printArray(multStrassen(C,C));
        testMultStrassen(C,C);
        
        System.out.println("mult(D,D)");
        printArray(mult(D,D));
        testMult(D,D);
        System.out.println("multRec(D,D)");
        printArray(multRec(D,D));
        testMultRec(D,D);
        System.out.println("multStrassen(D,D)");
        printArray(multStrassen(D,D));
        testMultStrassen(D,D);
        
    }
    
    
    
    /* 
 * Aihoa Le
 * CS132 | Summer I
 * 
 * MatrixHW contains methods that run linear algebra operations on
 * matrices.
 */


    

    
    // given an augmented matrix A, do G.J. Elimination and print out steps
    public static void gaussJordan(double [][] A) {
        System.out.println("\nRunning Gauss-Jordan...\n"); 
        A = copy(A);       
        printArray(A);
        int c = 0;
        int r = 0;
        while(c < A[0].length - 1) {
            for (int r2 = r; r2 < A.length; r2++) {
                if (r2 == A.length) {
                    c++;
                }
                if (A[r2][c] == 0.0) {
                    for (int r3 = r2; r3 < A.length; r3++) {
                        if (A[r3][c] != 0.0) {
                            swapRows(A, r3, r2);
                        }
                    }
                }
                for (int r3 = 0; r3 < A.length; r3++) {
                    if (A[r2][c] == 1.0 && A[r3][c] != 0.0) {
                        if (r3 != r2) {
                            addToRow(A, r3, r2, (A[r3][c] * -1));
                        }
                    }                    
                    if (A[r2][c] != 1.0 && A[r2][c] != 0.0) {
                        if (A[r2][c] == -1.0) {
                            multiplyRow(A, r2, -1.0/A[r2][c]);
                        } else {
                            multiplyRow(A, r2, 1.0/A[r2][c]);
                            if (r3 != r2) {
                                addToRow(A, r3, r2, (A[r3][c] * -1));
                            }
                        }                   
                    }   
                }
                printArray(A);              
            }        
            c++;
            r++;            
        }
        printSolution(A);
    }
    
    
    
    
    
    
    /*****************************************************************
      *****************************************************************
      ************  COULDN'T FIGURE THIS OUT IN TIME! :( ***************
      *****************************************************************
      *****************************************************************/
    
    // given an augmented matrix A, do G. Elimination and print out steps
    // At the end, report whether is no, 1, or multiple solutions
    public static void gaussianElimination(double [][] A) {
        System.out.println("\nRunning Gaussian Elimination...\n"); 
        A = copy(A);
        printArray(A);
        int c = 0;
        int r = 0;
        while(c < A[0].length-1) {
            for (int r2 = r; r2 < A.length; r2++) {
                if (r2 == A.length) {
                    c++;
                }
                if (A[r2][c] == 0.0) {
                    for (int r3 = r2; r3 < A.length; r3++) {
                        if (r3 != r2) {
                            swapRows(A, r2, r3);
                        }
                    }
                }                
                for (int r3 = 0; r3 < A.length; r3++) {
                    if (r3 != r2 && A[r2][c] == 1.0 && A[r3][c] != 0.0) {
                        addToRow(A, r3, r2, (A[r3][c] * -1));
                    }
                    if (A[r2][c] != 1.0 && A[r2][c] != 0.0) {
                        multiplyRow(A, r2, 1.0/A[r2][c]);
                    }                    
                }  
                printArray(A);              
            }
            c++;
            r++;
        }  
        
        // back-substitute the variables from bottom row to top
        System.out.println("Back Substituting....\n"); 
        
        // if no solution, no need to do back substitution
        if(!noSolution(A)) {
            for (int rLast = A.length - 1; r > 0; rLast--) {
                for (c = 0; c < A[0].length - 1; c++) {
                    if (A[rLast][c] != 1.0 && c != A[0].length - 1) {
                        addToRow(A, rLast - 1, rLast, A[rLast - 1][c] * -1);
                    }
                    printArray(A);
                }
            }
        }
        printSolution(A); 
    }  
    
    
    // exchanges the rows within a matrix
    private static void swapRows(double[][] A, int r1, int r2) {
        double[] temp = A[r1];
        A[r1] = A[r2];
        A[r2] = temp;
    }    
    
    // multiplies a row by a real number and keeps track of multiplication
    private static void multiplyRow(double[][] A, int i, double n) {
        for (int j = 0; j < A[0].length; j++) {
            A[i][j] *= n;
            multCount++;
        }
    }
    
    // increments the elements in a row by a given amount
    private static void addToRow(double[][] A, int r1, int r2, double n) {
        for (int j = 0; j < A[0].length; j++) {
            A[r1][j] += n * A[r2][j];
            multCount++;
        }
    }
    
    // noSolution returns true if any row has all 0's except for last column
    private static boolean noSolution(double [][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (j == A[i].length - 1) {
                    return (A[i][j] != 0.0); 
                }
                if (j != A[i].length - 1) {
                    if (A[i][j] != 0.0) {
                        break;
                    } 
                }
            }
        }
        return false;
    }
    
    
    // determines if matrix is in the form of an augmented identity matrix
    private static boolean uniqueSolution(double[][] A) {
        // checking for correct dimensions
        if (A.length != A[0].length - 1) {
            return false;
        }
        // checking for augmented identity matrix
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length - 1; j++) {
                if (i == j) {
                    if (A[i][j] != 1) {
                        return false;
                    }
                }
                if (i != j) {
                    if (A[i][j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }        
    // prints out matrix
    public static void printArray(double [][] A) {
        for(int r = 0; r < A.length; ++r) {
            for(int c = 0; c < A[0].length; ++c)
                System.out.print(formatDouble(A[r][c]) + "\t");
            System.out.println(); 
        }
        System.out.println();
    }
    
    // prints out solution from a final matrix, only called if know is a solution, otherwise is nonsense
    // works for unique or multiple solutions
    private static void printSolution(double [][] A) {
        // generate solution or report that is no solution
        if(noSolution(A)) {
            System.out.println("No Solution!");
            System.out.println("Number of multiplications: " + multCount); 
            multCount = 0;
            return; 
        }
        else if(!uniqueSolution(A)) {
            System.out.print("Multiple Solutions:   ");
        } else {
            System.out.print("Unique Solution:   ");
        }
        
        // round everything to 2 decimal places
        
        for(int r = 0; r < A.length; ++r) 
            for(int c = 0; c < A[0].length; ++c) 
            A[r][c] = formatDouble(A[r][c]);
        
        // now print out values for variables
        
        for(int r = 0; r < A.length; ++r) {
            // for each row, print out binding for that variable 
            int c; 
            for(c = 0; c < A[r].length && A[r][c] == 0.0; ++c) 
                ;
            if(c == A[r].length)              // this row was all 0's
                continue; 
            // now c is column of variable for this row, create binding
            System.out.print("X" + (c+1) + " = " + A[r][A[r].length-1]);
            // now subtract any other parameters, if not a unique solution
            for( ++c; c < A[r].length-1; ++c) {
                if(A[r][c] == 1.0)
                    System.out.print(" - X" + (c+1)); 
                else if(A[r][c] == -1.0)
                    System.out.print(" + X" + (c+1)); 
                else if(A[r][c] < 0.0)
                    System.out.print(" + " + (-A[r][c]) + "*X" + (c+1)); 
                else if(A[r][c] > 0.0)
                    System.out.print(" - " + A[r][c] + "*X" + (c+1)); 
            }
            if(r != A.length-1)
                System.out.print("; ");
        }
        System.out.println(); 
        System.out.println("Number of multiplications: " + multCount); 
        multCount = 0;
        
    }
    
    // round doubles to two decimal places
    private static double formatDouble(double x) {
        return Math.round(x * 100) / 100.0; 
    }
    
    // returns a new copy of a matrix    
    private static double [][] copy(double [][] A) {
        double[][] newA = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                newA[i][j] = A[i][j];
            }
        }
        return newA;
    }
    
    /************************************************************
      * *********************************************************
      * ************   PROGRAMMING PROJECT 2 ********************
      * ******************   6/12/13   **************************
      * *********************************************************
      * *********************************************************/
    
    public static double[][] inverse(double[][] B){
        // create new array
        double[][] inverse = new double[B.length][B.length * 2];
        // copy old array
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                inverse[i][j] = B[i][j];
            }
        }
        // add identity matrix
        for (int i = 0; i < B.length; i++) {
            for (int j = B.length; j < inverse[0].length; j++) {
                if ((j - B.length) == i) {
                    inverse[i][j] = 1;
                } else {
                    inverse[i][j] = 0;
                }
            }
        }
        // eliminate to find inverse
        int i = 0;
        int j = 0;
        for (int i2 = i; i2 < inverse.length; i2++) {
            for (int j2 = 0; j2 < inverse[0].length; j2++) {
                if (B[i2][j2] != 1.0) {
                    for (int k = 0; k < inverse[0].length; k++) {
                        inverse[i2][k] = inverse[i2][k] / inverse[i2][j2];
                    }
                }
            }  
        }
        
        return inverse;
        }
    
    // Multiplies two matrices.
    public static double[][] mult(double[][] A, double[][] B) {
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("Columns of the first matrix must equal rows of the second!");
        }
        double[][] AB = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    AB[i][j] += A[i][k] * B[k][j];
                }
            } 
        }
        return AB;
    }
    
    // Prints the product of two matrices (if it exists) as well as total
    // multiplications of scalars and additions that took place.
    public static void testMult(double[][] A, double[][] B) {
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("Product does not exist!");
        }
        multCount = 0;
        addCount = 0;
        double[][] AB = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    AB[i][j] += A[i][k] * B[k][j];
                    multCount++;
                    addCount++;
                }
            } 
        }
        for (int i = 0; i < AB.length; i++) {
            for (int j = 0; j < AB[0].length; j++) {
                System.out.print(AB[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Multiplications: " + multCount);
        System.out.println("Additions: " + addCount);
    }
    
    // Multiplies two matrices recursively.
    public static double[][] multRec(double[][] A, double[][] B) { 
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("These matrices cannot be multiplied!");
        } 
        
        int originalRows = B.length;
        int originalCols = A[0].length;
        
        int m = Math.max(A.length, B.length);
        int n = Math.max(A[0].length, B[0].length);
        if (n > m) {
            m = n;
        }
        A = padMat(A, m);
        B = padMat(B, m);
        
        int aRows = A.length;
        int aCols = A[0].length;
        int bRows = B.length;
        int bCols = B[0].length;
        
        double[][] AB = new double[aRows][bCols];
        
        if (aRows == 2) {
            AB[0][0] = (A[0][0] * B[0][0]) + (A[0][1] * B[1][0]);
            AB[0][1] = (A[0][0] * B[0][1]) + (A[0][1] * B[1][1]);
            AB[1][0] = (A[1][0] * B[0][0]) + (A[1][1] * B[1][0]);
            AB[1][1] = (A[1][0] * B[0][1]) + (A[1][1] * B[1][1]);
            addCount += 4;
            multCount += 8;
            } else {                                                
                double[][] A1 = subMat(A, 0, aRows/2, 0, aCols/2);
                double[][] A2 = subMat(A, 0, aRows/2, aCols/2, aCols);
                double[][] A3 = subMat(A, aRows/2, aRows, 0, aCols/2);
                double[][] A4 = subMat(A, aRows/2, aRows, aCols/2, aCols);
                double[][] B1 = subMat(B, 0, bRows/2, 0, bCols/2);
                double[][] B2 = subMat(B, 0, bRows/2, bCols/2, bCols);
                double[][] B3 = subMat(B, bRows/2, bRows, 0, bCols/2);
                double[][] B4 = subMat(B, bRows/2, bRows, bCols/2, bCols);
                
                double[][] A1B1 = multRec(A1, B1);
                double[][] A2B3 = multRec(A2, B3);
                double[][] A1B2 = multRec(A1, B2);
                double[][] A2B4 = multRec(A2, B4);
                double[][] A3B1 = multRec(A3, B1);
                double[][] A4B3 = multRec(A4, B3);
                double[][] A3B2 = multRec(A3, B2);
                double[][] A4B4 = multRec(A4, B4);
                
                double[][] sum1 = addMat(A1B1, A2B3);
                double[][] sum2 = addMat(A1B2, A2B4);
                double[][] sum3 = addMat(A3B1, A4B3);
                double[][] sum4 = addMat(A3B2, A4B4);
                
                combineQuads(AB, sum1, 0, AB.length/2, 0, AB[0].length/2);
                combineQuads(AB, sum2, 0, AB.length/2, AB[0].length/2, AB[0].length);
                combineQuads(AB, sum3, AB.length/2, AB.length, 0, AB[0].length/2);
                combineQuads(AB, sum4, AB.length/2, AB.length, AB[0].length/2, AB[0].length);
            }
            AB = unpadMat(AB, originalRows, originalCols);
            return AB;
        }
    
    public static void testMultRec(double[][] A, double[][] B) {
        multCount = 0;
        addCount = 0;
        double[][] AB = multRec(A, B);
        for (int i = 0; i < AB.length; i++) {
            for (int j = 0; j < AB[0].length; j++) {
                System.out.print(AB[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Multiplications: " + multCount);
        System.out.println("Additions: " + addCount);
    }
    
    // Places sums in appropriate spots in product matrix.
    private static void combineQuads(double[][] A, double[][] subA, int r, int r2, int c, int c2) {
        int m = 0;
        int n = 0;
        for (int i = r; i < r2; i++) {
            for (int j = c; j < c2; j++) {
                A[i][j] = subA[m][n];
                n++;
            }
            m++;
            n = 0;
        }
    }
                
    // Makes a copy of a submatrix.
    private static double[][] subMat(double[][] A, int r, int r2, int c, int c2) {
        double[][] subA = new double[A.length/2][A[0].length/2];
        int n = 0;
        int m = 0;
        for (int i = r; i < r2; i++) {
            for (int j = c; j < c2; j++) {
                subA[m][n] = A[i][j];
                n++;
            }
            m++; 
            n = 0; 
        }
        return subA;
    }
   
    // Adds the elements in a mattrix
    private static double[][] addMat(double[][] A, double[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
            throw new IllegalArgumentException("Matrices must have equal dimensions!");
        }
        
        double[][] sumAB = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                sumAB[i][j] = A[i][j] + B[i][j];
                addCount++;
            }
        }
        return sumAB;
    }
    
    // Determines if dimensions of a matrix are a power of 2.
    private static boolean isPowerOfTwo(int dimension) {
        while (dimension % 2 == 0 && dimension > 1) {
            dimension /= 2;
        }
        return (dimension == 1);
    }

    // Adds padding to a matrix.
    private static double[][] padMat(double[][] A, int dimension) {
        while (!isPowerOfTwo(dimension)) {
            dimension++;
        }
        double[][] paddedMat = new double[dimension][dimension];
        for (int i = 0; i < paddedMat.length; i++) {
            for (int j = 0; j < paddedMat[0].length; j++) {
                if (i >= A.length || j >= A[i].length) {
                    paddedMat[i][j] = 0;
                } else {
                    paddedMat[i][j] = A[i][j];
                }
            }
        }
        return paddedMat;
    }
    
    // Removes trailing zeroes from rows and columns
    private static double[][] unpadMat(double[][] A, int r, int c) {
        double[][] unpaddedMat = new double[r][c];
        for (int i = 0; i < unpaddedMat.length; i++) {
            for (int j = 0; j < unpaddedMat[0].length; j++) {
                unpaddedMat[i][j] = A[i][j];
            }
        } 
        return unpaddedMat;
    } 
    
    // Multiplies two matrices using Strassen's method.
    public static double[][] multStrassen(double[][] A, double[][] B) { 
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("These matrices cannot be multiplied!");
        } 
        
        int originalRows = B.length;
        int originalCols = A[0].length;
        
        int m = Math.max(A.length, B.length);
        int n = Math.max(A[0].length, B[0].length);
        if (n > m) {
            m = n;
        }
        A = padMat(A, m);
        B = padMat(B, m);
        
        int aRows = A.length;
        int aCols = A[0].length;
        int bRows = B.length;
        int bCols = B[0].length;
        
        double[][] AB = new double[aRows][bCols];
        
        if (aRows == 2) {
            AB[0][0] = (A[0][0] * B[0][0]) + (A[0][1] * B[1][0]);
            AB[0][1] = (A[0][0] * B[0][1]) + (A[0][1] * B[1][1]);
            AB[1][0] = (A[1][0] * B[0][0]) + (A[1][1] * B[1][0]);
            AB[1][1] = (A[1][0] * B[0][1]) + (A[1][1] * B[1][1]);
            addCount += 4;
            multCount += 8;
            } else {                                                
                double[][] A1 = subMat(A, 0, aRows/2, 0, aCols/2);
                double[][] A2 = subMat(A, 0, aRows/2, aCols/2, aCols);
                double[][] A3 = subMat(A, aRows/2, aRows, 0, aCols/2);
                double[][] A4 = subMat(A, aRows/2, aRows, aCols/2, aCols);
                double[][] B1 = subMat(B, 0, bRows/2, 0, bCols/2);
                double[][] B2 = subMat(B, 0, bRows/2, bCols/2, bCols);
                double[][] B3 = subMat(B, bRows/2, bRows, 0, bCols/2);
                double[][] B4 = subMat(B, bRows/2, bRows, bCols/2, bCols);
                
                double[][] M1 = multStrassen(addMat(A1, A4), addMat(B1, B4));
                double[][] M2 = multStrassen(addMat(A3, A4), B1);
                double[][] M3 = multStrassen(A1, subtractMat(B2, B4));
                double[][] M4 = multStrassen(A4, subtractMat(B3, B1));
                double[][] M5 = multStrassen(addMat(A1, A2), B4);
                double[][] M6 = multStrassen(subtractMat(A3, A1), addMat(B1, B2));
                double[][] M7 = multStrassen(subtractMat(A2, A4), addMat(B3, B4));
                
                combineQuads(AB, (addMat(subtractMat(addMat(M1, M4), M5), M7)), 0, AB.length/2, 0, AB[0].length/2);
                combineQuads(AB, addMat(M3, M5), 0, AB.length/2, AB[0].length/2, AB[0].length);
                combineQuads(AB, addMat(M2, M4), AB.length/2, AB.length, 0, AB[0].length/2);
                combineQuads(AB, (addMat(addMat(subtractMat(M1, M2), M3),M6)), AB.length/2, AB.length, AB[0].length/2, AB[0].length);
            }
            AB = unpadMat(AB, originalRows, originalCols);
            return AB;
        }
    
    // Subtracts the elements in a mattrix
    private static double[][] subtractMat(double[][] A, double[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
            throw new IllegalArgumentException("Matrices must have equal dimensions!");
        }
        
        double[][] diffAB = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                diffAB[i][j] = A[i][j] - B[i][j];
                }
        }
        return diffAB;
    }
    
    public static void testMultStrassen(double[][] A, double[][] B) {
        multCount = 0;
        addCount = 0;
        double[][] AB = multStrassen(A, B);
        for (int i = 0; i < AB.length; i++) {
            for (int j = 0; j < AB[0].length; j++) {
                System.out.print(AB[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Multiplications: " + multCount);
        System.out.println("Additions: " + addCount);
    }
}