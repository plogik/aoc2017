using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace day02
{
    class Program
    {
        static void Main(string[] args)
        {
            var dataEnum = File.ReadLines("../../inputs/day02.txt");
            //Console.WriteLine(SolvePt1(dataEnum));
            Console.WriteLine(SolvePt2(dataEnum));
        }

        static int SolvePt1(IEnumerable<string> dataEnum)
        {
            return dataEnum.Select(x => Diff(x)).Sum();
        }

        static int SolvePt2(IEnumerable<string> dataEnum)
        {
            return dataEnum.Select(x => EvenQuotient(x)).Sum();
        }

        static int Diff(string row)
        {
            var values = row
                .Split(new[] {' ', '\t'}, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => int.Parse(x))
                .ToList();
            return values.Max() - values.Min();
        }

        static int EvenQuotient(string row)
        {
            var values = row
                .Split(new[] {' ', '\t'}, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => int.Parse(x))
                .ToArray();
            for(int i = 0; i < values.Length - 1; i++)
            {
                for(int j = i + 1; j < values.Length; j++)
                {
                    var dividend = Math.Max(values[i], values[j]);
                    var divisor = Math.Min(values[i], values[j]);
                    if(dividend % divisor == 0)
                    {
                        return dividend / divisor;
                    }
                }
            }
            return 0;
        }

    }

}
