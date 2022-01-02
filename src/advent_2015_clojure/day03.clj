(ns advent-2015-clojure.day03
  (:require [advent-2015-clojure.point :as point]))

(defmulti move (fn [_point c] c))
(defmethod move \^ [p _] (point/move-north p))
(defmethod move \v [p _] (point/move-south p))
(defmethod move \< [p _] (point/move-west p))
(defmethod move \> [p _] (point/move-east p))

(defn house-seq
  ([input] (house-seq point/origin input))
  ([loc input] (if (seq input)
                 (lazy-seq (cons loc (house-seq (move loc (first input))
                                                (rest input))))
                 [loc])))

(defn solve [input n]
  (let [partitions (partition n input)]
    (->> (range n)
         (map (fn [santa] (map #(nth % santa) partitions)))
         (mapcat house-seq)
         set
         count)))

(defn part1 [input] (solve input 1))
(defn part2 [input] (solve input 2))
