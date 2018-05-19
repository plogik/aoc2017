import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.*;

public class Day19 {
    public static void main(String[] args) {
        try {
            List<char[]> map = Files
                .lines(Paths.get("../../inputs/day19.txt"))
                .map(x -> x.toCharArray())
                .collect(Collectors.toList());

            System.out.println(solvePt1(map));
            System.out.println(solvePt2(map));
        }
        catch(Exception e) {
            System.out.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

    static String solvePt1(List<char[]> map) {
        Stepper stepper = new Stepper(map);
        while(stepper.step());
        return stepper.getTrail();
    }

    static int solvePt2(List<char[]> map) {
        Stepper stepper = new Stepper(map);
        while(stepper.step());
        return stepper.getStepCount();
    }

    static class Stepper {
        enum Dir { U, D, L, R };
        int x, y;
        int stepCount;
        List<char[]> map;
        StringBuffer trail = new StringBuffer();
        Dir dir = Dir.D;

        Stepper(List<char[]> map) {
            this.map = map;
            goToStartPosition();
        }

        void goToStartPosition() {
            while(map.get(0)[x] == ' ')
                x++;
        }

        boolean step() {
            stepCount++;
            if(map.get(y)[x] == '+') {
                switch(dir) {
                    case D:
                    case U:
                        dir = x > 0 && 
                            (map.get(y)[x - 1] == '-' || 
                                Character.isAlphabetic(map.get(y)[x - 1])) ? 
                            Dir.L : Dir.R;
                        break;
                    case L:
                    case R:
                        dir = y > 0 &&
                            (map.get(y - 1)[x] == '|' ||
                                Character.isAlphabetic(map.get(y - 1)[x])) ? 
                        Dir.U : Dir.D;
                        break;
                }
            }
            x = dir == Dir.L ? x - 1 : 
                dir == Dir.R ? x + 1 : x;
            y = dir == Dir.U ? y - 1 :
                dir == Dir.D ? y + 1 : y;

            //System.out.println(String.format("%S: {%d, %d}", dir.toString(), x, y));

            char curr = map.get(y)[x];

            if(curr >= 'A' && curr <= 'Z') {
                trail.append(curr);
            }
            return curr != ' ';
        }

        String getTrail() {
            return trail.toString();
        }

        int getStepCount() {
            return stepCount;
        }
    }
}
