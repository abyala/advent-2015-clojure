(ns advent-2015-clojure.day17-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day17 :refer :all]))

(def puzzle-data (slurp "resources/day17-data.txt"))

(deftest part1-test
  (is (= 654 (part1 puzzle-data))))

(deftest part2-test
  (is (= 57 (part2 puzzle-data))))
