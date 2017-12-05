using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace day04
{
    class Program
    {
        static void Main(string[] args)
        {
            var dataEnum = File.ReadLines("../../inputs/day04.txt");
            Console.WriteLine(SolvePt1(dataEnum));
            Console.WriteLine(SolvePt2(dataEnum));
        }

        static int SolvePt1(IEnumerable<string> dataEnum)
        {
            return dataEnum
                .Where(x => IsValid(x))
                .Count();
        }

        static bool IsValid(string phrase)
        {
            var words = phrase
                .Split(new[] {' ', '\t'}, StringSplitOptions.RemoveEmptyEntries);
            return words.Count() == words.Distinct().Count();
        }

        static int SolvePt2(IEnumerable<string> dataEnum)
        {
            return dataEnum
                .Where(x => IsValidPt2(x))
                .Count();
        }

        static bool IsValidPt2(string phrase)
        {
            var words = phrase
                .Split(new[] {' ', '\t'}, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => new string(x.ToArray().OrderBy(c => c).ToArray()))
                .ToList();
            return words.Count() == words.Distinct().Count();
        }
    }
}
