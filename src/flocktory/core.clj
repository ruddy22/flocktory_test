(ns flocktory.core
  (:require [org.httpkit.server :refer (run-server)]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.string :as s]
            [com.climate.claypoole :as cp]
            [clojure.set :as set]
            [feedparser-clj.core :as rss]
            [clojure.java.io :refer (as-url)]
            ))

(defn index-route-handler
  "index route handler"
  [req]
  "If you want to use this application, send request on `search` route.")

(defn get-url
  "get url"
  [query]
  (str "https://www.bing.com/search?q=" query "&format=rss&count=10"))

(defn subprocess
  "subprocess for each word"
  [word]
  (let [entries (-> word
                    get-url
                    rss/parse-feed ;; here is requesting data from Bing
                    :entries)
        links (map :link entries)]
    links))

(def pool-size 10)

(defn process
  "words processing"
  [words pool]
  (let [union (apply set/union (cp/pmap pool subprocess words))
        urls (map as-url union)
        domains (map #(.getHost %) urls)]
    (frequencies domains)))

(defn search-route-handler
  "search route handler"
  [req]
  (let [{:keys [query-string]} req
        pool (cp/threadpool pool-size)
        res (-> query-string
                (s/replace #"query=" "")
                (s/split #"(&)")
                (process pool))]
    (cp/shutdown pool)
    res
    ))

(defroutes app
  (GET "/" [req] #'index-route-handler)
  (GET "/search" [req] #'search-route-handler)
  (route/not-found "Route not found")
  (route/resources "/"))

(defonce server (atom nil))

(defn- stop-server
  "stop running server"
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(comment
  (stop-server)
  )

(defn- start-server
  "start new server instance"
  []
  (let [serv (run-server #'app {:port 8080})]
    (reset! server serv)))

(comment
  (start-server)
  )

(defn -main
  "run the application server."
  [& args]
  (start-server)
  (println "Server has been started on 8080"))
