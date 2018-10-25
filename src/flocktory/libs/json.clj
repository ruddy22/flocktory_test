(ns flocktory.libs.json
  (:require [cheshire.core :as json]))

(defn make-json
  "
  (hash-map) -> (json string)

  Converts data to json.
  "
  [data]
  (json/generate-string data {:pretty true}))

