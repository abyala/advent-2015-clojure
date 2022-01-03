(ns advent-2015-clojure.day08
  (:require [clojure.string :as str]))

(defn memory-chars [s]
  (loop [n 0, s' s]
    (if (str/blank? s')
      (- n 2)
      (recur (inc n)
             (subs s' (cond (str/starts-with? s' "\\x") 4
                            (str/starts-with? s' "\\") 2
                            :else 1))))))

(defn encoded-chars [s]
  (+ 2
     (count s)
     (count (re-seq #"[\\\"]" s))))

(def count-xf (map (partial count)))
(def memory-xf (map (partial memory-chars)))
(def encoded-xf (map (partial encoded-chars)))

(defn solve [input & xfs]
  (let [words (str/split-lines input)]
    (apply - (map #(transduce % + words) xfs))))

(defn part1 [input] (solve input count-xf memory-xf))
(defn part2 [input] (solve input encoded-xf count-xf))