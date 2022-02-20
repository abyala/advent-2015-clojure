(ns advent-2015-clojure.day22-test
  (:refer-clojure :exclude [cast])
  (:require [clojure.test :refer :all]
            [advent-2015-clojure.day22 :refer :all]))

(def puzzle-hit-points 51)
(def puzzle-damage 9)
(def simple-game (create-game 30 3))

(deftest cast-magic-missile-test
  (is (= (cast simple-game :magic-missile)
         {:player  {:hit-points 50, :mana 447, :armor 0}
          :boss    {:hit-points 26, :damage 3}
          :effects {}, :mana-spent 53}))
  (is (= (-> simple-game (cast :magic-missile) (cast :magic-missile))
         {:player  {:hit-points 50, :mana 394, :armor 0}
          :boss    {:hit-points 22, :damage 3}
          :effects {}, :mana-spent 106})))

(deftest cast-drain-test
  (is (= (cast simple-game :drain)
         {:player  {:hit-points 52, :mana 427, :armor 0}
          :boss    {:hit-points 28, :damage 3}
          :effects {}, :mana-spent 73})))

(deftest cast-shield-test
  (is (= (cast simple-game :shield)
         {:player  {:hit-points 50, :mana 387, :armor 0}
          :boss    {:hit-points 30, :damage 3}
          :effects {:shield 6}, :mana-spent 113})))

(deftest cast-poison-test
  (is (= (cast simple-game :poison)
         {:player  {:hit-points 50, :mana 327, :armor 0}
          :boss    {:hit-points 30, :damage 3}
          :effects {:poison 6}, :mana-spent 173})))

(deftest cast-recharge-test
  (is (= (cast simple-game :recharge)
         {:player  {:hit-points 50, :mana 271, :armor 0}
          :boss    {:hit-points 30, :damage 3}
          :effects {:recharge 5}, :mana-spent 229})))

(deftest apply-effects-test
  (testing "All effects"
    (is (= (-> simple-game
               (add-mana 100)
               (cast :shield)
               (cast :poison)
               (cast :recharge)
               (apply-effects))
           {:player  {:hit-points 50, :mana 186, :armor 7}
            :boss    {:hit-points 27, :damage 3}
            :effects {:shield 5, :poison 5, :recharge 4}, :mana-spent 515})))
  (testing "Removing expired effects"
    (is (= (-> simple-game
               (assoc :effects {:shield 1, :poison 2})
               apply-effects)
           {:player  {:hit-points 50, :mana 500, :armor 7}
            :boss    {:hit-points 27, :damage 3}
            :effects {:poison 1}, :mana-spent 0}))))

(deftest cast-possible-spells-test
  (testing "Only mana for magic missile"
    (let [games (-> simple-game
                    (add-mana -440)
                    cast-possible-spells)]
      (is (= 1 (count games)))
      (is (= (first games)
             {:player  {:hit-points 50, :mana 7, :armor 0}
              :boss    {:hit-points 26, :damage 3}
              :effects {}, :mana-spent 53}))))
  (testing "Shield already in effect"
    (let [games (-> simple-game
                    (add-mana -300)
                    (add-effect :shield 6)
                    cast-possible-spells)]
      (is (= 3 (count games)))
      (is (= #{53 73 173}
             (set (map :mana-spent games)))))))

(deftest take-turn-test
  (testing "Opening turn"
    (let [games (take-turn :easy simple-game)]
      (is (= 5 (count games)))
      (is (= [53 73 113 173 229]
             (map :mana-spent games)))
      (is (not-any? winner games))
      (is (= [26 28 30 27 30]
             (map (comp :hit-points :boss) games)))
      (is (= [47 49 50 47 47]
             (map (comp :hit-points :player) games))))))

(deftest part1-test
  (is (= 900 (part1 puzzle-hit-points puzzle-damage))))

(deftest part2-test
  (is (= 1216 (part2 puzzle-hit-points puzzle-damage))))
