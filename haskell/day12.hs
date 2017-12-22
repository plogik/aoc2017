{-# LANGUAGE TemplateHaskell #-} 

import Data.Map.Lazy as Map
import Data.Set as Set

parse :: String -> Map Int [Int]
parse = Map.fromList . Prelude.map parseLine . lines where
    parseLine (words -> x : "<->" : xs) = (read x, read . Prelude.filter isDigit <$> xs)

connected :: (Ord a) => Map a [a] -> a -> Set a
connected neighbors = grow Set.empty . (:[]) where
    grow s [] = s
    grow s (x:xs) = grow (Set.insert x s) $ queue ++ xs where
        queue = if Set.member x s then [] else fromMaybe [] $ neighbors Map.!? x

day12a :: String -> Int
day12a = Set.size . flip connected 0 . parse

day12b :: String -> Int
day12b input = length . unfoldr (fmap dropConnected . Set.minView) $
               Map.keysSet neighbors where
    neighbors = parse input
    dropConnected (x, xs) = ((), xs Set.\\ connected neighbors x)

main = do
    input <- readFile "../inputs/day12.txt"
    let result = day12b input
    printf "Pt2: %d\n" result
