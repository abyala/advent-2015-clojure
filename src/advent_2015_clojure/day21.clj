(ns advent-2015-clojure.day21)

; Game: {:player {:hit-points x, :damage y, :armor z}
;        :boss   {:hit-points x, :damage y, :armor z}
;        :next-player :player}
(defn attack-item [n] (fn [player] (update player :damage + n)))
(defn defense-item [n] (fn [player] (update player :armor + n)))

(def store
  (let [sell-attack-item (fn [[cost damage]] {:cost cost :f (attack-item damage)})
        sell-defense-item (fn [[cost armor]] {:cost cost :f (defense-item armor)})]
    {:weapons (map sell-attack-item [[8 4] [10 5] [25 6] [40 7] [74 8]])
     :armor (map sell-defense-item [[13 1] [31 2] [53 3] [75 4] [102 5]])
     :rings (concat (map sell-attack-item [[25 1] [50 2] [100 3]])
                    (map sell-defense-item [[20 1] [40 2] [80 3]]))}))

(defn total-purchase-cost [items] (transduce (map :cost) + items))

(def purchase-options
  (let [items (vec (:rings store))
        armors (:armor store)
        weapons (:weapons store)

        ring-options (concat '(())
                             (map list items)
                             (mapcat (fn [idx] (map #(list (items idx) (items %)) (range (inc idx) (count items))))
                                     (range (count items))))
        ring-and-armor (concat ring-options
                               (mapcat (fn [ring-option] (map #(cons % ring-option) armors) ) ring-options))
        all-options (mapcat (fn [raa-option] (map #(cons % raa-option) weapons)) ring-and-armor)]
    (sort-by total-purchase-cost all-options)))

(defn player-with-purchases [options]
  (reduce #(%2 %1)
          {:hit-points 100 :damage 0 :armor 0}
          (map :f options)))

(defn starting-game [boss purchases]
  {:next-turn :player
   :boss boss
   :player (player-with-purchases purchases)})

(defn enemy [s] (if (= s :player) :boss :player))
(defn health-of [game who] (get-in game [who :hit-points]))

(defn fight [game]
  (let [attacker (:next-turn game)
        defender (enemy attacker)
        damage (max 1 (- (get-in game [attacker :damage])
                         (get-in game [defender :armor])))
        life-remaining (max 0 (- (health-of game defender) damage))]
    (-> game
        (assoc-in [defender :hit-points] life-remaining)
        (assoc :next-turn defender))))

(defn winner [game]
  (cond (zero? (health-of game :player)) :boss
        (zero? (health-of game :boss)) :player))

(defn fight-to-the-death [game]
  (->> (iterate fight game)
       (keep winner)
       first))

(defn solve [options desired-winner boss]
  (->> options
       (filter #(let [game (starting-game boss %)]
                  (= desired-winner (fight-to-the-death game))))
       first
       total-purchase-cost))

(defn part1 [boss] (solve purchase-options :player boss))
(defn part2 [boss] (solve (reverse purchase-options) :boss boss))
