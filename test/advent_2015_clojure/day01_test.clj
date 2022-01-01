(ns advent-2015-clojure.day01-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day01 :refer :all]))

(def puzzle-data (slurp "resources/day01-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        0 "(())"
                        0 "()()"
                        3 "((("
                        3 "(()(()("
                        3 "))((((("
                        -1 "())"
                        -1 "))("
                        -3 ")))"
                        -3 ")())())"
                        232 puzzle-data))

(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        1 ")"
                        5 "()())"
                        1783 puzzle-data))
