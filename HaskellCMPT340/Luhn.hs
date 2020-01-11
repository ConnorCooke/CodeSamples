-- Connor Cooke
-- CEC383
-- 11239140

-- Receives a digit, doubles it, and substracts by 9 if the result is greater than or equal to 10
luhnDouble:: Int -> Int
luhnDouble x | x < 5 = x * 2
             | otherwise = (x*2) - 9

-- Receives 4 integers, luhnDouble's the 1st and 3nd digit, and then sums the result and returns true if it's divisible by 10 
luhn:: Int -> Int -> Int -> Int -> Bool
luhn w x y z = checkTotal(((luhnDouble w) + x) + ((luhnDouble y) + z))
    where checkTotal x = if((mod x 10) == 0) then True else False
