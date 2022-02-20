(ns advent-2015-clojure.day22
  (:refer-clojure :exclude [cast]))

;; Game: {:player {:hit-points :mana :armor},
;;        :boss {:hit-points :damage},
;;        :effects [],
;;        :mana-spent 0,
;;        :winner :player|:boss}

(defn create-game [boss-hit-points boss-damage]
  {:player  {:hit-points 50, :mana 500, :armor 0}
   :boss    {:hit-points boss-hit-points, :damage boss-damage}
   :effects {}, :mana-spent 0})

(defn add-mana [game amount]
  (update-in game [:player :mana] + amount))
(defn cast-spell [game cost]
  (when (>= (get-in game [:player :mana]) cost)
    (-> game
        (add-mana (- cost))
        (update :mana-spent + cost))))

(defn effect-duration [game effect-name]
  (-> game :effects effect-name))
(defn add-effect [game spell-name turns]
  (when-not (effect-duration game spell-name)
    (assoc-in game [:effects spell-name] turns)))

(defn winner [game]
  (:winner game))
(defn set-winner [game who]
  (assoc game :winner who))
(defn calc-winner [game]
  (cond
    (winner game) game
    (<= (get-in game [:player :hit-points]) 0) (set-winner game :boss)
    (<= (get-in game [:boss :hit-points]) 0) (set-winner game :player)
    :else game))

(defn hit [game who amount]
  (-> game
      (update-in [who :hit-points] - amount)
      calc-winner))
(defn hit-boss [game amount] (hit game :boss amount))
(defn hit-player [game amount] (hit game :player amount))
(defn heal [game amount]
  (update-in game [:player :hit-points] + amount))
(defn set-armor [game amount]
  (assoc-in game [:player :armor] amount))

(defn- cast-effect-spell [game cost spell-name duration]
  (some-> game
          (cast-spell cost)
          (add-effect spell-name duration)))

(defmulti cast (fn [_game spell] spell))
(defmethod cast :magic-missile [game _]
  (some-> game
          (cast-spell 53)
          (hit-boss 4)))
(defmethod cast :drain [game _]
  (some-> game
          (cast-spell 73)
          (hit-boss 2)
          (heal 2)))
(defmethod cast :shield [game _] (cast-effect-spell game 113 :shield 6))
(defmethod cast :poison [game _] (cast-effect-spell game 173 :poison 6))
(defmethod cast :recharge [game _] (cast-effect-spell game 229 :recharge 5))
(defn cast-possible-spells [game]
  (->> [:magic-missile :drain :shield :poison :recharge]
       (keep (partial cast game))
       seq))

(defn decrease-effect-timer [game effect-name]
  (if (= (effect-duration game effect-name) 1)
    (update game :effects dissoc effect-name)
    (update game :effects update effect-name dec)))

(defmulti apply-effect (fn [_game effect] effect))
(defmethod apply-effect :shield [game _] (set-armor game 7))
(defmethod apply-effect :poison [game _] (hit-boss game 3))
(defmethod apply-effect :recharge [game _] (add-mana game 101))
(defn apply-effects [game]
  (reduce (fn [g effect]
            (-> g
                (apply-effect effect)
                (decrease-effect-timer effect)))
          game
          (-> game :effects keys)))

(defn boss-attack [game]
  (let [base-damage (get-in game [:boss :damage])
        armor (get-in game [:player :armor])
        damage (max (- base-damage armor) 0)]
    (hit-player game damage)))

(defn take-turn [mode game]
  (let [prep-game (fn [g] (-> g (set-armor 0) apply-effects))
        before-action (-> (if (= mode :hard) (hit-player game 1) game)
                          prep-game)]
    (if (winner before-action)
      [before-action]
      (->> (cast-possible-spells before-action)
           (map (fn [g] (if (winner g) g (-> g prep-game boss-attack))))))))

(defn play-all-games [mode starting-game]
  (loop [playing (list starting-game), seen #{}, finished #{}]
    (let [[game & next-games] playing]
      (cond
        (nil? game) finished
        (seen game) (recur next-games seen finished)
        (winner game) (recur next-games (conj seen game) (conj finished game))
        :else (recur (apply conj next-games (take-turn mode game))
                     (conj seen game)
                     finished)))))

(defn least-mana-to-win [mode boss-hit-points boss-attack]
  (->> (create-game boss-hit-points boss-attack)
       (play-all-games mode)
       (filter #(= :player (winner %)))
       (map :mana-spent)
       (apply min)))

(def part1 (partial least-mana-to-win :easy))
(def part2 (partial least-mana-to-win :hard))