mySum:: [Integer] -> Integer
mySum []     = 0
mySum (x:xs) = x + (sum xs)

getFactors:: Integer -> [Integer]
getFactors x = factorHelp x (smallFactors x 1 (fromInteger x))
    where smallFactors:: Integer -> Integer -> Float -> [Integer] 
          smallFactors x n m | n > (ceiling m) = []
                             | (mod x n) == 0 = n : (smallFactors x (n+1) ((fromInteger x) / (fromInteger n)))
                             | otherwise = smallFactors x (n+1) ((fromInteger x) / (fromInteger n))
          factorHelp:: Integer -> [Integer] -> [Integer]
          factorHelp x y = y ++ [div x z | z <- y, (div x z)-1 > z && z > 1]
          isIn:: Integer -> [Integer] -> Bool
          isIn x []                 = False
          isIn x (y:ys) | x > y     = False
                        | x == y    = True
                        | otherwise = isIn x ys

perfectNumbers:: [Integer]
perfectNumbers = [y | y <- [2 ..], y == (sum (getFactors y)) ]
