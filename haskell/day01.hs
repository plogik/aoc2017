import Data.Char

--pt1 :: String -> Int
--pt1 [x] = 0
--pt1 s = go s 0 0
--    where go [] _ n = n
--          go [x] n = n
--          go (x:xs) n = go xs (n + (if x == (head xs) then (ord x) else 0))

pt1 :: String -> Int
pt1 s = go s 0 0
    where go s i n =
            if i == (length s) then n
            else go s (i + 1) (if (s!!i == (s!!(mod (length s) (i + 1) ))) then (n + (ord (s!!i))) else n)



main = do
    input <- readFile "../inputs/day01.txt"
    putStrLn $ "Result:" ++ show (pt1 input)

