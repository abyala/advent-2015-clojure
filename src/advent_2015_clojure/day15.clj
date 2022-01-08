(ns advent-2015-clojure.day15
  (:require [clojure.string :as str]))

(def all-attributes #{:capacity :durability :flavor :texture :calories})

(defn parse-ingredient [line]
  (let [[ingredient & attribs] (re-seq #"[^ ,:]+" line)]
    (reduce (fn [acc [k v]] (assoc acc (keyword k) (Long/parseLong v)))
            {:ingredient ingredient}
            (partition 2 attribs))))

(defn parse-input [input] (map parse-ingredient (str/split-lines input)))

(defn ingredient-combinations [ingredients teaspoons]
  (let [ingredient (first ingredients)]
    (if (= (count ingredients) 1)
      [{ingredient teaspoons}]
      (mapcat #(map (partial merge {ingredient %})
                    (ingredient-combinations (rest ingredients) (- teaspoons %))) (range (inc teaspoons))))))

(defn recipe-attribute [recipe attrib]
  (reduce (fn [acc [ingredient n]] (+ acc (* (attrib ingredient) n)))
          0
          recipe))

(defn recipe-attributes [recipe]
  (reduce (fn [acc attrib] (assoc acc attrib (recipe-attribute recipe attrib)))
          {}
          all-attributes))

(defn valid-recipe? [recipe] (not-any? neg-int? (vals recipe)))

(defn total-score [recipe]
  (reduce * (map recipe (disj all-attributes :calories))))

(defn solve [input f]
  (->> (ingredient-combinations (parse-input input) 100)
       (map recipe-attributes)
       (filter valid-recipe?)
       (filter f)
       (map total-score)
       (reduce max)))

(defn part1 [input] (solve input some?))
(defn part2 [input] (solve input #(= 500 (:calories %))))