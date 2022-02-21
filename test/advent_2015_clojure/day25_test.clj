(ns advent-2015-clojure.day25-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day25 :refer :all]))

(def row 2978)
(def column 3083)

(deftest part1-test
  (is (= 2650453 (part1 row column))))
