(ns advent-2015-clojure.day06
  (:require
    [advent-2015-clojure.utils :refer [parse-long]]
    [clojure.string :as str]))

(defn parse-instruction [line]
  (let [[_ op x1 y1 x2 y2] (re-matches #"(.*) (\d+),(\d+) through (\d+),(\d+)" line)]
    [(keyword (last (str/split op #" "))) (parse-long x1) (parse-long x2) (parse-long y1) (parse-long y2)]))

(defn parse-input [input]
  (map parse-instruction (str/split-lines input)))

(defn instruction-range [[_ x0 x1 y0 y1]]
  (into #{} (for [x (range x0 (inc x1))
                  y (range y0 (inc y1))]
              [x y])))

(defn solve [fn-map input]
  (->> (parse-input input)
       (reduce (fn [acc ins] (let [f (fn-map (first ins))]
                               (reduce (fn [m p] (let [value (or (m p) 0)
                                                       value' (max 0 (f value))]
                                                   (assoc m p value')))
                                       acc
                                       (instruction-range ins))))
               {})
       vals
       (apply +)))

(defn part1 [input]
  (solve {:on (constantly 1), :off (constantly 0), :toggle (partial - 1)} input))

(defn part2 [input]
  (solve {:on inc, :off dec, :toggle (partial + 2)} input))