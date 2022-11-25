(ns advent-2015-clojure.day24
  (:require
    [clojure.set :as set]
    [clojure.string :as str]))

(defn parse-input [input]
  (->> (str/split-lines input)
       (map #(Long/parseLong %))
       (sort-by -)))

(defn box-target [num-groups boxes]
  (/ (reduce + boxes) num-groups))

(def groups-summing
  (memoize (fn [boxes sum]
     (when-some [box (first boxes)]
       (cond (= box sum) (concat [#{box}] (groups-summing (rest boxes) sum))
             (> box sum) (groups-summing (rest boxes) sum)
             :else (concat (map #(conj % box) (groups-summing (rest boxes) (- sum box)))
                           (groups-summing (rest boxes) sum)))))))

(def quantum-entanglement (partial transduce (map bigint) *))

(defn sorted-groups [groups]
  (->> groups
       (map #(vector [(count %) (quantum-entanglement %)] %))
       (sort-by first)
       (map second)))

(defn splittable-groups? [boxes-used num-groups groups]
  (let [remaining (remove #(seq (set/intersection % boxes-used)) groups)]
    (if (zero? num-groups)
      (empty? remaining)
      (some #(splittable-groups? % (dec num-groups) remaining) remaining))))

(defn solve [num-groups input]
  (let [boxes (parse-input input)
        target (box-target num-groups boxes)
        groups (sorted-groups (groups-summing boxes target))]
    (->> groups
         (filter #(splittable-groups? % (dec num-groups) groups))
         first
         quantum-entanglement)))

(defn part1 [input] (solve 3 input))
(defn part2 [input] (solve 4 input))