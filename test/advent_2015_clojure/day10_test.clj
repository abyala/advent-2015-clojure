(ns advent-2015-clojure.day10-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day10 :refer :all]))

(def puzzle-data "3113322113")

(deftest part1-test
  (is (= 329356 (part1 puzzle-data))))

(deftest part2-test
  (is (= 4666278 (part2 puzzle-data))))
