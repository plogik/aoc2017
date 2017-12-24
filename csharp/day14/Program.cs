using System;
using System.Linq;
using System.Text;

namespace day14
{
    class Program
    {
        static void Main(string[] args)
        {
            /*var input = "flqrgnkx" //"ljoxqyyw"
                .Select(c => (int)c)
                .ToList();
            foreach(var n in input)
                Console.Write("{0},", n);
            Console.WriteLine();*/
            var input = "108,106,111,120,113,121,121,119,45,48";
            Console.WriteLine(SolvePt1(input));
            //Console.WriteLine(SolvePt2(input));
        }

        static string SolvePt1(string input)
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
