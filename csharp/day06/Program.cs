using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;


namespace day06
{
    class Program
    {
        static void Main(string[] args)
        {
            var data = File.ReadAllText("../../inputs/day06.txt").Trim();
            var regs = data
                .Split(new[] {'\t'}, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => int.Parse(x))
                .ToArray();
            Solve(regs);
        }

        static void Solve(int[] regs)
        {
            var cache = new List<int[]>();
            var iterations = 0;
            var foundIdx = 0;
            do {
                cache.Add((int[])regs.Clone());
                Rotate(regs);
                iterations++;
                foundIdx = IndexOf(cache, regs);
            } while(foundIdx == -1);
            Console.WriteLine(iterations);
            Console.WriteLine(iterations - foundIdx);
        }

        static void Rotate(int[] regs)
        {
            var i = regs.ToList().IndexOf(regs.Max());
            var n = regs[i];
            regs[i] = 0;
            while(n > 0)
            {
                regs[++i % regs.Length]++;
                n--;
            }
        }

        static int IndexOf(List<int[]> list, int[] arr)
        {
            for (var i = 0; i < list.Count(); i++)
            {
                if(list[i].SequenceEqual(arr))
                    return i;
            }
            return -1;
        }
    }
}
