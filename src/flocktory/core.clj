(ns flocktory.core
  (:require [org.httpkit.server :refer (run-server)]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.string :as s]
            [com.climate.claypoole :as cp]
            [clojure.set :as set]
            [feedparser-clj.core :as rss]
            [clojure.java.io :refer (as-url)]
            [clojure.data.json :as json]
            ))

(defn index-route-handler
  "index route handler"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<h1>If you want to use this application, send request on `search` route.</h1>"} ;; TODO: add description with example
  )

(defn get-url
  "get url"
  [query]
  (str "https://www.bing.com/search?q=" query "&format=rss&count=10"))

(defn subprocess
  "subprocess for each word"
  [word]
  (let [entries (-> word
                    get-url
                    ;; TODO: add `rss/parse-feed` as fn param for tests
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
        domains (map #(.getHost %) urls)] ;; TODO: use `memfn` insead of lambda-fn
    (frequencies domains)))

(defn prettify-json
  "indenting json on 1st level"
  [json-data]
  (-> json-data
      (s/replace #"\{" "{\n\t")
      (s/replace #"\}" "\n}\n")
      (s/replace #"\," ",\n\t")
      )
  )

(defn search-route-handler
  "search route handler"
  [req]
  (let [{:keys [query-string]} req
        pool (cp/threadpool pool-size)
        processed-data (-> query-string
                           (s/replace #"query=" "")
                           (s/split #"(&)")
                           (process pool))
        json-data (json/write-str processed-data)
        pp-json (prettify-json json-data)
        response {:status 200
                  :headers {"Content-Type" "application/json"}
                  :body pp-json}]
    (cp/shutdown pool)
    response
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

(defn- start-server
  "start new server instance"
  []
  (let [serv (run-server #'app {:port 8080})]
    (reset! server serv)))

(comment
  (start-server)
  (stop-server)
  )

(defn -main
  "run the application server."
  [& args]
  (start-server)
  (println "Server has been started on 8080"))
