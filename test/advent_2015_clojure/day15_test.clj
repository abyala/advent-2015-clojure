(ns advent-2015-clojure.day15-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day15 :refer :all]))

(def test-data (slurp "resources/day15-sample.txt"))
(def puzzle-data (slurp "resources/day15-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        62842880 test-data
                        21367368 puzzle-data))

(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        57600000 test-data
                        1766400 puzzle-data))
