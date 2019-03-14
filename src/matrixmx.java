class MatrixMx
{
    public MatrixMx()
    {

    }

    //classic 3-loop

    public int[][] classic(int[][] A, int[][] B)
    {
        int dim = A.length;
        int sum = 0;
        int multiply[][] = new int[dim][dim];
        for (int c = 0; c < dim; c++)
        {
            for (int d = 0; d < dim; d++)
            {
                for (int k = 0; k < dim; k++)
                {
                    sum = sum + A[c][k]*B[k][d];
                }
                multiply[c][d] = sum;
                sum = 0;
            }
        }
        return multiply;
    }

    //divide and conquer

    public int[][] divconquer(int[][] A, int[][] B)
    {
        int dim = A.length;
        return dcrecurse(A, B, 0,0,0,0, dim);
    }

    private int[][] dcrecurse(int[][] A, int[][] B, int rowA, int colA, int rowB, int colB, int dim)
    {
        int[][] multiply = new int[dim][dim];
        if(dim==1)
            multiply[0][0] = A[rowA][colA] * B[rowB][colB];
        //else if(dim<=256)
        //    return classic(A,B);
        else
        {
            int newdims = dim/2;
            add(multiply,
                dcrecurse(A,B,rowA,colA,rowB,colB,newdims),
                dcrecurse(A,B,rowA,colA+newdims,rowB+newdims,colB,newdims),
                0,0,newdims); //m[0][0] = 00*00 + 01*10

            add(multiply,
                dcrecurse(A,B,rowA,colA,rowB,colB+newdims,newdims),
                dcrecurse(A,B,rowA,colA+newdims,rowB+newdims,colB+newdims,newdims),
                0,newdims,newdims); //m[0][1] = 00*01 + 01*11

            add(multiply,
                dcrecurse(A,B,rowA+newdims,colA,rowB,colB,newdims),
                dcrecurse(A,B,rowA+newdims,colA+newdims,rowB+newdims,colB,newdims),
                newdims,0,newdims); //m[1][0] = 10*00 + 11*10

            add(multiply,
                dcrecurse(A,B,rowA+newdims,colA,rowB,colB+newdims,newdims),
                dcrecurse(A,B,rowA+newdims,colA+newdims,rowB+newdims,colB+newdims,newdims),
                newdims,newdims,newdims); //m[1][1] = 10*01 + 11*11
        }
        return multiply;
    }


    private void add(int[][] added, int[][] A, int[][] B, int row, int col, int dim)
    {
        for(int r=0; r<dim; r++)
        {
            for(int c=0; c<dim; c++)
            {
                added[r+row][c+col] = A[r][c] + B[r][c];
            }
        }
    }

    //strassens

    public int[][] strassens(int[][] A, int[][] B)
    {
        int dim = A.length;
        return strc(A,B,dim);
    }

    private int[][] strc(int[][] A, int[][] B, int dim)
    {
        int[][] multiply = new int[dim][dim];
        if(dim==1)
            multiply[0][0] = A[0][0] * B[0][0];
        else if(dim<=256)
            return classic(A,B);
        else
        {
            int newdims = dim/2;

            int[][] A00 = divide(A,0,0);
            int[][] A01 = divide(A,0,newdims);
            int[][] A10 = divide(A,newdims,0);
            int[][] A11 = divide(A,newdims,newdims);

            int[][] B00 = divide(B,0,0);
            int[][] B01 = divide(B,0,newdims);
            int[][] B10 = divide(B,newdims,0);
            int[][] B11 = divide(B,newdims,newdims);

            //<editor-fold desc="BAD IMPLEMENTATION">
            /*int[][] ab0000 = strc(A00,B00,newdims);
            int[][] ab0001 = strc(A00,B01,newdims);
            int[][] ab0011 = strc(A00,B11,newdims);
            int[][] ab0111 = strc(A01,B11,newdims);
            int[][] ab1111 = strc(A11,B11,newdims);
            int[][] ab1110 = strc(A11,B10,newdims);
            int[][] ab1100 = strc(A11,B00,newdims);
            int[][] ab1000 = strc(A10,B00,newdims);

            int[][] m1 = sub(ab0001,ab0011); //00*01 -00*11
            int[][] m2 = add(ab0011,ab0111); //00*11 +01*11
            int[][] m3 = add(ab1000,ab1100); //10*00 +11*00
            int[][] m4 = sub(ab1110,ab1100); //11*10 -11*00
            //00*00 +00*11 +11*00 +11*11
            int[][] m5 = add(   add(   add(ab0000,ab0011), ab1100), ab1111);   //00*00 +00*11 +11*00 +11*11
            //01*10 +01*11 -11*10 -11*11
            int[][] m6 = sub(   add(   sub(ab0111,ab1110), strc(A01,B10,newdims)), ab1111);
            //00*00 +00*01 -10*00 -10*01
            int[][] m7 = sub(   add(   sub(ab0001,ab1000), ab0000), strc(A10,B01,newdims));
            */
            //</editor-fold>
            //<editor-fold desc="operation instructions">
            /*
            s1 = B01-B11
            s2 = A00+A01
            s3 = A10+A11
            s4 = B10-B00
            s5 = A00+A11
            s6 = B00+B11
            s7 = A01-A11
            s8 = B10+B11
            s9 = A00-A10
            s10 = B00+B01

            m1 = A00*S1
            m2 = S2*B11
            m3 = S3*B00
            m4 = A11*S4
            m5 = S5*S6
            m6 = S7*S8
            m7 = S9*S10
             */
            //</editor-fold>

            int[][] m1 = strc(A00,sub(B01,B11),newdims);
            int[][] m2 = strc(add(A00,A01),B11,newdims);
            int[][] m3 = strc(add(A10,A11),B00,newdims);
            int[][] m4 = strc(A11,sub(B10,B00),newdims);
            int[][] m5 = strc(add(A00,A11),add(B00,B11),newdims);
            int[][] m6 = strc(sub(A01,A11),add(B10,B11),newdims);
            int[][] m7 = strc(sub(A00,A10),add(B00,B01),newdims);

            add(multiply,m5,add(m6,sub(m4,m2)),0,0,newdims); //00=5+4-2+6
            add(multiply,m1,m2,0,newdims,newdims); //01=1+2
            add(multiply,m3,m4,newdims,0,newdims); //10=3+4
            add(multiply,m5,sub(sub(m1,m3),m7),newdims,newdims,newdims); //11=5+1-3-7
        }
        return multiply;
    }

    private int[][] divide(int[][] M, int row, int col)
    {
        int dims = M.length/2;
        int[][] div = new int[dims][dims];
        for(int i = 0;i<dims;i++)
        {
            for(int j = 0;j<dims; j++)
            {
                div[i][j] = M[i+row][j+col];
            }
        }
        return div;
    }

    private int[][] add(int[][] A, int[][] B)
    {
        int dim = A.length;
        int[][] added = new int[dim][dim];
        for(int r=0; r<dim; r++)
        {
            for(int c=0; c<dim; c++)
            {
                added[r][c] = A[r][c] + B[r][c];
            }
        }
        return added;
    }

    private int[][] sub(int[][] A, int[][] B)
    {
        int dim = A.length;
        int[][] subbed = new int[dim][dim];
        for(int r=0; r<dim; r++)
        {
            for(int c=0; c<dim; c++)
            {
                subbed[r][c] = A[r][c] - B[r][c];
            }
        }
        return subbed;
    }


    public void print(int[][] m)
    {
        for(int[] row:m)
        {
            System.out.print("|\t");
            for(int col:row)
                System.out.print(col+"\t");
            System.out.println("|");
        }
        System.out.println();
    }

    public int[][] resize(int[][] m, int newdims, int filler)
    {
        int dim = m.length;
        int[][] rem = new int[newdims][newdims];
        for(int r=0;r<newdims;r++)
        {
            for(int c=0;c<newdims;c++)
            {
                if(r<dim && c<dim)
                    rem[r][c] = m[r][c];
                else
                    rem[r][c] = filler;
            }
        }
        return rem;
    }
}