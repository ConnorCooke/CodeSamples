-- Connor Cooke
-- CEC383
-- 11239140

-- Gets the average integer value of three integers
averageThree:: Int -> Int -> Int -> Int
averageThree x y z =  ceilOrFloor ((fromIntegral ((x + y) + z)) / 3)
    where ceilOrFloor x | (x - (fromIntegral (floor x)) ) > 0.5 = ceiling x
                        | otherwise = floor  x

-- Gets the average value of three integers as a float
averageThreeFloat:: Int -> Int -> Int -> Float
averageThreeFloat x y z = ((fromIntegral ((x + y) + z)) / 3)

-- Totals up the amount of values that are greater than the average
howManyAboveAverage:: Int -> Int -> Int -> Int
howManyAboveAverage x y z = (greaterThan (fromIntegral x) (averageThreeFloat x y z)) + (greaterThan (fromIntegral y) (averageThreeFloat x y z)) + (greaterThan (fromIntegral z) (averageThreeFloat x y z))
     where greaterThan x y | x > y = 1
                           | otherwise = 0

-- receives an integer triple and returns the average values of the triple
averageThreeInOne:: (Int, Int, Int) -> Int
averageThreeInOne (x, y, z) = averageThree x y z

orderTriple:: (Int, Int, Int) -> (Int, Int, Int)
orderTriple (x, y, z) | ((howManyAboveAverage x y z) == 0) = (x, y, z)
                      | ((x >= y) && (x >= z)) = ( (lesser y z), (greater y z), x)
                      | ((y >= x) && (y >= z)) = ( (lesser x z), (greater x z), y)
                      | otherwise = ( (lesser x y), (greater x y), z)
    where greater a b | a > b = a
                      | otherwise = b
          lesser a b | a < b = a
                     | otherwise = b

