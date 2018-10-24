(ns flocktory.handlers.search
  (:require [flocktory.libs.threadpool :as threadpool]
            [flocktory.libs.data-handler :as dh]
            [flocktory.libs.rss :as rss]
            [flocktory.libs.json :as json]))

(defn make-pos-response
  "positive response"
  [data]
  {:status 200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body data})

(defn handle-error
  "negative response"
  [error]
  (let [error-str (.getMessage error)
        json (-> {:error error-str}
                 json/make-json
                 json/prettify)]
    {:status 500
     :headers {"Content-Type" "application/json; charset=utf-8"}
     :body json}))

(defn search-handler
  "search route handler"
  [req]
  (let [{:keys [query-params]} req
        pool (threadpool/get-or-create-threadpool 10)]
    (if-let [words (get query-params "query")]
      (try
        (-> words
            (dh/data-handler pool rss/get-rss)
            make-pos-response)
        (catch Exception e (handle-error e)))
      (handle-error (Exception. "Wrong params")))))
