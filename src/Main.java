public class Main
{
    static MatrixMx mxtester = new MatrixMx();

    public static void main(String[] args)
    {
        int[][] test = {{1,2},{3,4}};
        int[][] test0 = {{0}};
        data(test0, 0);
    }

    public static void data(int[][] m, int filler)
    {
        int dims = 2;
        int[][] resz = mxtester.resize(m,dims,filler);
        for(int i=0;i<16;i++)
        {
            timeCounter(resz);
            dims*=2;
            resz = mxtester.resize(m,dims,filler);
        }
    }

    public static void timeCounter(int[][] m)
    {
        long startTime, runTime, avg = 0;
        for(int i=0;i<2;i++)
        {
            startTime = System.currentTimeMillis();

            //testClassic(m);
            testDC(m);
            //testStrassens(m);

            runTime = System.currentTimeMillis() - startTime;
            avg+=runTime;
        }
        avg/=2;
        int dim = m.length;
        System.out.println(dim + "*" + dim + " runtime:" + avg);
    }

    public static void testClassic(int[][] m)
    {
        //System.out.println("classic");
        int[][] classic = mxtester.classic(m,m);
        //mxtester.print(classic);
    }

    public static void testDC(int[][] m)
    {
        //System.out.println("divide & conquer");
        int[][] DC = mxtester.divconquer(m,m);
        //mxtester.print(DC);
    }

    public static void testStrassens(int[][] m)
    {
        //System.out.println("Strassen's");
        int[][] str = mxtester.strassens(m,m);
        //mxtester.print(str);
    }
}
