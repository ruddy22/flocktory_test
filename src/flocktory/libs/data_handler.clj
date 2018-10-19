(ns flocktory.libs.data-handler
  (:require [clojure.set :as set]
            [clojure.string :as s]
            [clojure.java.io :refer (as-url)]
            [flocktory.libs.threadpool :as tp]
            [flocktory.libs.json :refer (make-json prettify)]
            ))

(defn make-url
  "make url for request"
  [query]
  (str "https://www.bing.com/search?q=" query "&format=rss&count=10"))

(defn process
  "subprocess for each word"
  [get-feed word]
  (let [entries (-> word
                    make-url
                    get-feed
                    :entries)
        links (map :link entries)]
    links))

(defn data-processing
  "words processing"
  [query-string pool get-feed]
  (let [words (-> query-string
                  (s/replace #"query=" "")
                  (s/split #"(&)"))
        union (apply set/union
                     (tp/do-in-thread pool (partial process get-feed) words))
        domains (map (comp (memfn getHost) as-url) union)
        json (-> domains
                 frequencies
                 make-json
                 prettify)]
    json
    ))

(defn handle-data
  "handle data"
  [query-string pool get-feed]
  (data-processing query-string pool get-feed))
