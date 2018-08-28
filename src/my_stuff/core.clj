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