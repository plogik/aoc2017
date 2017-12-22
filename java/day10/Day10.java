//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.stream.*;
import java.util.Arrays;

class Day10 {
    public static void main(String[] args) {
        String input = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70";
        // 37230
        // 70b856a24d586194331398c7fcfa0aaf
        System.out.println(solvePt1(input));
        System.out.println(solvePt2(input));
    }

    static int solvePt1(String input) {
        int[] list = IntStream
                        .range(0, 256)
                        .toArray();
        int[] lengths = 
            Stream.of(input.split(","))
            .mapToInt(x -> Integer.parseInt(x.trim()))
            .toArray();
        int skipSize = 0;
        int currPos = 0;
        for(int length : lengths) {
            reverse(list, currPos, length);
            currPos = (currPos + skipSize + length) % list.length;
            skipSize++;
        }
        return list[0] * list[1];
    }

    static String solvePt2(String input) {
        int[] list = IntStream
                        .range(0, 256)
                        .toArray();

        StringBuffer buf = new StringBuffer();
        for(char c : input.toCharArray())
            buf.append(Integer.toString((int)c)).append(',');
        buf.append("17,31,73,47,23");

        int[] lengths = Stream.of(buf.toString()
            .split(","))
            .mapToInt(x -> Integer.parseInt(x.trim()))
            .toArray();

        int skipSize = 0;
        int currPos = 0;
        for(int i = 0; i < 64; i++) {
            for(int length : lengths) {
                reverse(list, currPos, length);
                currPos = (currPos + skipSize + length) % list.length;
                skipSize++;
            }
        }
        buf = new StringBuffer();

        for(int i = 0; i < 16; i++) {
            buf.append(String.format("%02x", 
                Arrays.stream(list)
                    .skip(i * 16)
                    .limit(16)
                    .reduce(0, (p, e) -> p ^ e)));
        }
        return buf.toString();
    }

    static void reverse(int[] list, int start, int length)
    {
        int p1 = start % list.length;
        int p2 = (start + length - 1) % list.length;
        int swaps = length / 2;
        while(swaps > 0) {
            int tmp = list[p1];
            list[p1] = list[p2];
            list[p2] = tmp;

            p1 = (p1 + 1) % list.length;
            p2 = p2 > 0 ? p2 - 1 : list.length - 1;

            swaps--;
        }
    }

    static void printList(int[] list)
    {
        for(int e : list)
        {
            System.out.printf("%d, ", e);
        }
        System.out.println();
    }
}
