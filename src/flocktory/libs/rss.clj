(ns flocktory.libs.rss
  (:require [feedparser-clj.core :as rss]))

(defn get-rss
  "Get RSS-feed and parse it"
  [path]
  (rss/parse-feed path))

