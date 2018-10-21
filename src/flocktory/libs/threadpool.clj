(ns flocktory.libs.threadpool
  (:require [com.climate.claypoole :as cp]))

(def default-pool-size 10)

(defn make-threadpool
  "
  int? -> instance

  Takes `size` (if specified) and creates a thread pool executor
  with `size` processes.
  "
  ([] (make-threadpool default-pool-size))
  ([size] (cp/threadpool size)))

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

