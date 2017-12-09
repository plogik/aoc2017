using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;

namespace day07
{
    class Program
    {
        static void Main(string[] args)
        {
            var data = File.ReadAllText("../../inputs/day07.txt").Trim();
            //Console.WriteLine(SolvePt1(data));
            SolvePt2(data);
        }

        // Finds the one node name that only occurs once
        static string SolvePt1(string data)
        {
            return Regex
                .Matches(data, "[a-zA-Z]+(?=[,\\s$])")
                .Select(x => x.ToString())
                .GroupBy(x => x)
                .OrderBy(g => g.Count())
                .SelectMany(g => g)
                .First();
        }

        static void SolvePt2(string data)
        {
            var nodes = GetNodes(data);
            Console.WriteLine("Node count:" + nodes.Count);
            var rootName = SolvePt1(data);
            var rootNode = new Node() { Name = rootName };
            BuildTree(data, rootNode);
            foreach(var child in rootNode.Children)
            {
                Console.WriteLine("Total for {0} ({1}):{2}",
                    child.Name, nodes[child.Name], WeightTotalFor(nodes, child));
            }
        }

        static int WeightTotalFor(Dictionary<string, int> allNodes, Node node)
        {
            var total = allNodes[node.Name];
            foreach(var child in node.Children)
            {
                total += WeightTotalFor(allNodes, child);
            }
            return total;
        }

        static void BuildTree(string input, Node root)
        {
            foreach(var nodeName in NodeNamesUnder(input, root.Name))
            {
                var leaf = new Node() { Name = nodeName };
                root.Children.Add(leaf);
                BuildTree(input, leaf);
            }
        }

        static Dictionary<string, int> GetNodes(string data)
        {
            var matches = Regex
                .Matches(data, @"([a-zA-Z]+)\s+\((\d+)\)");
            return matches
                    .Select(m => new { Name = m.Groups[1], Weight = m.Groups[2] })
                    .ToDictionary(g => g.Name.Value, g => int.Parse(g.Weight.Value));
        }

        static List<string> NodeNamesUnder(string input, string rootName)
        {
            var subNodeLine = input
                .Split(new[] {'\n','\r'}, StringSplitOptions.RemoveEmptyEntries)
                .Where(s => s.StartsWith(rootName))
                .First();
            if(subNodeLine != null && subNodeLine.IndexOf("->") != -1)
            {
               return subNodeLine
                    .Substring(subNodeLine.IndexOf("->") + 2)
                    .Split(new[] {','}, StringSplitOptions.RemoveEmptyEntries)
                    .Select(s => s.Trim())
                    .ToList();
            }
            return new List<string>();
        }

        static int WeightsFor(Dictionary<string, int> nodes, string nodename)
        {
            return 0;
        }

        class Node
        {
            public string Name;
            //public int Weight;
            public List<Node> Children = new List<Node>();
        }
    }
}

