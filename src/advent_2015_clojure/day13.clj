(ns advent-2015-clojure.day13
  (:require [clojure.string :as str]))

(def ^:private regex #"(\w+) would (\w+) (\d+) happiness units by sitting next to (\w+)\.")

(defn parse-input [input]
  (reduce (fn [acc line] (let [[_ from action amt to] (re-matches regex line)]
                           (assoc acc [from to] (({"gain" + "lose" -} action) 0 (Long/parseLong amt)))))
          {}
          (str/split-lines input)))

(defn all-people [costs] (->> costs keys (map first) set))

(defn all-paths [people]
  (if (= 1 (count people))
    (list (seq people))
    (mapcat #(map (partial cons %) (all-paths (disj people %)))
            people)))

(defn all-rings [people]
  (map #(cons (last %) %) (all-paths people)))

(defn path-cost [costs path]
  (transduce (map #(get costs % 0)) + (partition 2 1 path)))

(defn solve [input & bonus-people]
  (let [costs (parse-input input)
        people (apply conj (all-people costs) bonus-people)]
    (->> (all-rings people)
         (map #(reduce + (map (partial path-cost costs) [% (reverse %)])))
         (reduce max))))

(defn part1 [input] (solve input))
(defn part2 [input] (solve input :me))