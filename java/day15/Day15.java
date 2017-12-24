public class Day15 {
    public static void main(String[] args) {
        //System.out.println(solvePt1(65, 8921));
        long start = System.currentTimeMillis();
        System.out.println(solvePt1(512, 191));
        long stopA = System.currentTimeMillis();
        System.out.println(solvePt2(512, 191));
        long stopB = System.currentTimeMillis();
        System.out.printf("Pt 1: %d, pt 2: %d\n",
                stopA - start, stopB - stopA);
    }

     static int solvePt1(long a, long b) {
         int count = 0;
         for(int i = 0; i < 40000000; i++) {
            a = a * 16807 % 2147483647;
            b = b * 48271 % 2147483647;
            if(equals(a, b))
                count++;
        }
        return count;
    }

    static int solvePt2(long a, long b) {
        int count = 0;
        for(int i = 0; i < 5000000; i++) {
            do {
                a = a * 16807 % 2147483647;
            } while(a % 4 != 0);
            do {
                b = b * 48271 % 2147483647;
            } while(b % 8 != 0);
            if(equals(a, b))
                count++;
        }
        return count;
    }

    static boolean equals(long a, long b) {
        String strA = Integer.toBinaryString((int)a);
        String strB = Integer.toBinaryString((int)b);
        if(strA.length() < 17 || strB.length() < 17)
            return false;
        return strA.substring(strA.length() - 16, strA.length())
            .equals(strB.substring(strB.length() - 16, strB.length()));
    }
}

