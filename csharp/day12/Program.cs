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
            var lines = File.ReadLines("../../inputs/day12.txt");
            var  nodeData = lines
                .Select(x => x.Split(" <-> "))
                .ToDictionary(
                        x => int.Parse(x[0]),
                        x => x[1].Split(',')
                            .Select(e => int.Parse(e))
                            .ToList());
            Console.WriteLine("Part 1:" + SolvePt1(nodeData));
            Console.WriteLine("Part 2:" + SolvePt2(nodeData));
        }

        static int SolvePt1(Dictionary<int, List<int>> nodes)
        {
            var leafList = new List<int>();
            AddLeaves(nodes, 0, leafList, new List<int>());
            return leafList.Distinct().Count();
        }

        static int SolvePt2(Dictionary<int, List<int>> nodes)
        {
            var highestNode = nodes.Keys.Max();
            var groups = new List<List<int>>();
            for(int i = 0; i < highestNode; i++)
            {
                if(!Exists(groups, i))
                {
                    var group = new List<int>();
                    AddLeaves(nodes, i, group, new List<int>());
                    groups.Add(group);
                }
            }
            return groups.Count;
        }

        static bool Exists(List<List<int>> haystack, int needle)
        {
            foreach(var list in haystack)
            {
                if(list.Contains(needle))
                    return true;
            }
            return false;
        }

        static void AddLeaves(Dictionary<int,List<int>> nodes, int root, List<int> leafList, List<int> visitedRoots)
        {
            leafList.AddRange(nodes[root]);
            foreach(var leaf in nodes[root])
            {
                if(!visitedRoots.Contains(leaf))
                {
                    visitedRoots.Add(root);
                    AddLeaves(nodes, leaf, leafList, visitedRoots);
                }
            }
        }
    }
}
