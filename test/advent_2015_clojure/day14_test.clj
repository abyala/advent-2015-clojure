(ns advent-2015-clojure.day14-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day14 :refer :all]))

(def test-data (slurp "resources/day14-sample.txt"))
(def puzzle-data (slurp "resources/day14-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        2696 puzzle-data))

(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        1084 puzzle-data))
