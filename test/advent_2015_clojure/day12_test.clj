(ns advent-2015-clojure.day12-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day12 :refer :all]))

(def puzzle-data (slurp "resources/day12-data.txt"))

(deftest part1-test
  (are [expected s] (= expected (part1 s))
                    6 "[1,2,3]"
                    6 "{\"a\":2,\"b\":4}"
                    3 "[[[3]]]"
                    3 "{\"a\":{\"b\":4},\"c\":-1}"
                    0 "{\"a\":[-1,1]}"
                    0 "[-1,{\"a\":1}]"
                    0 "[]"
                    0 "{}"
                    119433 puzzle-data))

(deftest part2-test
  (are [expected s] (= expected (part2 s))
                    6 "[1,2,3]"
                    4 "[1,{\"c\":\"red\",\"b\":2},3]"
                    0 "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"
                    6 "[1,\"red\",5]"
                    68466 puzzle-data))