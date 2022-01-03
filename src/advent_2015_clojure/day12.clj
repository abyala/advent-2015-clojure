(ns advent-2015-clojure.day12
  (:require [advent-2015-clojure.utils :as utils :refer [parse-long]]))

(defn collapse-object [o f]
  (if (f o) (:value o) 0))

(defn collapse-array [o]
  (collapse-object o any?))

(defn set-red [o]
  (assoc o :red true))

(defn add-value-to [o v]
  (utils/map-add o :value v))

(def empty-container {:value 0})
(defn parse [input f]
  (loop [tokens (re-seq #"\{|\}|\[|\]|red|\-?\d+" input),
         [x & xs :as stack] (list empty-container)]
    (if-not (seq tokens)
      (:value x)
      (recur (rest tokens)
             (let [t (first tokens)]
               (case t
                 "[" (cons empty-container stack)
                 "{" (cons empty-container stack)
                 "]" (cons (add-value-to (first xs) (collapse-array x)) (rest xs))
                 "}" (cons (add-value-to (first xs) (collapse-object x f)) (rest xs))
                 "red" (cons (set-red x) xs)
                 (cons (add-value-to x (parse-long t)) xs)))))))

(defn part1 [input] (parse input any?))
(defn part2 [input] (parse input (partial (complement :red))))