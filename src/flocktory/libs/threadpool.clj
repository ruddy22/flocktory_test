(ns flocktory.libs.threadpool
  (:require [com.climate.claypoole :as cp]))

(def default-pool-size 10)

(defn make-threadpool
  "create a threadpool"
  ([] (make-threadpool default-pool-size))
  ([size] (cp/threadpool size)))

(defn destroy-threadpool
  "destroy a threadpool"
  [pool]
  (cp/shutdown pool))

(defn do-in-thread
  "do something in parallel"
  [pool fn data]
  (cp/pmap pool fn data))

