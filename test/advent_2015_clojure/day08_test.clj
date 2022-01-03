(ns advent-2015-clojure.day08-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day08 :refer :all]))

(def test-data (slurp "resources/day08-sample.txt"))
(def puzzle-data (slurp "resources/day08-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        12 test-data
                        1350 puzzle-data))

(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        19 test-data
                        2085 puzzle-data))