(ns advent-2015-clojure.utils
  (:require [clojure.string :as str]))

(defn parse-long [s] (try
                       (Long/parseLong s)
                       (catch NumberFormatException _ nil)))

(defn bytes->hex [b]
  (->> b
       (map (comp #(if (= 1 (count %)) (str "0" %) %)
                  #(Integer/toHexString %)
                  #(bit-and 0xff %)))
       (apply str)))

(defn map-add [m k n]
  (if (m k)
    (update m k + n)
    (assoc m k n)))

(defn map-conj [m k v]
  (if (m k)
    (update m k conj v)
    (assoc m k [v])))

(defn keep-indexes-when [f coll]
  (keep-indexed (fn [idx v] (when (f v) idx)) coll))

(defn split-blank-line
  "Given an input string, returns a sequence of sub-strings, separated by a completely
  blank string. This function preserves any newlines between blank lines, and it filters
  out Windows' \"\r\" characters."
  [input]
  (-> (str/replace input "\r" "")
      (str/split #"\n\n")))