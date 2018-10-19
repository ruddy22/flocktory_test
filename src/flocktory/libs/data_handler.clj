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

(defn subprocess
  "subprocess for each word"
  [get-feed word]
  (let [entries (-> word
                    make-url
                    get-feed
                    :entries)
        links (map :link entries)]
    links))

(defn process
  "words processing"
  [words pool get-feed]
  (let [union (apply set/union
                     (tp/do-in-thread pool (partial subprocess get-feed) words))
        domains (map (comp (memfn getHost) as-url) union)
        json (prettify (make-json (frequencies domains)))]
    json
    ))

(defn handle-data
  "handle data"
  [query-string pool get-feed]
  (-> query-string
      (s/replace #"query=" "")
      (s/split #"(&)")
      (process pool get-feed)))
