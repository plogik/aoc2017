using System;
using System.Linq;
using System.Text;

namespace day10
{
    class Program
    {
        static void Main(string[] args)
        {
            var input = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70";
            Console.WriteLine(SolvePt1(input));
            Console.WriteLine(SolvePt2(input));
        }

        static int SolvePt1(string input)
        {
            var list = Enumerable
                            .Range(0, 256)
                            .ToArray();
            var lengths = input
                .Split(new[] {','}, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => int.Parse(x))
                .ToArray();
            var skipSize = 0;
            var currPos = 0;
            foreach(var length in lengths)
            {
                Reverse(list, currPos, length);
                currPos = (currPos + skipSize + length) % list.Length;
                skipSize++;
            }
            return list[0] * list[1];
        }

        static string SolvePt2(string input)
        {
            var list = Enumerable
                            .Range(0, 256)
                            .ToArray();
            var buf = new StringBuilder();
            foreach(var c in input)
                buf.Append(((int)c).ToString()).Append(',');
            buf.Append("17,31,73,47,23");

            var lengths = buf.ToString()
                .Split(new[] {','}, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => int.Parse(x))
                .ToArray();

            var skipSize = 0;
            var currPos = 0;
            for(int i = 0; i < 64; i++)
            {
                foreach(var length in lengths)
                {
                    Reverse(list, currPos, length);
                    currPos = (currPos + skipSize + length) % list.Length;
                    skipSize++;
                }
            }
            buf = new StringBuilder();
            for(int i = 0; i < 16; i++)
            {
                buf.Append(list
                        .Skip(i * 16)
                        .Take(16)
                        .Aggregate(0, (n, a) => n ^ a)
                        .ToString("x2"));
            }
            return buf.ToString();
        }

        static void Reverse(int[] list, int start, int length)
        {
            var p1 = start % list.Length;
            var p2 = (start + length - 1) % list.Length;
            var swaps = length / 2;
            while(swaps > 0)
            {
                var tmp = list[p1];
                list[p1] = list[p2];
                list[p2] = tmp;

                p1 = (p1 + 1) % list.Length;
                p2 = p2 > 0 ? p2 - 1 : list.Length - 1;

                swaps--;
            }
        }

        static void PrintList(int[] list)
        {
            foreach(var e in list)
            {
                Console.Write("{0}, ", e);
            }
            Console.WriteLine();
        }
    }
}
