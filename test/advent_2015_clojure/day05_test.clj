(ns advent-2015-clojure.day05-test
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day05 :refer :all]))

(def puzzle-data (slurp "resources/day05-data.txt"))

(deftest nice-string?-test
  (is (true? (nice-string? "ugknbfddgicrmopn")))
  (is (true? (nice-string? "aaa")))
  (is (false? (nice-string? "jchzalrnumimnmhp")))
  (is (false? (nice-string? "haegwjzuvuyypxyu")))
  (is (false? (nice-string? "dvszwmarrgswjxmb"))))

(deftest part1-test
  (is (= 255 (part1 puzzle-data))))

(deftest extra-nice-string?-test
  (is (true? (extra-nice-string? "qjhvhtzxzqqjkmpb")))
  (is (true? (extra-nice-string? "xxyxx")))
  (is (false? (extra-nice-string? "uurcxstgmygtbstg")))
  (is (false? (extra-nice-string? "ieodomkazucvgmuy"))))

(deftest part2-test
  (is (= 55 (part2 puzzle-data))))
