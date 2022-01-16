(ns advent-2015-clojure.day21-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day21 :refer :all]))

(def puzzle-data {:hit-points 104, :damage 8, :armor 1})

(deftest part1-test
  (is (= 78 (part1 puzzle-data))))

(deftest part2-test
  (is (= 148 (part2 puzzle-data))))