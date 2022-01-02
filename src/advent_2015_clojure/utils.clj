(ns advent-2015-clojure.utils)

(defn bytes->hex [b]
  (->> b
       (map (comp #(if (= 1 (count %)) (str "0" %) %)
                  #(Integer/toHexString %)
                  #(bit-and 0xff %)))
       (apply str)))
