using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace day12
{
    class Program
    {
        static void Main(string[] args)
        {
            //var data = File.ReadAllText("../../inputs/day12.txt");
            var dataEnum = File.ReadLines("../../inputs/day12.txt");
            Console.WriteLine(SolvePt1(dataEnum));
            //Console.WriteLine("0 <-> 1,2,3".Split(" <-> ")[1]);
        }

        static int SolvePt1(IEnumerable<string> lines)
        {
            var  nodeData = lines
                .Select(x => x.Split(" <-> "))
                .ToDictionary(
                        x => int.Parse(x[0]),
                        x => x[1].Split(',')
                            .Select(e => int.Parse(e))
                            .ToList());
            return nodeData[0][0];
        }

        static int LeafCount(Dictionary<int,List<int>> nodes, int root)
        {
        }
    }
}
