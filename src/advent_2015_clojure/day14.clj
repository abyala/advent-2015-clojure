(ns advent-2015-clojure.day14
  (:require
    [advent-2015-clojure.utils :refer [parse-long]]
    [clojure.string :as str]))

(defn parse-reindeer [s]
  (into {} (mapv vector
                 [:speed :run-seconds :rest-seconds]
                 (mapv parse-long (re-seq #"\d+" s)))))

(defn parse-input [input]
  (map parse-reindeer (str/split-lines input)))

(defn reindeer-progress [reindeer]
  (letfn [(step-seq [[x & xs] n] (lazy-seq (cons n (step-seq xs (+ n x)))))]
    (let [{:keys [speed run-seconds rest-seconds]} reindeer
          steps (cycle (concat (repeat run-seconds speed)
                               (repeat rest-seconds 0)))]
      (step-seq steps 0))))

(defn lead-indexes [coll]
  (let [lead (reduce max coll)]
    (keep-indexed (fn [idx v] (when (= lead v) idx)) coll)))

(defn take-turn [reindeer-stats]
  (let [{:keys [steps scores]} reindeer-stats
        next-distances (mapv first steps)
        next-scores (reduce #(update %1 %2 inc)
                            scores
                            (lead-indexes next-distances))]
    {:steps     (map rest steps)
     :distances next-distances
     :scores    next-scores}))

(defn race [all-reindeer]
  (->> {:steps  (mapv (comp rest reindeer-progress) all-reindeer)
        :scores (mapv (constantly 0) (range (count all-reindeer)))}
       (iterate take-turn)
       (map #(select-keys % [:distances :scores]))))

(defn solve [attrib n input]
  (let [result (nth (-> input parse-input race) n)]
    (reduce max (attrib result))))

(defn part1 [input] (solve :distances 2503 input))
(defn part2 [input] (solve :scores 2503 input))