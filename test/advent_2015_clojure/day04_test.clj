(ns advent-2015-clojure.day04-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day04 :refer :all]))

(def puzzle-data "yzbqklnj")

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        609043 "abcdef"
                        1048970 "pqrstuv"
                        282749 puzzle-data))

(deftest part2-test
  (is (= 9962624 (part2 puzzle-data))))