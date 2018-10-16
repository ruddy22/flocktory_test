(ns flocktory.core
  (:require [org.httpkit.server :refer [run-server]]))

  (def hello-http "Hello http!!!")

(defn app
  "test fn"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body hello-http})

(comment
  (app {})
  )

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
