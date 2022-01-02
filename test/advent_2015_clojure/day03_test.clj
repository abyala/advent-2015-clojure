(ns advent-2015-clojure.day03-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day03 :refer :all]))

(def puzzle-data (slurp "resources/day03-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        2 ">"
                        4 "^>v<"
                        2 "^v^v^v^v^v"
                        2081 puzzle-data))

(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        3 "^v"
                        3 "^>v<"
                        11 "^v^v^v^v^v"
                        2341 puzzle-data))
