(ns advent-of-code-2021.day-2.solution
  (:require [clojure.string :as str]))

; Input
(def input
  (->> (slurp "resources/day-2-input.txt")
       str/split-lines
       (map #(str/split % #" "))
       (map #(vector (keyword (first %)) (Integer/parseInt (second %))))))

; Part 1
(defn calc-final-position
  [input-data]
  (loop [data input-data
         total 0
         depth 0]
    (let [[key value] (first data)
          new-data (rest data)]
      (cond
        (empty? data) {:total  total
                       :depth  depth
                       :result (* total depth)}
        (= key :forward) (recur new-data (+ value total) depth)
        (= key :down) (recur new-data total (+ depth value))
        (= key :up) (recur new-data total (- depth value))))))

(println "Result part 1:" (calc-final-position input))

; Part 2
(defn calc-final-position-with-aim
  [input-data]
  (loop [data input-data
         total 0
         aim 0
         depth 0]
    (let [[key value] (first data)
          new-data (rest data)]
      (cond
        (empty? data) {:total  total
                       :aim    aim
                       :depth  depth
                       :result (* total depth)}
        (= key :forward) (recur new-data (+ value total) aim (+ depth (* value aim)))
        (= key :down) (recur new-data total (+ aim value) depth)
        (= key :up) (recur new-data total (- aim value) depth)))))

(println "Result part 2:" (calc-final-position-with-aim input))
