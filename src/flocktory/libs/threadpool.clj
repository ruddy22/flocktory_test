(ns flocktory.libs.threadpool
  (:require [com.climate.claypoole :as cp]))

(def pool (atom nil))

(defn get-or-create-threadpool
  "
  int -> instance

  Returns thread pool executor instance if it exists or
  takes `size` and creates it with `size` processes.
  "
  [size]
  (if-let [pl @pool]
    pl
    (reset! pool (cp/threadpool size))))

(defn destroy-threadpool
  "
  instance -> nil

  Takes a `pool` and destroy it.
  "
  [pool]
  (cp/shutdown pool))

(defn do-in-thread
  "
  instance -> fn -> data -> *

  Takes `pool`, `fn`, `data` and parallelizes the execution of `fn` with `data`
  on the processes from the pool.
  "
  [pool fn data]
  (cp/pmap pool fn data))

