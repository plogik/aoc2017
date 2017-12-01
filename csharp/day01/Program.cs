using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace day01
{
    class Program
    {
        static void Main(string[] args)
        {
            var data = File.ReadAllText("input.txt").Trim();
            Console.WriteLine(SolvePt1(data));
            Console.WriteLine(SolvePt2(data));

            Console.ReadLine();

        }
        static int SolvePt1(string data)
        {
            return data
                .Where((c, i) => c == data[(i + 1) % data.Length])
                .Select(c => c - '0')
                .Sum();
        }

        static int SolvePt2(string data)
        {
            return Enumerable.Range(0, data.Length)
                .Where(x => data[x] == data[(x + data.Length / 2) % data.Length])
                .Select(x => data[x] - '0')
                .Sum();
        }
    }
}
