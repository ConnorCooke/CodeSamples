altMap:: Eq a => [( a -> b)] -> [a] -> [b]
altMap f x = mapIt f x 0 0
    where mapIt f (x:xs) y z | (xs == [])              = [(f !! y) x]
                             | (y == ((length f) - 1)) = ((f !! y) x) : (mapIt f xs 0 (z + 1))
                             | otherwise               = ((f !! y) x) : (mapIt f xs (y+1) (z + 1))

luhn:: [Int] -> Bool
luhn x = (mod (sum (altMap [luhnDouble,(\x -> x)] x)) 10) == 0
    where luhnDouble x | x < 5 = x * 2
                       | otherwise = (x*2) - 9
          sum []     = 0
          sum (x:xs) = x + sum xs
