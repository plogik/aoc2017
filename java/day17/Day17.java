import java.util.*;

public class Day17 {
    public static void main(String[] args) {
        int input = 380;
        System.out.println(solvePt1(input));
        System.out.println(solvePt2(input));
    }

    static int solvePt1(int step) {
        int currPos = 0;
        List<Integer> list = new ArrayList<Integer>();
        list.add(0);
        for(int i = 0; i < 2017; i++) {
            currPos = (currPos + step) % list.size() + 1;
            if(currPos > list.size() - 1) {
                list.add(i + 1);
            }
            else {
                list.add(currPos, i + 1);
            }
        }
        return list.get(currPos + 1);
    }

    static int solvePt2(int step) {
        int currPos = 0;
        int answer = 0;
        for(int i = 1; i < 50000000; i++) {
            currPos = ((currPos + step) % i) + 1;
            if(currPos == 1) {
                answer = i;
            }
        }
        return answer;
    }
}
