(ns advent-2015-clojure.day04
  (:require [advent-2015-clojure.utils :as utils]
            [clojure.string :as str])
  (:import (java.security MessageDigest)))

(defn md5-template [secret-key]
  (doto (MessageDigest/getInstance "MD5")
    (.update (.getBytes secret-key))))

(defn hash-of [^MessageDigest template n]
  (->> (doto ^MessageDigest (.clone template)
         (.update (.getBytes (str n))))
       .digest
       (take 3)                                             ; Only need 3 bytes to check the first 5 or 6 bits
       utils/bytes->hex))

(defn solve [secret-key f]
  (let [template (md5-template secret-key)]
    (->> (range)
         (filter #(f (hash-of template %)))
         first)))

(defn adventcoin? [s] (str/starts-with? s "00000"))
(defn superadventcoin? [s] (= s "000000"))

(defn part1 [secret-key] (solve secret-key adventcoin?))
(defn part2 [secret-key] (solve secret-key superadventcoin?))