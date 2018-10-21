(ns flocktory.server
  (:require [org.httpkit.server :refer (run-server)]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [flocktory.handlers.index :refer (index-handler)]
            [flocktory.handlers.search :refer (search-handler)]
            ))

(defonce server (atom nil))

(defroutes app
  (GET "/" [req] index-handler)
  (GET "/search" [req] search-handler)
  (route/not-found "Route not found")
  (route/resources "/"))

(defn- stop-server
  "stop running server"
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn start-server
  "start new server instance"
  ([] (start-server 8080))
  ([port]
   (let [serv (run-server #'app {:port port})]
     (reset! server serv)
     (println (str "Server have been started on " port)))))

(comment
  (start-server)
  (stop-server)
  )
