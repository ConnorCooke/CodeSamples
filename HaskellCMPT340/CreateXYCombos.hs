createXYCombosTwoGens:: [a] -> [b] -> [(a,b)]
createXYCombosTwoGens w z = [(x,y) | x <- w, y <- z]

createXYCombosTwoComs:: [a] -> [b] -> [(a,b)]
createXYCombosTwoComs w z = concat [[(x,y)| x <- w] | y <- z ]
