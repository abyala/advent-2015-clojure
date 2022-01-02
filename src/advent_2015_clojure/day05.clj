(ns advent-2015-clojure.day05
  (:require
    [advent-2015-clojure.utils :as utils]
    [clojure.string :as str]))

(defn vowel? [c] (#{\a \e \i \o \u} c))
(def naughty-pairs #{[\a \b] [\c \d] [\p \q] [\x \y]})

(defn num-vowels [s] (count (filter vowel? s)))
(defn has-double? [s]
  (->> (partition 2 1 s)
       (some (partial apply =))))
(defn has-naughty-pair? [s]
  (->> (partition 2 1 s)
       (some naughty-pairs)))

(defn nice-string? [s]
  (boolean (and (>= (num-vowels s) 3)
                (has-double? s)
                (not (has-naughty-pair? s)))))

(defn pair-indexes [s]
  (reduce #(utils/map-conj %1 (subs s %2 (+ 2 %2)) %2)
          {}
          (range  (-> s count dec))))

(defn non-repeating-double? [s]
  (->> (pair-indexes s)
       vals
       (some (fn [indexes] (seq (for [i indexes, j indexes, :when (> i (inc j))] true))))))

(defn one-letter-between? [s]
  (->> (partition 3 1 s)
       (some (fn [[a _ c]] (= a c)))))

(defn extra-nice-string? [s] ((every-pred non-repeating-double? one-letter-between?) s))

(defn part1 [input] (->> input str/split-lines (filter nice-string?) count))
(defn part2 [input] (->> input str/split-lines (filter extra-nice-string?) count))