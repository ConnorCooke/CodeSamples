-- Connor Cooke
-- CEC383
-- 11239140

compose3AnyType:: (c -> d) -> (b -> c) -> (a -> b) -> a -> d
compose3AnyType  f g h x = f (g (h x))

compose3:: (Double -> Double) -> (Double -> Double) -> (Double -> Double) -> Double -> Double
compose3  f g h x = compose3AnyType f g h x
