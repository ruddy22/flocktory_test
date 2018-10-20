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

(defn query-string->words
  "clean and convert query-string to list of words"
  [query-string]
  (-> query-string
      (s/replace #"query=" "")
      (s/split #"&")))

(defn domains->json
  "perform frequencies calculation and convert result to pretty json"
  [domains]
  (-> domains
      frequencies
      make-json
      prettify))

(defn process-urls
  "get sld"
  [url-str]
  (try
    (let [host (-> url-str
                   as-url
                   (.getHost))
          sld (take 2 (reverse (s/split host #"\.")))]
      (s/join "." (reverse sld)))
    (catch java.lang.NullPointerException e
      (throw (Exception. "null pointer exception")))
    (catch java.net.MalformedURLException e
      (throw (Exception. (str (first (s/split (.getMessage e) #":"))))))
    (catch Exception e
      (throw (Exception. (.getMessage e))))))

(defn process
  "subprocess for each word"
  [get-feed word]
  (try
    (let [entries (-> word
                      make-url
                      get-feed
                      :entries)
          links (map :link entries)]
      links)
    (catch Exception e
      (throw (Exception. (.getMessage e))))))

(defn data-processing
  "words processing"
  [query-string pool get-feed]
  (let [words (query-string->words query-string)
        union (apply set/union
                     (tp/do-in-thread pool (partial process get-feed) words))
        domains (map process-urls union)
        json (domains->json domains)]
    json
    ))

(defn handle-data
  "handle data"
  [query-string pool get-feed]
  (data-processing query-string pool get-feed))
