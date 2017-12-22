import java.util.stream.*;
import java.util.*;

public class Day14 {
	public static void main(String[] args) {
		//String input = "flqrgnkx";
		String input = "ljoxqyyw";
		System.out.println(solvePt1(input));
		System.out.println(solvePt2(input));
	}

	static int solvePt1(String input) {
		return (int)IntStream.range(0, 128)
					.mapToObj(i -> 
						toBinary(knotHash(String.format("%s-%d", input, i))))
					.flatMapToInt(x -> x.chars())
					.filter(c -> c == '1')
					.count();
	}

	static int solvePt2(String input) {
		List<char[]> map  = 
			IntStream.range(0, 128)
				.mapToObj(i -> 
					toBinary(knotHash(String.format("%s-%d", input, i))).toCharArray())
				.collect(Collectors.toList());

		int regionCount = 0;
		List<Node> visited = new ArrayList<Node>();
		for(int row = 0; row < 128; row++) {
			for(int col = 0; col < 128; col++) {
				if(map.get(row)[col] == '1' &&
					!contains(visited, row, col)) {
					Node node = new Node(col, row);
					visited.add(node);
					addNeighbours(map, visited, node);
					regionCount++;
				}
			}
		}
		return regionCount;
	}

	static void addNeighbours(List<char[]> world, List<Node> visited, Node root) {
		if(root.row > 0 && 
			world.get(root.row - 1)[root.col] == '1' &&
			!contains(visited, root.row - 1, root.col)) {
			Node node = new Node(root.col, root.row - 1);
			visited.add(node);
			addNeighbours(world, visited, node);
		}
		if(root.row < 127 &&
			world.get(root.row + 1)[root.col] == '1' &&
			!contains(visited, root.row + 1, root.col)) {
			Node node = new Node(root.col, root.row + 1);
			visited.add(node);
			addNeighbours(world, visited, node);
		}
		if(root.col > 0 &&
			world.get(root.row)[root.col - 1] == '1' &&
			!contains(visited, root.row, root.col - 1)) {
			Node node = new Node(root.col - 1, root.row);
			visited.add(node);
			addNeighbours(world, visited, node);
		}
		if(root.col < 127 &&
			world.get(root.row)[root.col + 1] == '1' &&
			!contains(visited, root.row, root.col + 1)) {
			Node node = new Node(root.col + 1, root.row);
			visited.add(node);
			addNeighbours(world, visited, node);
		}
	}

	static boolean hasNeighbours(List<Node> haystack, int row, int col) {
		if(row > 0 && contains(haystack, row - 1, col))
			return true;
		if(col < 127 && contains(haystack, row, col - 1))
			return true;
		return false;
	}

	static boolean contains(List<Node> haystack, int row, int col) {
		for(Node node : haystack)
			if(node.col == col && node.row == row)
				return true;
		return false;
	}

	static class Node {
		public int col;
		public int row;

		public Node(int col, int row) {
			this.col = col;
			this.row = row;
		}

		public int hashCode() {
			int result = 17;
			result = 37*result + 'c' + col;
			result = 37*result + 'r' + col;
			return result;
		}

		public boolean equals(Object other) {
			return other instanceof Node &&
				((Node)other).col == col &&
				((Node)other).row == row;
		}
	}


	static String knotHash(String input) {
        int[] list = IntStream
                        .range(0, 256)
                        .toArray();

        StringBuffer buf = new StringBuffer();
        for(char c : input.toCharArray())
            buf.append(Integer.toString((int)c)).append(',');
        buf.append("17,31,73,47,23");

        int[] lengths = Stream.of(buf.toString()
            .split(","))
            .mapToInt(x -> Integer.parseInt(x.trim()))
            .toArray();

        int skipSize = 0;
        int currPos = 0;
        for(int i = 0; i < 64; i++) {
            for(int length : lengths) {
                reverse(list, currPos, length);
                currPos = (currPos + skipSize + length) % list.length;
                skipSize++;
            }
        }
        buf = new StringBuffer();

        for(int i = 0; i < 16; i++) {
            buf.append(String.format("%02x", 
                Arrays.stream(list)
                    .skip(i * 16)
                    .limit(16)
                    .reduce(0, (p, e) -> p ^ e)));
        }
        return buf.toString();
	}

	static String toBinary(String input) {
		StringBuffer buf = new StringBuffer();
		input.chars()
			.map(c -> c >= '0' && c <= '9' ? (int)c - '0' : (int)c - 'a' + 10)
			.mapToObj(i -> toBinaryString(i))
			.forEach(s -> buf.append(s));
		return buf.toString();
	}

	static String toBinaryString(int n) {
		//return "" + n;
		switch(n) {
			case 0:
				return "0000";
			case 1:
				return "0001";
			case 2:
				return "0010";
			case 3:
				return "0011";
			case 4:
				return "0100";
			case 5:
				return "0101";
			case 6:
				return "0110";
			case 7:
				return "0111";
			case 8:
				return "1000";
			case 9:
				return "1001";
			case 10:
				return "1010";
			case 11:
				return "1011";
			case 12:
				return "1100";
			case 13:
				return "1101";
			case 14:
				return "1110";
			case 15:
				return "1111";
		}
		throw new IllegalArgumentException("" + n + " cannot be converted to bin");
	}

    static void reverse(int[] list, int start, int length)
    {
        int p1 = start % list.length;
        int p2 = (start + length - 1) % list.length;
        int swaps = length / 2;
        while(swaps > 0) {
            int tmp = list[p1];
            list[p1] = list[p2];
            list[p2] = tmp;

            p1 = (p1 + 1) % list.length;
            p2 = p2 > 0 ? p2 - 1 : list.length - 1;

            swaps--;
        }
    }
}