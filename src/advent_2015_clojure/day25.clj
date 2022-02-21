(ns advent-2015-clojure.day25)

(def code-seq
  (iterate #(-> % (* 252533) (rem 33554393))
           20151125))
(def pos-seq (for [y (range)
                   x (range 1 (inc y))]
               [x (- (inc y) x)]))
(def grid (map vector pos-seq code-seq))

(defn part1 [row column]
  (->> grid
       (keep (fn [[pos v]] (when (= pos [column row]) v)))
       first))
