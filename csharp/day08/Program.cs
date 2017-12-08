using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;

namespace day08
{
    class Program
    {
        static void Main(string[] args)
        {
            var dataEnum = File.ReadLines("../../inputs/day08.txt");
            var result = Solve(dataEnum);
            Console.WriteLine("Pt1:" + result.Item1);
            Console.WriteLine("Pt2:" + result.Item2);
        }

        static Tuple<int, int> Solve(IEnumerable<string> dataEnum)
        {
            var regs = new Dictionary<string, int>();
            var maxVal = 0;
            foreach(var line in dataEnum)
            {
                var reg = line.Substring(0, line.IndexOf(' '));
                if(!regs.ContainsKey(reg))
                {
                    regs.Add(reg, 0);
                }
                var nextPos = reg.Length + 1;
                bool isInc = line.Substring(nextPos, line.IndexOf(' ', nextPos) - nextPos).StartsWith("inc");
                nextPos += "inc".Length + 1;
                var operandStr = line.Substring(nextPos, line.IndexOf(' ', nextPos) - nextPos);
                nextPos += operandStr.Length + "if".Length + 2;
                var operand = int.Parse(operandStr);
                var checkReg = line.Substring(nextPos, line.IndexOf(' ', nextPos) - nextPos);
                nextPos += checkReg.Length + 1;
                var op = line.Substring(nextPos, line.IndexOf(' ', nextPos) - nextPos);
                nextPos += op.Length + 1;
                var checkVal = int.Parse(line.Substring(nextPos));
                
                var currCheckRegVal = regs.ContainsKey(checkReg) ? regs[checkReg] : 0;
                var conditionTrue = false;
                switch(op)
                {
                    case ">":
                        conditionTrue = currCheckRegVal > checkVal;
                        break;
                    case "<":
                        conditionTrue = currCheckRegVal < checkVal;
                        break;
                    case ">=":
                        conditionTrue = currCheckRegVal >= checkVal;
                        break;
                    case "<=":
                        conditionTrue = currCheckRegVal <= checkVal;
                        break;
                    case "==":
                        conditionTrue = currCheckRegVal == checkVal;
                        break;
                    case "!=":
                        conditionTrue = currCheckRegVal != checkVal;
                        break;
                }
                if(conditionTrue)
                {
                    if(isInc)
                        regs[reg] += operand;
                    else
                        regs[reg] -= operand;
                }
                var currMax = regs.Values.Max();
                maxVal = currMax > maxVal ? currMax : maxVal;
            }
            return Tuple.Create(regs.Values.Max(), maxVal);
            
        }
    }
}
