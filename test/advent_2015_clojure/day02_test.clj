(ns advent-2015-clojure.day02-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day02 :refer :all]))

(def puzzle-data (slurp "resources/day02-data.txt"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        58 "2x3x4"
                        43 "1x1x10"
                        1586300 puzzle-data))

(deftest part2-test
  (are [expected input] (= expected (part2 input))
                        34 "2x3x4"
                        14 "1x1x10"
                        3737498 puzzle-data))
