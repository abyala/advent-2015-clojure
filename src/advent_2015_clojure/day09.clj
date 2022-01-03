(ns advent-2015-clojure.day09
  (:require
    [advent-2015-clojure.utils :refer [parse-long]]
    [clojure.string :as str]))

(defn parse-input [input]
  (reduce (fn [acc line] (let [[c1 _ c2 _ d] (re-seq #"\S+" line)
                               dist (parse-long d)]
                           (assoc acc [c1 c2] dist [c2 c1] dist)))
          {}
          (str/split-lines input)))

(defn all-cities [distances]
  (-> distances keys flatten set))

(defn all-paths [distances]
  (let [cities (all-cities distances)]
    (loop [unseen (map #(vector [%] 0) cities), paths {}]
      (if-not (seq unseen)
        paths
        (let [[path cost] (first unseen)
              next-steps (apply disj cities path)]
          (if-not (seq next-steps)
            (recur (rest unseen) (assoc paths path cost))
            (recur (apply conj (rest unseen) (map #(vector (conj path %) (+ cost (distances [(last path) %]))) next-steps))
                   paths)))))))

(defn part1 [input]
  (->> (parse-input input)
       all-paths
       vals
       (apply min)))

(defn solve [input f]
  (->> (parse-input input)
       all-paths
       vals
       (apply f)))

(defn part1 [input] (solve input min))
(defn part2 [input] (solve input max))