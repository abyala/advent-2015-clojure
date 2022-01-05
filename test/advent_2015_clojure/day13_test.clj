(ns advent-2015-clojure.day13-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day13 :refer :all]))

(def test-data (slurp "resources/day13-sample.txt"))
(def puzzle-data (slurp "resources/day13-data.txt"))

(deftest part1-test
  (is (= 330 (part1 test-data)))
  (is (= 709 (part1 puzzle-data))))

(deftest part2-test
  (is (= 668 (part2 puzzle-data))))