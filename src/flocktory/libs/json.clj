(ns flocktory.libs.json
  (:require [clojure.data.json :as json]
            [clojure.string :as s]
            ))

(defn prettify
  "indenting json on 1st level"
  [json-data]
  (-> json-data
      (s/replace #"\{" "{\n\t")
      (s/replace #"\}" "\n}\n")
      (s/replace #"\," ",\n\t")
      ))

(defn make-json
  "convert clj data to json"
  [data]
  (json/write-str data))

