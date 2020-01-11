pyth:: Int -> [(Int,Int,Int)]
pyth n = [(x,y,z) | x <- [1 .. (n-1)], y <- [1 .. (n-1)], z <- [1 .. n], ((x * x) + (y * y)) == (z * z)]
