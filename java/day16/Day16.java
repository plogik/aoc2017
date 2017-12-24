import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.*;

public class Day16 {
    public static void main(String[] args) {
        try {
            String data =
                new String(
                        Files.readAllBytes(Paths.get("../../inputs/day16.txt"))
                    ).trim();
            //System.out.println("Pt1:" + solvePt1("s1,x3/4,pe/b"));
            System.out.println("Pt1:" + solvePt1(data));
            System.out.println("Pt2:" + solvePt2(data));

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static String solvePt1(String input) {
        char[] progs = "abcdefghijklmnop".toCharArray();
        Stream.of(input.split(","))
            .forEach(cmd -> {
                    if(cmd.startsWith("s"))
                        spin(progs, Integer.parseInt(cmd.substring(1)));
                    else if(cmd.startsWith("x"))
                        swap(progs,
                            Integer.parseInt(cmd.substring(1, cmd.indexOf("/"))),
                            Integer.parseInt(cmd.substring(cmd.indexOf("/")+1)));
                    else if(cmd.startsWith("p"))
                        swap(progs,
                            cmd.substring(1, cmd.indexOf("/")),
                            cmd.substring(cmd.indexOf("/")+1));
                    else
                        throw new IllegalArgumentException("Unknown command:" + cmd);
            });
        return new String(progs);
    }

    static String solvePt2(String input) {
        char[] progs = "abcdefghijklmnop".toCharArray();
        String[] cmds = input.split(",");


        // Scratch that, too many iterations
        // Found duplicate at each 60th iteratarion
        // 1000000000 % 60 = 40, so 60 + 40 iterations it is!
        //List<String> history = new ArrayList<String>();
        for(int i = 0; i < 100/*1000000000*/; i++) {
            Stream.of(cmds)
                .forEach(cmd -> {
                        if(cmd.startsWith("s"))
                            spin(progs, Integer.parseInt(cmd.substring(1)));
                        else if(cmd.startsWith("x"))
                            swap(progs,
                                Integer.parseInt(cmd.substring(1, cmd.indexOf("/"))),
                                Integer.parseInt(cmd.substring(cmd.indexOf("/")+1)));
                        else if(cmd.startsWith("p"))
                            swap(progs,
                                cmd.substring(1, cmd.indexOf("/")),
                                cmd.substring(cmd.indexOf("/")+1));
                        else
                            throw new IllegalArgumentException("Unknown command:" + cmd);
                });
            /*if(exists(history, progs)) {
                System.out.println("Duplicate at " + i);
                history = new ArrayList<String>();
            }
            history.add(new String(progs.clone()));*/
        }
        return new String(progs);
    }

    static boolean exists(List<String> haystack, char[] needle) {
        String strNeedle = new String(needle);
        for(String str : haystack) {
            if(strNeedle.equals(str))
                return true;
        }
        return false;
    }

    static void spin(char[] progs, int num) {
        char[] a = new char[progs.length - num];
        char[] b = new char[num];
        System.arraycopy(progs, 0, a, 0, a.length);
        System.arraycopy(progs, a.length, b, 0, num);
        System.arraycopy(b, 0, progs, 0, b.length);
        System.arraycopy(a, 0, progs, num, a.length);
    }

    static void swap(char[] progs, int a, int b) {
        char tmp = progs[a];
        progs[a] = progs[b];
        progs[b] = tmp;
    }

    static void swap(char[] progs, String a, String b) {
        int aPos = -1;
        int bPos = -1;
        char ca = a.toCharArray()[0];
        char cb = b.toCharArray()[0];
        for(int i = 0; i < progs.length; i++) {
            if(progs[i] == ca)
                aPos = i;
            if(progs[i] == cb)
                bPos = i;
        }
        swap(progs, aPos, bPos);
    }

}
