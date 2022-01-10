(ns advent-2015-clojure.day16
  (:require
    [advent-2015-clojure.utils :as utils]
    [clojure.string :as str]))

(def ticker-tape {"children"    3
                  "cats"        7
                  "samoyeds"    2
                  "pomeranians" 3
                  "akitas"      0
                  "vizslas"     0
                  "goldfish"    5
                  "trees"       3
                  "cars"        2
                  "perfumes"    1})

(defn parse-sue [line]
  (reduce (fn [acc [k v]] (assoc acc k (Integer/parseInt v)))
          {}
          (->> line (re-seq #"[^ :,]+") (drop 2) (partition 2))))

(defn parse-input [input] (->> input str/split-lines (mapv parse-sue)))

(defn matches? [compare-fn sue]
  (every? (fn [[attrib v]]
            ((compare-fn attrib) v (ticker-tape attrib)))
          sue))

(defn solve [compare-fn input]
  (->> (parse-input input)
       (utils/keep-indexes-when (partial matches? compare-fn))
       first
       inc))

(def part1 (partial solve (fn [_] =)))
(def part2 (partial solve (fn [attrib] (condp contains? attrib
                                #{"cats" "trees"} >
                                #{"pomeranians" "goldfish"} <
                                =))))