;; Set of Clojure programs for practice
(ns my-stuff.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn number-comment
  [x]
  (if (> x 6)
    "Oh My Gosh! What a big number!"
    "That number's ok, I guess"))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-leg" :size 3}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a sequence of maps having a :name and :value"
  [asym-body-parts]
  (loop [remaining-asym-body-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-body-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-body-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn better-symmetrize-body-parts
  "Using the reducer to make a symmetric body"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          [] asym-body-parts))

(defn titalize
  [topic]
  (str topic " for the Brave and True!"))

(defn stats
  [numbers]
  (map #(% numbers)
       [#(reduce + %) count #(/ (reduce + %) (count %))]))

(defn my-map
  [fn seq]
  (reduce #(conj %1 (fn %2)) [] seq))

(defn my-filter
  [fn? seq]
  (reduce #(if (fn? %2) (conj %1 %2) %1) [] seq))

(defn my-some
  [fn? seq]
  (reduce #(if (fn? %2) true) [] seq))
(def food-journal
  [{:month 1 :day 1 :foods ["apple" "pear" "chicken"]}
   {:month 1 :day 2 :foods ["apple" "pear" "chicken"]}
   {:month 1 :day 3 :foods ["apple" "pear" "chicken"]}
   {:month 2 :day 1 :foods ["apple" "pear" "chicken"]} 
   {:month 2 :day 2 :foods ["apple" "pear" "chicken"]}
   {:month 2 :day 3 :foods ["apple" "pear" "chicken"]}
   {:month 3 :day 1 :foods ["apple" "pear" "chicken"]}
   {:month 3 :day 2 :foods ["apple" "pear" "chicken"]}
   {:month 4 :day 1 :foods ["apple" "pear" "chicken"]}
   {:month 4 :day 2 :foods ["apple" "pear" "chicken"]}
   ])
(take-while #(< (count (:foods %) ) 2) food-journal)

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true :name "McMackson"}
   2 {:makes-blood-puns? true, :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true, :has-pulse? true :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  ;(Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record)) record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire? 
                 (map vampire-related-details social-security-numbers))))
(def not-vampire? (complement vampire?))
(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))

(def file-name "resources/suspects.csv")
(def vamp-keys [:name :glitter-index])
(defn str->int
  [str]
  (Integer. str))
(def conversions 
  {:name identity :glitter-index str->int})
(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\r\n")))
(defn mapify
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (map #(:name %) (filter #(>= (:glitter-index %) minimum-glitter) records)))

