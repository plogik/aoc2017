import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 {
    public static void main(String[] args) throws IOException {
        List<Particle> particles = Files
                .lines(Paths.get("../../inputs/day20.txt"))
                .map(x -> parseLine(x))
                .collect(Collectors.toList());
        System.out.println(part1(particles));
        System.out.println(part2(particles));
    }

    static long part1(List<Particle> particles) {
        boolean there = false;
        long[] history = new long[1000];
        for(int i = 0; !there; i++) {

            for(Particle p : particles) {
                p.step();
            }
            history[i % history.length] = closest(particles);

            // If nothings changed for history.length iterations, where officially there!
            // (unshamefully using brute force to run my errands)
            there = true;
            for(int n = 1; n < history.length; n++) {
                if(history[0] != history[n]) {
                    there = false;
                    break;
                }
            }
        }
        return history[0];
    }


    static long part2(List<Particle> particles) {
        boolean there = false;
        int[] history = new int[1000];

        for(int i = 0; !there; i++) {

            for(Particle p : particles) {
                p.step();
            }

            List<Particle> duplicates = particles.stream()
                    .collect(Collectors.groupingBy(p -> p.p))
                    .entrySet()
                    .stream()
                    .filter(ps -> ps.getValue().size() > 1)
                    .map(ps -> ps.getValue().get(0))
                    .collect(Collectors.toList());

            particles = particles
                    .stream()
                    .filter(p -> !duplicates.contains(p))
                    .collect(Collectors.toList());

            history[i % history.length] = particles.size();

            there = true;
            for(int n = 1; n < history.length; n++) {
                if(history[0] != history[n]) {
                    there = false;
                    break;
                }
            }
        }
        return history[0];
    }

    static int closest(List<Particle> particles) {
        int closest = 0;
        for(long distance = particles.get(closest).getDistance(), i = 1; i < particles.size(); i++) {
            if(particles.get((int)i).getDistance() < particles.get(closest).getDistance()) {
                closest = (int)i;
            }
        }
        return closest;
    }

    static Particle parseLine(String line)  {
        return matches(line, "(?<=<)[-0-9,]*(?=>)")
                .map(s -> {
                    String[] parts = s.split(",");
                    ThreeTuple tt =  new ThreeTuple();
                    tt.x = Long.parseLong(parts[0]);
                    tt.y = Long.parseLong(parts[1]);
                    tt.z = Long.parseLong(parts[2]);
                    return tt;
                })
                .collect(Collector.of(
                        () -> new ArrayList<ThreeTuple>(),
                        (l, tt) -> l.add(tt),
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        },
                        l -> new Particle(l.get(0), l.get(1), l.get(2))));
    }

    static class ThreeTuple {
        public long x, y, z;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            return o != null &&
                    getClass() == o.getClass() &&
                    ((ThreeTuple)o).x == x &&
                    ((ThreeTuple)o).y == y &&
                    ((ThreeTuple)o).z == z;
        }

        @Override
        public int hashCode() {
            int result = (int) (x ^ (x >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            result = 31 * result + (int) (z ^ (z >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "ThreeTuple{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    static class Particle {
        public ThreeTuple p;
        public ThreeTuple v;
        public ThreeTuple a;
        private long prevDistance;
        private long distance;

        public Particle(ThreeTuple p, ThreeTuple v, ThreeTuple a) {
            this.p = p;
            this.v = v;
            this.a = a;
            distance = Math.abs(p.x) + Math.abs(p.y) + Math.abs(p.z);
        }

        void step() {
            v.x += a.x;
            v.y += a.y;
            v.z += a.z;

            p.x += v.x;
            p.y += v.y;
            p.z += v.z;

            prevDistance = distance;
            distance = Math.abs(p.x) + Math.abs(p.y) + Math.abs(p.z);
        }

        long getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Particle particle = (Particle) o;

            /*if (p != null ? !p.equals(particle.p) : particle.p != null) return false;
            if (v != null ? !v.equals(particle.v) : particle.v != null) return false;
            return a != null ? a.equals(particle.a) : particle.a == null;*/
            return p != null ? p.equals(particle.p) : particle.p == null;
        }

        @Override
        public int hashCode() {
            int result = p != null ? p.hashCode() : 0;
            //result = 31 * result + (v != null ? v.hashCode() : 0);
            //result = 31 * result + (a != null ? a.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Particle{" +
                    "p=" + p +
                    ", v=" + v +
                    ", a=" + a +
                    '}';
        }
    }

    static Stream<String> matches(String haystack, String needlePtrn) {
        Pattern ptrn = Pattern.compile(needlePtrn);
        Matcher m = ptrn.matcher(haystack);
        List<String> matches = new ArrayList<>();
        while(m.find()) {
            matches.add(m.group());
        }
        return matches.stream();
    }

}
