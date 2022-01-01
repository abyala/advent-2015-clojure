(ns advent-2015-clojure.day01)

(defn floor-move [c] ({\( 1 \) -1} c))

(defn part1 [input]
  (apply + (map floor-move input)))

(defn part2 [input]
  (loop [floor 0, n 0]
    (if (= -1 floor)
      n (recur (+ floor (floor-move (get input n))) (inc n)))))
