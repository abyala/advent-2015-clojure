(ns advent-2015-clojure.day18
  (:require [advent-2015-clojure.point :as point]))

(def on-char \#)
(def off-char \.)
(defn on? [c] (= c on-char))

(defn parse-grid [input]
  (into {} (point/parse-to-char-coords input)))

(defn surrounding-on [grid p]
  (->> (map grid (point/surrounding p))
       (filter on?)
       count))

(defn corners-on [grid]
  (let [max-x (apply max (map ffirst grid))
        max-y (apply max (map (comp second first) grid))]
    (merge grid (reduce #(assoc %1 %2 on-char)
                        {}
                        [[0 0] [0 max-y] [max-x 0] [max-x max-y]]))))

(defn next-state
  ([grid] (reduce-kv (fn [m k _] (assoc m k (next-state grid k)))
                     {}
                     grid))
  ([grid p] (let [neighbor-count (surrounding-on grid p)
                  next-on? (if (on? (grid p))
                             (<= 2 neighbor-count 3)
                             (= 3 neighbor-count))]
              (if next-on? on-char off-char))))

(defn on-count [grid] (->> grid vals (filter on?) count))

(defn part1 [input num-steps]
  (->> (parse-grid input)
       (iterate next-state)
       (drop num-steps)
       first
       on-count))

(defn part2 [input num-steps]
  (->> (corners-on (parse-grid input))
       (iterate (comp corners-on next-state))
       (drop num-steps)
       first
       on-count))