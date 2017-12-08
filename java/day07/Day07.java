import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.regex.*;
import java.util.*;

public class Day07 {
    public static void main(String[] args) {
        try {
            String data =
                new String(
                        Files.readAllBytes(Paths.get("../../inputs/day07_ex1.txt"))
                    ).trim();
            System.out.println("Pt1:" + solvePt1(data));
            //System.out.println("Pt2:" + solve(data, data.length() / 2));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static String solvePt1(String input) {
        List<String> names = findAll(input);
        System.out.println("Count:" + names.size());
        for(String s : names)
            System.out.println(s);
        Map<String, Integer> groups =
            names.stream()
            .collect(Collectors.groupingBy(n -> n, Collectors.mapping((String s) -> s, Collectors.toCollection(s))));
        return null;
    }

    static List<String> findAll(String input) {
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern
            .compile("[a-zA-Z]+(?=[^$])")
            .matcher(input);
        while(m.find()) {
            matches.add(m.group());
        }
        return matches;
    }

    /*static class Program {
        String name;
        int weight;
    }*/
}
