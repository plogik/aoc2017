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
            System.out.println(solvePt1(layers));
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
        while(currPos < maxIndex) {
            currPos++;
            // if hit, calc cost
            Layer l = findLayerAtIndex(layers, currPos);
            if(l != null && l.currPos == 0) {
                cost += (currPos * l.depth);
            }
            layers.stream().forEach(Layer::step);
        }
        return cost;
    }

    static int solvePt2(List<Layer> input) {
        int maxIndex = Collections.max(input, Comparator.comparing(l -> l.index)).index;
        int[] layers = new int[maxIndex + 1];
        for(Layer l : input) {
            layers[l.index] = l.depth;
        }

        int delay = 0;
        boolean caught;
        do {
            caught = false;
            for(int i = 0; i < layers.length; i++) {
                if(layers[i] != 0 && (i + delay) % ((layers[i] - 1) * 2) == 0) {
                    caught = true;
                    break;
                }
            }
            delay++;
        } while(caught);
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
