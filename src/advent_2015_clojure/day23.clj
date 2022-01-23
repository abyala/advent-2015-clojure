(ns advent-2015-clojure.day23
  (:require [clojure.string :as str]))

(defn new-state [instructions] {:registers    {"a" 0 "b" 0}
                                :instructions instructions
                                :offset       0})

(defn register-instruction [f state] (-> state
                                         (update :registers f)
                                         (update :offset inc)))
(defn jump-instruction [f state] (update state :offset + (or (f (:registers state)) 1)))

(defn parse-instruction [line]
  (let [[op & arg-seq] (re-seq #"[^ ,]+" line)
        [arg0 arg1] (mapv (comp #(if (symbol? %) (str %) %)
                                read-string)
                          arg-seq)]
    (case op
      "hlf" (partial register-instruction #(update % arg0 quot 2))
      "tpl" (partial register-instruction #(update % arg0 * 3))
      "inc" (partial register-instruction #(update % arg0 inc))
      "jmp" (partial jump-instruction (fn [_] arg0))
      "jie" (partial jump-instruction #(when (even? (% arg0)) arg1))
      "jio" (partial jump-instruction #(when (= (% arg0) 1) arg1)))))

(defn parse-input [input]
  (->> input str/split-lines (mapv parse-instruction) new-state))

(defn current-instruction [state]
  (let [{:keys [instructions offset]} state]
    (get instructions offset nil)))

(defn run-step [state]
  (when-some [instruction (current-instruction state)]
    (instruction state)))

(defn run-program [state]
  (->> (iterate run-step state)
       (take-while some?)
       last))

(defn final-register-b [state] (get-in (run-program state) [:registers "b"]))
(defn part1 [input] (-> input parse-input final-register-b))
(defn part2 [input] (-> input parse-input (assoc-in [:registers "a"] 1) final-register-b))