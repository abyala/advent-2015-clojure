(ns advent-2015-clojure.utils)

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