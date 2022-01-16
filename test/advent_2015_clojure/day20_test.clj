(ns advent-2015-clojure.day20-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day20 :refer :all]))

(def puzzle-data 34000000)

(deftest house-presents-test
  (are [expected n] (= expected (house-presents n))
                    10 1
                    30 2
                    40 3
                    70 4
                    60 5
                    120 6
                    80 7
                    150 8
                    130 9))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        6 120
                        8 121
                        786240 puzzle-data))

(deftest part2-test
  (is (= 831600 (part2 puzzle-data))))
