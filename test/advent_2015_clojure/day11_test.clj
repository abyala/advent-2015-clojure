(ns advent-2015-clojure.day11-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day11 :refer :all]))

(def puzzle-data "cqjxjnds")

(deftest valid-password?-test
  (are [expected s] (= expected (valid-password? (vec s)))
                    false "hijklmmn"
                    false "abbceffg"
                    false "abbcegjk"
                    false "abcdefgh"
                    true "abcdffaa"
                    false "ghijklmn"
                    true "ghjaabcc"))

(deftest part1-test
  (are [expected input] (= expected (part1 input))
                        "abcdffaa" "abcdefgh"
                        "ghjaabcc" "ghijklmn"
                        "cqjxxyzz" puzzle-data))

(deftest part2-test
  (is (= "cqkaabcc" (part2 puzzle-data))))
