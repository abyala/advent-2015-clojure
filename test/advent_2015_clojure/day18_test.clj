(ns advent-2015-clojure.day18-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day18 :refer :all]))

(def test-data (slurp "resources/day18-sample.txt"))
(def puzzle-data (slurp "resources/day18-data.txt"))

(deftest part1-test
  (is (= 4 (part1 test-data 4)))
  (is (= 768 (part1 puzzle-data 100))))

(deftest part2-test
  (is (= 17 (part2 test-data 5)))
  (is (= 781 (part2 puzzle-data 100))))
