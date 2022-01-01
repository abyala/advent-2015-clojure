(ns advent-2015-clojure.day02
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (map read-string (re-seq #"\d+" line)))

(defn dimension-pairs [[x y z]] [[x y] [x z] [y z]])
(defn surface-area [[x y]] (* x y))
(defn perimeter [[x y]] (* 2 (+ x y)))
(defn cubic-volume [[x y z]] (* x y z))

(defn wrapping-paper-for [dimensions]
  (let [areas (map surface-area (dimension-pairs dimensions))]
    (+ (* 2 (reduce + areas))
       (reduce min areas))))

(defn ribbon-for [dimensions]
  (+ (reduce min (map perimeter (dimension-pairs dimensions)))
     (cubic-volume dimensions)))

(defn solve [input f]
  (->> (str/split-lines input)
       (map (comp f parse-line))
       (reduce +)))

(defn part1 [input] (solve input wrapping-paper-for))
(defn part2 [input] (solve input ribbon-for))