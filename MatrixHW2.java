public class MatrixHW2 {
    
    private static int multCount = 0;
    
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
    private static int addCount = 0;
    public static void testMult(double[][] A, double[][] B) {
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("Product does not exist!");
        }
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
    
    public static double[][] testMultRec(double[][] A, double[][] B) {
        multRec(A, B);
        return;
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
}