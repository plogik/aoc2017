using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

public class Reddit_Day07
{
    static void Main(string[] args)
    {
        var data = File.ReadAllText("../../inputs/day07.txt").Trim();
        Console.WriteLine(PartTwo(data));
    }

    public static string PartOne(string input)
    {
        var lines = input.Lines();

        var discs = lines.Select(x => new Disc(x)).ToList();
        discs.ForEach(x => x.AddChildDiscs(discs));

        return GetBaseDisc(discs).Name;
    }

    public static Disc GetBaseDisc(IEnumerable<Disc> discs)
    {
        var disc = discs.First();

        while (disc.Parent != null)
        {
            disc = disc.Parent;
        }
        return disc;
    }

    public static string PartTwo(string input)
    {
        var lines = input.Lines();

        var discs = lines.Select(x => new Disc(x)).ToList();
        discs.ForEach(x => x.AddChildDiscs(discs));

        var disc = GetBaseDisc(discs);
        var targetWeight = 0;

        while (!disc.IsBalanced())
        {
            (disc, targetWeight) = disc.GetUnbalancedChild();
        }

        var weightDiff = targetWeight - disc.GetTotalWeight();
        Console.WriteLine("Disc:" + disc.Name + " targetWeight:" + targetWeight);
        return (disc.Weight + weightDiff).ToString();
    }
}


static class StringHelpers
{

    public static IEnumerable<string> Lines(this string input)
    {
        return input.Split(new string[] { Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries);
    }

    public static IEnumerable<string> Words(this string input)
    {
        return input.Split(new string[] { " ", "\t", Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries);
    }

    public static string Shave(this string a, int characters)
    {
        return a.Substring(characters, a.Length - (characters * 2));
    }

    public static string ShaveRight(this string a, string str)
    {

        if(a.IndexOf(str) != -1)
        {
            a = a.Substring(0, a.IndexOf(str));
        }
        return a;
    }
}

public class Disc
{
    public int Weight { get; private set; }
    public string Name { get; private set; }
    public List<string> ChildNames { get; private set; }
    public List<Disc> ChildDiscs { get; private set; }
    public Disc Parent { get; private set; }

    public Disc(string input)
    {
        var words = input.Words().ToList();

        Name = words[0];
        Weight = int.Parse(words[1].Shave(1));
        ChildNames = new List<string>();

        for (var i = 3; i < words.Count; i++)
        {
            ChildNames.Add(words[i].ShaveRight(","));
        }
    }

    public void AddChildDiscs(IEnumerable<Disc> discs)
    {
        ChildDiscs = ChildNames.Select(x => discs.First(y => y.Name == x)).ToList();
        ChildDiscs.ForEach(x => x.Parent = this);
    }

    public int GetTotalWeight()
    {
        var childSum = ChildDiscs.Sum(x => x.GetTotalWeight());
        return childSum + Weight;
    }

    public bool IsBalanced()
    {
        var groups = ChildDiscs.GroupBy(x => x.GetTotalWeight());
        return groups.Count() == 1;
    }

    public (Disc disc, int targetWeight) GetUnbalancedChild()
    {
        var groups = ChildDiscs.GroupBy(x => x.GetTotalWeight());
        var targetWeight = groups.First(x => x.Count() > 1).Key;
        var unbalancedChild = groups.First(x => x.Count() == 1).First();

        return (unbalancedChild, targetWeight);
    }
}
