(ns advent-2015-clojure.day24-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day24 :refer :all]))

(def test-data (slurp "resources/day24-sample.txt"))
(def puzzle-data (slurp "resources/day24-data.txt"))

(deftest part1-test
  (is (= 99 (part1 test-data)))
  (is (= 11846773891N (part1 puzzle-data))))

(deftest part2-test
  (is (= 80393059 (part2 puzzle-data))))
