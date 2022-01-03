(ns advent-2015-clojure.day07
  (:require [advent-2015-clojure.utils :refer [parse-long]]
            [clojure.string :as str]))

(def max-byte 65535)

(defn parse-dependencies [s]
  (let [wire-names (fn [& args] (set (remove parse-long args)))
        [a b c :as args] (str/split s #" ")]
    (case (count args)
      1 {:op "=" :args [a] :dependencies (wire-names a)}
      2 {:op a :args [b] :dependencies (wire-names b)}
      3 {:op b :args [a c] :dependencies (wire-names a c)})))

(defn parse-instruction [instruction]
  (let [[left right] (str/split instruction #" -> ")]
    (assoc (parse-dependencies left) :target right)))

(defn parse-input [input]
  (map parse-instruction (str/split-lines input)))

(defn wire-ready? [instruction wires]
  (every? wires (:dependencies instruction)))

(defn solve [instruction wires]
  (let [{:keys [op args]} instruction
        [arg0 arg1] (map #(if-some [n (parse-long %)] n (wires %)) args)]
    (case op "=" arg0
             "AND" (bit-and arg0 arg1)
             "OR" (bit-or arg0 arg1)
             "NOT" (bit-xor arg0 max-byte)
             "LSHIFT" (bit-shift-left arg0 arg1)
             "RSHIFT" (bit-shift-right arg0 arg1))))

(defn solve-wires [instructions]
  (loop [undefined (set instructions), wires {}]
    (if-not (seq undefined)
      wires
      (let [instruction (first (filter #(wire-ready? % wires) undefined))]
        (recur (disj undefined instruction)
               (assoc wires (:target instruction) (solve instruction wires)))))))

(defn solve-for-a [instructions]
  (get (solve-wires instructions) "a"))

(defn part1 [input]
  (-> input parse-input solve-for-a))

(defn part2 [input]
  (let [instructions (parse-input input)
        wire-a (part1 input)
        new-instructions (-> (remove #(= "b" (:target %)) instructions)
                             (conj (parse-instruction (str wire-a " -> b"))))]
    (solve-for-a new-instructions)))