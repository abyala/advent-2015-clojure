(ns advent-2015-clojure.day06-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day06 :refer :all]))

(def puzzle-data (slurp "resources/day06-data.txt"))

(deftest part1-test
  (is (= 400410 (part1 puzzle-data))))

(deftest part2-test
  (is (= 15343601 (part2 puzzle-data))))