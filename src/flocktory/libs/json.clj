(ns flocktory.libs.json
  (:require [clojure.data.json :as json]
            [clojure.string :as s]
            ))

(defn prettify
  "
  (JSON string) -> (JSON string)

  Makes json data much prettier.
  Indents each string in json data.
  "
  [json-data]
  (-> json-data
      (s/replace #"\{" "{\n\t")
      (s/replace #"\}" "\n}\n")
      (s/replace #"\," ",\n\t")
      ))

(defn make-json
  "
  (hash-map) -> (json string)

  Converts data to json.
  "
  [data]
  (json/write-str data))

