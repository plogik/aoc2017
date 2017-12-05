import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day01 {
    public static void main(String[] args) {
        try {
            String data =
                new String(
                        Files.readAllBytes(Paths.get("../../inputs/day01.txt"))
                    ).trim();
            System.out.println("Pt1:" + solvePt1(data));
            System.out.println("Pt2:" + solvePt2(data));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int solvePt1(String str) {
        char[] data = str.toCharArray();
        return IntStream.range(0, data.length)
                .filter(i -> data[i] == data[(i + 1) % data.length])
                .map(i -> data[i] - '0')
                .sum();
    }

    static int solvePt2(String str) {
        char[] data = str.toCharArray();
        return IntStream.range(0, data.length)
                .filter(i -> data[i] == data[(i + data.length / 2) % data.length])
                .map(i -> data[i] - '0')
                .sum();
    }
}
