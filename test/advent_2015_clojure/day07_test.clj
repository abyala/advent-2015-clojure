(ns advent-2015-clojure.day07-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day07 :refer :all]))

(def puzzle-data (slurp "resources/day07-data.txt"))

(deftest part1-test
  (is (= 3176 (part1 puzzle-data))))

(deftest part2-test
  (is (= 14710 (part2 puzzle-data))))