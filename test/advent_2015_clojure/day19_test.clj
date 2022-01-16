(ns advent-2015-clojure.day19-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day19 :refer :all]))

(def test-data (slurp "resources/day19-sample.txt"))
(def puzzle-data (slurp "resources/day19-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        4 test-data
                        535 puzzle-data))

(deftest part2-test
  (is (= 212 (part2 puzzle-data))))
