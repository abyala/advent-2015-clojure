(ns advent-2015-clojure.day10)

(defn look-and-say [s]
  (->> (partition-by identity s)
       (map #(vector (count %) (first %)))
       flatten
       (apply str)))

(defn solve [input num-generations]
  (-> (iterate look-and-say input)
      (nth num-generations)
      count))

(defn part1 [input] (solve input 40))
(defn part2 [input] (solve input 50))