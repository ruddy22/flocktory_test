(ns flocktory.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.string :as s]
            ))

(defn index-route-handler
  "index route handler"
  []
  "If you want to use this application, send request on `search` route.")

(defn search-route-handler
  "search route handler"
  [req]
  (let [{:keys [query-string]} req]
    {:status 200
     :headers {"Content-Type" "text/html"} ;;"application/json"}
     :body (-> query-string
               (s/replace #"query=" "")
               (s/split #"(&)")
               str)
     }))

(defroutes app
  (GET "/" [] #'index-route-handler)
  (GET "/search" [req] #'search-route-handler)
  (route/not-found "Route not found")
  (route/resources "/"))

(defonce server (atom nil))

(defn stop-server
  "stop running server"
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(comment
  (stop-server)
  )

(defn start-server
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
