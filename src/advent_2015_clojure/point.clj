(ns advent-2015-clojure.point
  (:require [clojure.string :as str]))

(def origin [0 0])

(defn move-north [[x y]] [x (inc y)])
(defn move-south [[x y]] [x (dec y)])
(defn move-west [[x y]] [(dec x) y])
(defn move-east [[x y]] [(inc x) y])

(defn parse-to-char-coords
  "Given an input string, returns a lazy sequence of [[x y] c] tuples of [x y] coords to each character c."
  [input]
  (->> (str/split-lines input)
       (map-indexed (fn [y line]
                      (map-indexed (fn [x c] [[x y] c]) line)))
       (apply concat)))

(defn surrounding
  ([point] (surrounding false point))
  ([include-self? point] (let [points (if include-self? [[-1 -1] [0 -1] [1 -1] [-1 0] [0 0] [1 0] [-1 1] [0 1] [1 1]]
                                                        [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]])]
                           (map (partial mapv + point) points))))