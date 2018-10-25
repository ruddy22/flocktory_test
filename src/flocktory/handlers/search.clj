(ns flocktory.handlers.search
  (:require [flocktory.libs.threadpool :as threadpool]
            [flocktory.libs.data-handler :as dh]
            [flocktory.libs.rss :as rss]
            [flocktory.defaults :as default]
            [flocktory.libs.json :as json]))

(defn make-response
  [[status-code data]]
  {:status status-code
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (-> (case status-code
               500 {:error data}
               200 data)
             json/make-json
             json/prettify)})

(defn make-response-type
  [status-code data]
  (vector status-code data))

(def make-positive-response
  (partial make-response-type 200))

(def make-negative-response
  (partial make-response-type 500))

(defn do-stuff
  [incoming-params pool]
  (if-let [params (get incoming-params default/query-selector)]
    (try
      (-> (if (vector? params) params (vector params))
          (dh/data-handler pool rss/get-rss)
          (make-positive-response))
      (catch Exception e (make-negative-response (.getMessage e))))
    (make-negative-response "Wrong params")))

(defn search-handler
  "search route handler"
  [req]
  (let [{:keys [query-params]} req
        pool (threadpool/get-or-create-threadpool default/pool-size)
        res (do-stuff query-params pool)]
    (make-response res)))
