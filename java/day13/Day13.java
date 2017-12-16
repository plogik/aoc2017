import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.*;

public class Day13 {
    public static void main(String[] args) {
        try {
            List<Layer> layers = Files
                .lines(Paths.get("../../inputs/day13.txt"))
                .map(x -> x.split(":"))
                .map(strs -> new Layer(
                            Integer.parseInt(strs[0].trim()),
                            Integer.parseInt(strs[1].trim())))
                .collect(Collectors.toList());
            /*for(Layer l : layers) {
                System.out.println("" + l.index + ":" + l.depth);
            }*/
            //System.out.println(solvePt1(layers));
            System.out.println(solvePt2(layers));
        }
        catch(Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    static int solvePt1(List<Layer> layers) {
        int maxIndex = Collections.max(layers, Comparator.comparing(l -> l.index)).index;
        int currPos = -1;
        int cost = 0;
        //Scanner s = new Scanner(System.in);
        while(currPos < maxIndex) {
            //debugPrint(layers, currPos);
            //s.next();

            currPos++;
            // if hit, calc cost
            Layer l = findLayerAtIndex(layers, currPos);
            if(l != null && l.currPos == 0) {
                cost += (currPos * l.depth);
                //System.out.println("Caught at " + currPos + " cost:" + cost);
            }
            //debugPrint(layers, currPos);
            //s.next();
            layers.stream().forEach(Layer::step);
        }
        return cost;
    }

    static int solvePt2(List<Layer> layers) {
        int maxIndex = Collections.max(layers, Comparator.comparing(l -> l.index)).index;
        int delay = 0;
        int cost = 0;

        do {
            layers.stream().forEach(Layer::reset);
            int currPos = -1;
            cost = 0;
            for(int i = 0; i < delay; i++)
                layers.stream().forEach(Layer::step);


            while(currPos < maxIndex) {
                currPos++;
                // if hit, calc cost
                Layer l = findLayerAtIndex(layers, currPos);
                if(l != null && l.currPos == 0) {
                    cost += (currPos * l.depth);
                    cost++;
                }
                layers.stream().forEach(Layer::step);
            }
            delay++;
        } while(cost > 0);
        return delay - 1;
    }

    static class Layer {
        public Layer(int index, int depth) {
            this.index = index;
            this.depth = depth;
        }
        public boolean goingUp;
        public int currPos;
        public int index;
        public int depth;
        public void step() {
            if(currPos >= (depth - 1))
                goingUp = true;
            if(currPos == 0)
                goingUp = false;
            if(goingUp)
                currPos--;
            else
                currPos++;
        }
        public void reset() {
            goingUp = false;
            currPos = 0;
        }
    }

    static Layer findLayerAtIndex(List<Layer> layers, int idx) {
        Layer l = null;
        for(Layer x : layers) {
            if(x.index == idx) {
                l = x;
                break;
            }
        }
        return l;
    }

    static void debugPrint(List<Layer> layers, int currPos) {
        int maxIndex = Collections
            .max(layers, Comparator.comparing(l -> l.index))
            .index;
        IntStream.range(0, maxIndex + 1)
            .forEach(n -> System.out.printf(" %d  ", n));
        System.out.println();
        int maxDepth = Collections
            .max(layers, Comparator.comparing(l -> l.depth))
            .depth;
        for(int i = 0; i < maxDepth; i++) {
            for(int n = 0; n <= maxIndex; n++) {
                Layer l = findLayerAtIndex(layers, n);
                if(i == 0 && n == currPos) {
                    System.out.printf("(%c)",
                            l != null && l.currPos == i ? '0' : ' ');
                }
                else {
                    if(l != null && i < l.depth)
                        System.out.printf("[%c]", l.currPos == i ? '0' : ' ');
                    else
                        System.out.print("...");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
