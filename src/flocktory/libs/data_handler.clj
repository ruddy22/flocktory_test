(ns flocktory.libs.data-handler
  (:require [clojure.set :as set]
            [clojure.string :as s]
            [clojure.java.io :as io]
            [flocktory.libs.threadpool :as tp]))

(defn make-url
  "
  string -> string

  Takes a `word` and makes url-string for request rss feed.
  "
  [word]
  (str "https://www.bing.com/search?q=" word "&format=rss&count=10"))

(defn process-urls
  "
  string -> string || exception

  Takes a `url` string and convert it to sld name.
  "
  [url]
  (try
    (let [host (-> url
                   io/as-url
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
  "
  fn -> string -> (vec string) || exception

  Word processor

  Takes a `get-feed` fn (receiver and parser of rss) and a `word` string
  makes url-string from params, sends it to the remote server,
  receive and parse incoming data.

  After that convert data to list of domains.
  "
  [get-feed word]
  (try
    (let [entries (-> word
                      make-url
                      get-feed
                      :entries)
          links (map :link entries)]
      (vec links))
    (catch Exception e
      (throw (Exception. (.getMessage e))))))

(defn data-handler
  "
  string -> instance -> fn -> (hash-map)

  Major processing function.

  Takes a `query-string`, `pool`(thread pool executor), `get-feed`
  and uses them to receive and process data from a external service
  "
  [words pool get-feed]
  (let [union (apply set/union
                     (tp/do-in-thread pool (partial process get-feed) words))
        domains (-> (map process-urls union)
                    frequencies)]
    domains))
