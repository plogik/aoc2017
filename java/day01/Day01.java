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
            System.out.println("Pt1:" + solve(data, 1));
            System.out.println("Pt2:" + solve(data, data.length() / 2));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int solve(String str, int compareOffset) {
        char[] data = str.toCharArray();
        return IntStream.range(0, data.length)
                .filter(i -> data[i] == data[(i + compareOffset) % data.length])
                .map(i -> data[i] - '0')
                .sum();
    }
}
