-- Connor Cooke
-- CEC383
-- 11239140

-- calculates n to the power of k
fastExp1:: Int -> Int -> Int
fastExp1 n k =
    if (n == 0) 
    then 0 -- returns 0 if n is 0
    else if (k == 0)
    then 1 -- returns 1 if k is 0
    else if (even k) 
    then ((fastExp1 n (div k 2))^2) -- if k is even, returns the result of (n ^ (k/2))^2
    else n * (fastExp1 n (k-1)) -- if k is odd, multiplies n by result of n^(k-1)

-- calculates n to the power of k
fastExp2:: Int -> Int -> Int
fastExp2 n k | n==0 = 0 -- returns 0 if n is 0
             | k==0 = 1 -- returns 1 if k = 0
             | even k = ((fastExp1 n (div k 2))^2) -- if k is even, returns the result of (n ^ (k/2))^2
             | otherwise = n * (fastExp1 n (k-1)) -- if k is odd, multiplies n by result of n^(k-1)

-- calculates n to the power of k
fastExp3:: Int -> Int -> Int
fastExp3 0 k = 0 -- returns 0 if n is 0
fastExp3 n 0 = 1 -- returns 1 if k is 0
fastExp3 n k | even k = ((fastExp1 n (div k 2))^2) -- if k is even, returns the result of (n ^ (k/2))^2
             | otherwise = n * (fastExp1 n (k-1)) -- if k is odd, multiplies n by result of n^(k-1)
