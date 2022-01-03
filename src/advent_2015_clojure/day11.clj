(ns advent-2015-clojure.day11
  (:require [clojure.string :as str]))

(def forbidden-rotations {\i \j, \o \p, \l \m})
(def forbidden-characters (set (keys forbidden-rotations)))
(def all-characters (map char (range (int \a) (inc (int \z)))))
(def allowed-characters (remove forbidden-characters all-characters))
(def next-chars (->> allowed-characters
                     (partition 2 1)
                     (map vec)
                     (into {})))
(def triples (set (remove #(some forbidden-characters %)
                          (partition 3 1 all-characters))))

(defn has-increasing-straight? [password]
  (boolean (some triples (partition 3 1 password))))
(defn only-allowed-chars? [password]
  (not-any? forbidden-characters password))
(defn two-pairs? [password]
  (let [[a b c] (->> (range (dec (count password)) 0 -1)
                     (filter #(= (get password %) (get password (dec %)))))]
    (boolean (or (some? c)
                 (and b (not= a (inc b)))))))

(defn valid-password? [password]
  ((every-pred has-increasing-straight? only-allowed-chars? two-pairs?) password))

(defn rotate-z
  "Replaces all trailing \"z\" characters to \"a\"s, and rotates the previous non-\"z\" character."
  [password]
  {:pre [(some #(not= % \z) password)]}
  (if-let [idx (->> (range (dec (count password)) -1 -1)
                    (take-while #(= \z (get password %)))
                    last)]
    (reduce #(assoc %1 %2 \a)
            (update password (dec idx) next-chars)
            (range idx (count password)))
    password))

(defn next-possible
  "Given a password, rotates characters to the next possible value without forbidden characters."
  [password]
  (if-some [c (next-chars (last password))]
    (assoc password (dec (count password)) c)
    (rotate-z password)))

(defn roll-up-forbidden
  "Given an initial password, rotates above all forbidden characters. This allows us to let the rest of
  the program only deal with valid characters."
  [password]
  (if-some [idx (->> password
                     (keep-indexed (fn [idx c] (when (forbidden-characters c) idx)))
                     first)]
    (reduce #(assoc %1 %2 \a)
            (update password idx forbidden-rotations)
            (range (inc idx) (count password)))
    password))

(defn valid-password-seq [initial]
  (->> (vec initial)
       roll-up-forbidden
       (iterate next-possible)
       (filter valid-password?)
       (map (partial apply str))))

(defn part1 [input] (-> input valid-password-seq first))
(defn part2 [input] (-> input valid-password-seq second))