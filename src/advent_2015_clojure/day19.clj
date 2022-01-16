(ns advent-2015-clojure.day19
  (:require [advent-2015-clojure.utils :as utils]
            [clojure.string :as str]))

(defn parse-input [input]
  (let [[combos molecule] (utils/split-blank-line input)]
    {:replacements (->> (str/split-lines combos)
                        (map #(str/split % #" => "))
                        (reduce (fn [m [from to]] (utils/map-conj m from to)) {}))
     :molecule     molecule}))

(defn possible-replacements [replacements molecule]
  (->> (range (count molecule))
       (mapcat (fn [idx]
                 (let [sub-molecule (subs molecule idx)]
                   (->> replacements
                        (mapcat (fn [[from to-options]]
                                  (when (str/starts-with? sub-molecule from)
                                    (map (fn [to] (str (subs molecule 0 idx)
                                                       to
                                                       (subs molecule (+ idx (count from)))))
                                         to-options))))))))
       set))

(defn part1 [input]
  (let [{:keys [replacements molecule]} (parse-input input)]
    (count (possible-replacements replacements molecule))))

(defn part2
  "This was one of those non-programming puzzles, based on the input data.  Move along."
  [input]
  (let [elements (->> (parse-input input)
                      :molecule
                      (re-seq #"[A-Z][a-z]*"))]
    (- (count elements)
       (count (filter #{"Rn" "Ar"} elements))
       (* 2 (count (filter #(= % "Y") elements)))
       1)))
