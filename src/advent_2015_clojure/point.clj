(ns advent-2015-clojure.point)

(def origin [0 0])

(defn move-north [[x y]] [x (inc y)])
(defn move-south [[x y]] [x (dec y)])
(defn move-west [[x y]] [(dec x) y])
(defn move-east [[x y]] [(inc x) y])
