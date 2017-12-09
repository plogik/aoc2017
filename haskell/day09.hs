import System.CPUTime (getCPUTime)
import Text.Printf

solve :: String -> (Int, Int)
solve s = go s False 0 0 0
    -- args: input inGarbage nestlevel total totalGarbage
    where go [] _ _ t g = (t, g)
          go (x:xs) inGb l t g
            | x == '{' = go xs inGb
                (if inGb then l else (l + 1))
                t
                (if inGb then (g + 1) else g)
            | x == '}' = go xs inGb
                (if inGb then l else (l - 1))
                (if inGb then t else (t + l))
                (if inGb then (g + 1) else g)
            | x == '!' = go (tail xs) inGb l t g
            | x == '<' = go xs True l t
                (if inGb then (g + 1) else g)
            | x == '>' = go xs False l t g
            | otherwise = go xs inGb l t
                (if inGb then (g + 1) else g)

main = do
    input <- readFile "../inputs/day09.txt"
    start <- getCPUTime
    let result = solve input
    end <- getCPUTime
    let diff = (fromIntegral (end - start)) / (10^12)
    printf "Total: %d, garbage: %d, time: %0.7fs\n" (fst result) (snd result) (diff::Double)
