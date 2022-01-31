(ns advent-2015-clojure.day24-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day24 :refer :all]))

(def test-data (slurp "resources/day24-sample.txt"))
(def puzzle-data (slurp "resources/day24-data.txt"))

(deftest part1-test
  (is (= 99 (part1 test-data)))
  (is (= 11846773891N (part1 puzzle-data))))

#_(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        1 ")"
                        5 "()())"
                        1783 puzzle-data))
