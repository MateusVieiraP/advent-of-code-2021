(ns advent-of-code-2021.day-1.solution
  (:require [clojure.string :as str]))

; Input
(def input
  (->> (slurp "resources/day-1-input.txt")
       str/split-lines
       (map #(Integer/parseInt %))))

; Part 1
(defn count-increase-number
  [data-input]
  (let [window1 data-input
        window2 (rest window1)]
    (->> (map #(> %1 %2) window2 window1)
         (filter true?)
         count)))

(println "Parte 1:" (count-increase-number input))

; Part 2
(defn count-increase-in-three-measurement-windows
  [data-input]
  (let [window1 data-input
        window2 (rest window1)
        window3 (rest window2)]
    (->> (map #(+ %1 %2 %3) window1 window2 window3)
         count-increase-number)))

(println "Parte 2:" (count-increase-in-three-measurement-windows input))
