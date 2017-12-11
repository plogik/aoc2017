using System;
using System.IO;
using System.Linq;

namespace day11
{
    class Program
    {
        static void Main(string[] args)
        {
            var data = File.ReadAllText("../../inputs/day11.txt");
            SolvePt1(data);
            SolvePt2(data);
        }

        static void SolvePt1(string input)
        {
            var dirs = input
                .Split(new[] {','}, StringSplitOptions.RemoveEmptyEntries);
            var sCount = dirs.Where(x => x == "s").Count();
            var nCount = dirs.Where(x => x == "n").Count();
            var nwCount = dirs.Where(x => x == "nw").Count();
            var seCount = dirs.Where(x => x == "se").Count();
            var neCount = dirs.Where(x => x == "ne").Count();
            var swCount = dirs.Where(x => x == "sw").Count();

            Console.WriteLine("S:{0}, n:{1}, nw:{2}, se:{3}, ne:{4}, sw:{5}",
                    sCount, nCount, nwCount, seCount, neCount, swCount);
            // Answer: s: 1389, n: 1742, nw: 1271, se: 1399, ne: 1377, sw: 1045
            // n - s = 353 n, ne - sw = 332 ne, se - nw = 128 se
            // Since se diff is less than ne that does not do anything
            // So the answer is 353 + 332 = 685
        }

        static void SolvePt2(string input)
        {
            var dirs = input
                .Split(new[] {','}, StringSplitOptions.RemoveEmptyEntries);
            var maxDistance = 0;
            var x = 0;
            var y = 0;
            var z = 0;
            foreach(var dir in dirs)
            {
                switch(dir)
                {
                    case "n": 
                        y++;
                        z--;
                        break;
                    case "s":
                        y--;
                        z++;
                        break;
                    case "ne":
                        x++;
                        z--;
                        break;
                    case "sw":
                        x--;
                        z++;
                        break;
                    case "nw":
                        x--;
                        y++;
                        break;
                    case "se":
                        x++;
                        y--;
                        break;
                }
                var curr = (Math.Abs(x) + Math.Abs(y) + Math.Abs(z)) / 2;   
                maxDistance = Math.Max(maxDistance, curr);
            }
            Console.WriteLine("Max:" + maxDistance);
        }
    }
}
