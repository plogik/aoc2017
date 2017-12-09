import java.nio.file.Files;
import java.nio.file.Paths;
import static java.lang.System.out;

public class Day09 {
    public static void main(String[] args) {
        try {
            String data =
                new String(
                        Files.readAllBytes(Paths.get("../../inputs/day09.txt"))
                    ).trim();
            long start = System.nanoTime();
            int[] result = Solve(data);
            long duration = System.nanoTime() - start;
            out.format("Total:%d, garbage:%d, duration ns:%d\n",
                    result[0], result[1], duration );
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int[] Solve(String input) {
        char[] data = input.toCharArray();
        boolean inGarbage = false;
        int nestLevel = 0;
        int total = 0;
        int totalGarbage = 0;
        for(int i = 0; i < input.length(); i++) {
            switch(data[i]) {
                    case '{':
                        if(!inGarbage)
                            nestLevel++;
                        else
                            totalGarbage++;
                        break;
                    case '}':
                        if(!inGarbage)
                        {
                            total += nestLevel;
                            nestLevel--;
                        }
                        else
                            totalGarbage++;
                        break;
                    case '!':
                        i++;
                        break;
                    case '<':
                        if(inGarbage)
                            totalGarbage++;
                        inGarbage = true;
                        break;
                    case '>':
                        inGarbage = false;
                        break;
                    default:
                        if(inGarbage)
                            totalGarbage++;
                        break;
            }
        }
        return new int[] { total, totalGarbage };
    }
}
