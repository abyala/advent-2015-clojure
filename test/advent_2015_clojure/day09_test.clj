(ns advent-2015-clojure.day09-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day09 :refer :all]))

(def test-data (slurp "resources/day09-sample.txt"))
(def puzzle-data (slurp "resources/day09-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        605 test-data
                        117 puzzle-data))

(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        982 test-data
                        909 puzzle-data))
