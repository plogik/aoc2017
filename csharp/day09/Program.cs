using System;
using System.IO;

namespace day09
{
    class Program
    {
        static void Main(string[] args)
        {
            var data = File.ReadAllText("../../inputs/day09.txt");
            var result = Solve(data);
            Console.WriteLine("Total:{0}, garbage:{1}", result.total, result.totalGarbage);
        }

        static (int total, int totalGarbage) Solve(string input)
        {
            var inGarbage = false;
            var nestLevel = 0;
            var total = 0;
            var totalGarbage = 0;
            for(int i = 0; i < input.Length; i++)
            {
                switch(input[i])
                {
                    case '{':
                        if(!inGarbage)
                            nestLevel++;
                        else
                            totalGarbage++;
                        break;
                    case '}':
                        if(!inGarbage)
                        {
                            total += nestLevel;
                            nestLevel--;
                        }
                        else
                            totalGarbage++;
                        break;
                    case '!':
                        i++;
                        break;
                    case '<':
                        if(inGarbage)
                            totalGarbage++;
                        inGarbage = true;
                        break;
                    case '>':
                        inGarbage = false;
                        break;
                    default:
                        if(inGarbage)
                            totalGarbage++;
                        break;
                }
            }
            return (total, totalGarbage);
        }
    }
}
