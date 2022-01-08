(ns advent-2015-clojure.day14
  (:require
    [advent-2015-clojure.utils :as utils :refer [parse-long]]
    [clojure.string :as str]))

(defn parse-reindeer [s] (mapv parse-long (re-seq #"\d+" s)))
(defn parse-input [input] (map parse-reindeer (str/split-lines input)))

(defn reindeer-progress [reindeer]
  (let [[speed run-seconds rest-seconds] reindeer]
    (->> [0 (cycle (concat (repeat run-seconds speed)
                           (repeat rest-seconds 0)))]
         (iterate (fn [[acc [x & xs]]] [(+ acc x) xs]))
         (map first))))

(defn all-progress [reindeer]
  (->> (map reindeer-progress reindeer)
       (iterate (partial map rest))
       (map (partial mapv first))))

(defn lead-indexes [coll]
  (let [lead (reduce max coll)]
    (utils/keep-indexes-when (partial = lead) coll)))

(defn add-points-to-leaders [scores positions]
  (reduce #(update %1 %2 inc)
          scores
          (lead-indexes positions)))

(defn running-scores [position-seq]
  (->> [(vec (repeat (-> position-seq first count) 0)) (rest position-seq)]
       (iterate (fn [[scores [pos & pos']]] [(add-points-to-leaders scores pos) pos']))
       (map first)))

(defn solve [input f]
  (->> (parse-input input)
       f
       (drop 2503)
       first
       (reduce max)))

(defn part1 [input] (solve input all-progress))
(defn part2 [input] (solve input (comp running-scores all-progress)))