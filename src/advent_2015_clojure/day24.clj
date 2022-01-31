(ns advent-2015-clojure.day24
  (:require
    [clojure.set :as set]
    [clojure.string :as str]))

(defn parse-input [input]
  (->> (str/split-lines input)
       (map #(Long/parseLong %))
       (sort-by -)))

(defn box-target [boxes]
  (/ (reduce + boxes) 3))

(defn groups-summing [boxes sum]
  (when-some [box (first boxes)]
    (cond
      (= box sum) [#{box}]
      (> box sum) (groups-summing (rest boxes) sum)
      :else (concat (map #(conj % box) (groups-summing (rest boxes) (- sum box)))
                    (groups-summing (rest boxes) sum)))))
(def groups-summing
  (memoize (fn [boxes sum]
             (when-some [box (first boxes)]
               (cond
                 (= box sum) [#{box}]
                 (> box sum) (groups-summing (rest boxes) sum)
                 :else (concat (map #(conj % box) (groups-summing (rest boxes) (- sum box)))
                               (groups-summing (rest boxes) sum)))))))

(defn box-triple [boxes groups group]
  (->> groups
       (keep #(when (set/subset? % boxes)
                [group % (set/difference boxes %)]))
       first))

(defn box-allocations [boxes target]
  (let [groups (groups-summing boxes target)]
    (keep #(box-triple (set/difference boxes %) groups %) groups)))

(defn box-allocations [boxes target]
  (loop [groups (set (groups-summing boxes target)), found ()]
    (if-some [group (first groups)]
      (if-some [triple (box-triple (set/difference boxes group) groups group)]
        (recur (apply disj groups triple) (conj found triple))
        (recur (disj groups group) found))
      found)))

(def quantum-entanglement (partial transduce (map bigint) *))

(defn part1 [input]
  (let [boxes (parse-input input)
        target (box-target boxes)
        allocations (box-allocations (set boxes) target)]
    (->> (mapcat identity allocations)
         (sort-by (juxt count quantum-entanglement))
         first
         quantum-entanglement)))