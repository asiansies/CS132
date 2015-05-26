public class LinearAlgebra {
   
     public static void main(String [] args) {


      // these have unique solutions
      
      double [][] B = { { 1.0, 1.0, 4.0}, { 2.0, -1.0, 2.0} };
      
      double [][] C = { 
         { 3.0, -3.0, 9.0, 24.0}, 
         { 2.0, -2.0, 7.0, 17.0}, 
         {-1.0, 2.0, -4.0, -11.0 } };
      
      // these have no solution
      double [][] D = {                        // Example 4, p.21
         { 1.0, 1.0, 5.0, 3.0}, 
         { 0.0, 1.0, 3.0, -1.0}, 
         { 1.0, 2.0, 8.0, 3.0 } };
      
      double [][] A = { {3.0, 3.4 }, {3.4, 5.6} };
      

      // examples and exercises from the book
      
      double [][] E121 = {            //  "E121" = "Exercise 1 from section 1.2"
         { 0, 0, 2, -2, 2}, 
         { 3, 3, -3, 9, 12}, 
         { 4, 4, -2, 11, 12 } };
      double [][] E122 = { 
         { 3.0, -3.0, 3.0, 9.0}, 
         { 2.0, -1.0, 4.0, 7.0}, 
         { 3.0, -5.0, -1.0, 7.0 } };
      
      double [][] E123 = {                     // Ex.3 p.20
         { 1.0, 2.0, -1.0, 3.0, 4.0}, 
         { 2.0, 4.0, -2.0, 7.0, 10.0}, 
         {-1.0, -2.0, 1.0, -4.0, -6.0 } }; 
      double [][] E124 = {                     // Ex.4 p.21
         { 1, 1, 5, 3}, 
         { 0, 1, 3, -1}, 
         { 1, 2, 8, 3} }; 
      
      double [][] P126a = {                     // Prob.6.a p.24
         { 3, 6, -3, 6}, 
         { -2, -4, -3, -1}, 
         { 3, 6, -2, 10} }; 
      
      double [][] P126c = {                     // Prob.6.c p.24
         { 1, 2, -1, 3}, 
         { 2, 4, -2, 6}, 
         { 3, 6, 2, -1} }; 
      
      double [][] P126e = {                     // Prob.6.e p.24
         { 0, 1, 2, 5}, 
         { 1, 2, 5, 13}, 
         { 1, 0, 2, 4} }; 
      
      double [][] E711 = {                     // Example 1 on p.366
         { 1, 2, 3, 2, -1}, 
         { -1, -2, -2, 1, 2}, 
         { 2, 4, 8, 12, 4} }; 
      
      double [][] E712 = {                     // Example 1 on p.367
         { 1, 2, 3, 2, -1}, 
         { -1, -2, -2, 1, 2}, 
         { 2, 4, 8, 12, 4} }; 
      
      double [][] Z = {                     
         { 1,   -5,  2,  -3,  -10}, 
         {  2, -4, 12, 2, 4}, 
         { 1, -2, 6,  1, 2}, 
         { -1,  2,  -2, 6, 0}  
      }; 
      
      double [][] T = E121; 
      
      Matrix.gaussJordan(T); 
      MatrixHW.gaussJordan(T);
      
      //MatrixHW.gaussianElimination(T); 
      
   }  
   
   
   
   
   
}