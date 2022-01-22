(ns advent-2015-clojure.day23-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day23 :refer :all]))

(def test-data (slurp "resources/day23-sample.txt"))
(def puzzle-data (slurp "resources/day23-data.txt"))

(deftest part1-test
  (is (= 170 (part1 puzzle-data))))

(deftest part2-test
  (is (= 247 (part2 puzzle-data))))
