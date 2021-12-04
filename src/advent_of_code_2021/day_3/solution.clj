(ns advent-of-code-2021.day-3.solution
  (:require [clojure.string :as str]))

; Input
(def input
  (->> (slurp "resources/day-3-input.txt")
       str/split-lines
       (map #(str/split % #""))))

(def input-length (count input))
(def half-input-length (/ input-length 2))

; Part 1
(defn count-1's-in-column
  [column]
  (->> (filter #(= % "1") column)
       (map #(Integer/parseInt %))
       (reduce +)))

(defn over?
  [element
   data-length]
  (>= element data-length))

(defn over-half_data-length?
  [element
   data-length]
  (if (over? element data-length)
    ["1" "0"]
    ["0" "1"]))

(defn calc-columns-values
  [data-input]
  (->> (apply map vector data-input)
       (mapv count-1's-in-column)))

(defn calc-gamma-and-epsilon-rate
  [data-input]
  (->> (calc-columns-values data-input)
       (map #(over-half_data-length? % half-input-length))
       (apply map vector)
       (map #(Integer/parseInt (str/join "" %) 2))
       (reduce *)))

(println "Result of part 1:" (calc-gamma-and-epsilon-rate input))

; Part 2
(defn calc-rate-of
  [data-input
   choose-position]
  (let [default-bit-length (count (first data-input))]
    (loop [current-number ""
           data data-input
           index 0]
      (if (= (count data) 1)
        (-> (if (= default-bit-length (count current-number))
              current-number
              (str/join "" (first data)))
            (Integer/parseInt 2))
        (let [half-data-length (/ (count data) 2)
              current-value-of-index (-> (get (calc-columns-values data) index)
                                         (over-half_data-length? half-data-length)
                                         choose-position)
              new-data (filter #(= current-value-of-index (get % index)) data)
              new-number (str current-number current-value-of-index)]
          (recur new-number new-data (inc index)))))))

(defn calc-oxygen-rate
  [data-input]
  (calc-rate-of data-input first))

(defn calc-co2-rate
  [data-input]
  (calc-rate-of data-input second))

(defn calc-oxygen-and-co2-rate
  [data-input]
  (let [oxygen-rate (calc-oxygen-rate data-input)
        co2-rate (calc-co2-rate data-input)]
    (* oxygen-rate co2-rate)))

(println "Result of part 2:" (calc-oxygen-and-co2-rate input))
