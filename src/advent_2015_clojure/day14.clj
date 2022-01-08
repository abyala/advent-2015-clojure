(ns advent-2015-clojure.day14
  (:require
    [advent-2015-clojure.utils :refer [parse-long]]
    [clojure.string :as str]))

(defn parse-reindeer [s] (mapv parse-long (re-seq #"\d+" s)))
(defn parse-input [input] (map parse-reindeer (str/split-lines input)))

(defn reindeer-progress [reindeer]
  (letfn [(step-seq [[x & xs] n] (lazy-seq (cons n (step-seq xs (+ n x)))))]
    (let [[speed run-seconds rest-seconds] reindeer
          steps (cycle (concat (repeat run-seconds speed)
                               (repeat rest-seconds 0)))]
      (step-seq steps 0))))

(defn all-progress [reindeer]
  (->> (map reindeer-progress reindeer)
       (iterate (partial map rest))
       (map (partial mapv first))))

(defn lead-indexes [coll]
  (let [lead (reduce max coll)]
    (keep-indexed (fn [idx v] (when (= lead v) idx)) coll)))

(defn running-scores [position-seq]
  (letfn [(score-seq [scores [positions & next-positions]]
            (lazy-seq (cons scores
                            (score-seq (reduce #(update %1 %2 inc)
                                               scores
                                               (lead-indexes positions))
                                       next-positions))))]
    (score-seq (mapv (constantly 0) (first position-seq)) (rest position-seq))))

(defn solve [input f]
  (->> (parse-input input)
       f
       (drop 2503)
       first
       (reduce max)))

(defn part1 [input] (solve input all-progress))
(defn part2 [input] (solve input (comp running-scores all-progress)))