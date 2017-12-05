using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace day05
{
    class Program
    {
        static void Main(string[] args)
        {
            var data = File.ReadAllText("../../inputs/day05.txt");
            var instructions = data
                .Split(new[] {' ', '\n', '\r'}, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => int.Parse(x))
                .ToArray();
            Console.WriteLine(SolvePt1(instructions.ToArray())); // Make a copy so pt2 gets correct instructions!
            Console.WriteLine(SolvePt2(instructions));
        }

        static int SolvePt1(int[] instructions)
        {
            var sp = 0;
            var idx = 0;
            while(sp < instructions.Length)
            {
                sp += instructions[sp]++;
                idx++;
            }
            return idx; 
        }

        static int SolvePt2(int[] instructions)
        {
            var sp = 0;
            var idx = 0;
            while(sp < instructions.Length)
            {
                var prevSp = sp;
                sp += instructions[sp];
                instructions[prevSp] += instructions[prevSp] >= 3 ? -1 : 1;
                idx++;
            }
            return idx; 
        }
    }
}
