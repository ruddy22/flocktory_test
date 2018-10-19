(ns flocktory.main
  (:require [flocktory.server :as serv])
  (:gen-class))

(defn -main
  "Application entry point"
  [& args]
  (serv/start-server)
  )

