(ns advent-2015-clojure.day17
  (:require [clojure.string :as str]))

(defn sort-buckets [input]
  (->> input str/split-lines (map #(Long/parseLong %)) (sort-by -) vec))

(defn find-combos [target buckets]
  (when (seq buckets)
    (let [[_ not-too-big] (split-with (partial < target) buckets)
          [equal too-small] (split-with (partial = target) not-too-big)]
      (into (map list equal)
            (->> (range (dec (count too-small)))
                 (map #(drop % too-small))
                 (mapcat (fn [[v & vs]]
                         (when-some [combos (seq (find-combos (- target v) vs))]
                           (map #(cons v %) combos)))))))))

(defn part1 [input] (->> input sort-buckets (find-combos 150) count))
(defn part2 [input] (->> input sort-buckets (find-combos 150) (map count) frequencies (reduce min-key) second))