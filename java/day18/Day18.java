import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.*;

public class Day18 {
    public static void main(String[] args) {
        try {
            Instruction[] instructions = Files
                .lines(Paths.get("../../inputs/day18.txt"))
                .map(x -> x.split(" "))
                .map(strs -> new Instruction(
                            strs[0].trim(),
                            strs[1].trim().toCharArray()[0],
                            strs.length > 2 ? strs[2].trim() : null))
                .toArray(Instruction[]::new);

            System.out.println(solvePt1(instructions));
            System.out.println(solvePt2(instructions));
        }
        catch(Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    static long solvePt1(Instruction[] instructions) {
        HashMap<Character, Long> regs = new HashMap<Character, Long>();
        for(Instruction i : instructions) {
            if(!regs.containsKey(i.reg)) {
                regs.put(i.reg, 0L);
            }
        }
        Computer computer = new Computer(instructions, regs, true, null, null);
        while(computer.step())
            ;
        return computer.getLastPlayed();
    }

    static int solvePt2(Instruction[] instructions) {
        HashMap<Character, Long> regsA = new HashMap<Character, Long>();
        for(Instruction i : instructions) {
            if(!regsA.containsKey(i.reg)) {
                regsA.put(i.reg, 0L);
            }
        }
        HashMap<Character, Long> regsB = new HashMap<Character, Long>();
        for(char key : regsA.keySet()) {
            long v = key == 'p' ? 1L : 0L;
            regsB.put(key, v);
        }
        Queue<Long> queueA = new LinkedList<Long>();
        Queue<Long> queueB = new LinkedList<Long>();
        Computer computerA = new Computer(instructions, regsA, false, queueA, queueB);
        Computer computerB = new Computer(instructions, regsB, false, queueB, queueA);

        while(computerA.step() || computerB.step())
            ;
    	return computerB.getSendCount();
    }

    static class Computer {
    	int sp;
    	HashMap<Character, Long> regs;
    	Instruction[] instructions;
    	Queue<Long> incoming;
    	Queue<Long> outgoing;
        int sendCount;
        boolean waiting;
        long lastPlayed = 0L;
        boolean doPt1;

    	public Computer(Instruction[] instructions, HashMap<Character, Long> regs,
    		boolean doPt1, Queue<Long> incoming, Queue<Long> outgoing) {
    		this.instructions = instructions;
    		this.regs = regs;
            this.doPt1 = doPt1;
    		this.incoming = incoming;
    		this.outgoing = outgoing;
    	}

        public int getSendCount() {
            return sendCount;
        }

        public long getLastPlayed() {
            return lastPlayed;
        }

    	public boolean step() {
    		if(sp >= instructions.length || sp < 0) {
                System.out.println("Outside bounds:" + sp);
    			return false;
            }

    		Instruction inst = instructions[sp];
            long val = inst.isIndirect ?
                regs.get(inst.targetReg) :
                inst.num;
            waiting = false;
            switch(inst.code) {
                case "set":
                    regs.put(inst.reg, val);
                    break;
                case "add":
                    regs.put(inst.reg, regs.get(inst.reg) + val);
                    break;
                case "mul":
                    regs.put(inst.reg, regs.get(inst.reg) * val);
                    break;
                case "mod":
                    regs.put(inst.reg, regs.get(inst.reg) % val);
                    break;
                case "snd":
                    lastPlayed = regs.get(inst.reg);
                    if(!doPt1) {
                        outgoing.add(lastPlayed);
                        sendCount++;
                    }
                    break;
                case "rcv":
                    if(doPt1) {
                        if(regs.get(inst.reg) != 0) {
                            waiting = true;
                        }
                    }
                    else {
                        if(incoming.peek() != null) {
                            regs.put(inst.reg, incoming.poll());
                        }
                        else {
                            waiting = true;
                        }
                    }
                    break;
                case "jgz":
                    if(inst.reg == '1') {
                        sp += val - 1;
                    }
                    else if(regs.get(inst.reg) > 0) {
                        sp += val - 1;
                    }
                    break;
            }
            if(!waiting)
                sp++;
            return !waiting;
    	}
    }


    static class Instruction {
        public Instruction(String code, char reg, String lastPart) {
            this.code = code;
            this.reg = reg;
            if(lastPart != null && lastPart.length() > 0) {
                char c = lastPart.toCharArray()[0];
                if(c >= 'a' && c <= 'z'){
                    this.targetReg = c;
                    isIndirect = true;
                }
                else {
                    this.num = Long.parseLong(lastPart);
                }
            }
        }

        public String code;
        public char reg;
        public char targetReg;
        public long num;
        public boolean isIndirect;

        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("Code:").append(code);
            buf.append(", reg:").append(reg);
            buf.append(", isIndirect:" + isIndirect);
            if(isIndirect)
                buf.append(", targetreg:").append(targetReg);
            else
                buf.append(", num:").append(num);
            return buf.toString();
        }
    }
}
