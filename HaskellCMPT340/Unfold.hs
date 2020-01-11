unfold:: Eq a => (a -> Bool) -> (a -> b) -> (a -> a) -> a -> [b]
unfold p h t x | p x = []
               | otherwise = h x:unfold p h t (t x)

map :: Eq a => (a -> b) -> [a] -> [b]
map f y = unfold (==[]) (function f) (tail) y
    where function f (x:xs) = f x 
          tail (x:xs) = xs

iterate:: Eq a => (a -> a) -> a -> [a]
iterate f x = unfold (\x -> False) f (iterat (f)) x
    where iterat f x = f (f x)

repHalve:: Eq a => [a] -> [[a]]
repHalve x = unfold (== []) half secHalf x
    where half x = getHalf (toInteger (ceiling ( (fromInteger (toInteger (length x))) / 2.0))) x
          getHalf y (x:xs) | (xs == []) = [x]
                           | (y == 0)  = []
                           | otherwise = x: (getHalf (y - 1) xs)
          secHalf x | (length x) == 1 = []
                    | otherwise = getSecHalf (toInteger (ceiling ((fromInteger (toInteger (length x))) / 2.0))) x
          getSecHalf y (x:xs) | (y==0)    = xs
                              | otherwise = getSecHalf (y-1) xs
