(ns flocktory.server
  (:require [org.httpkit.server :as http-serv]
            [ring.middleware.params :as middleware]
            [compojure.core :as cc]
            [compojure.route :as route]
            [flocktory.libs.threadpool :as threadpool]
            [flocktory.handlers.index :refer (index-handler)]
            [flocktory.handlers.search :refer (search-handler)]))

(defonce server (atom nil))

(def default-pool-size 10)

(cc/defroutes routes
  (cc/GET "/" req index-handler)
  (cc/GET "/search" req search-handler)
  (route/not-found "Route not found")
  (route/resources "/"))

(def app
  (-> routes
      middleware/wrap-params))

(defn- stop-server
  "stop running server"
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)
    (let [pool threadpool/get-or-create-threadpool]
      (threadpool/destroy-threadpool pool))))

(defn start-server
  "start new server instance"
  ([] (start-server 8080))
  ([port]
   (let [serv (http-serv/run-server app {:port port})
         _ (threadpool/get-or-create-threadpool default-pool-size)] ;; create threadpool in the module
     (reset! server serv)
     (println (str "Server have been started on " port)))))

(comment
  (start-server)
  (stop-server)
  )
