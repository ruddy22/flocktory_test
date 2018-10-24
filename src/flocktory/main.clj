(ns flocktory.main
  (:require [flocktory.server :as server])
  (:gen-class))

(defn -main
  "Application entry point"
  [& args]
  (server/start-server))

