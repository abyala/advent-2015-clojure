(ns advent-2015-clojure.day16-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day16 :refer :all]))

(def puzzle-data (slurp "resources/day16-data.txt"))

(deftest part1-test
  (is (= 40 (part1 puzzle-data))))

(deftest part2-test
  (is (= 241 (part2 puzzle-data))))
