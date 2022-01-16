(ns advent-2015-clojure.day20
  (:require [advent-2015-clojure.utils :as utils]))

(defn int-sqrt [n] (int (Math/sqrt n)))
(defn divisible? [v factor] (zero? (rem v factor)))

(defn factors [n]
  (if-not (pos-int? n)
    ()
    (->> (range 1 (inc (int-sqrt n)) (if (even? n) 1 2))
         (mapcat #(when (divisible? n %)
                    (let [r (quot n %)]
                      (if (= r %) (list %) (list % r))))))))

(defn house-presents [n]
  (->> (factors n) (reduce +) (* 10)))

(defn part1 [target]
  (->> (range)
       (keep-indexed (fn [idx n] (when (>= (house-presents n) target) idx)))
       first))

(defn add-presents [houses elf]
  (reduce #(utils/map-add %1 (* elf %2) (* elf 11))
          houses
          (range 1 51)))

(defn part2 [target]
  (reduce (fn [houses elf] (let [houses' (add-presents houses elf)]
                             (if (>= (houses' elf) target)
                               (reduced elf)
                               (dissoc houses' elf)))) {}
          (map inc (range))))
