(ns flocktory.handlers.search
  (:require [flocktory.libs.threadpool :refer (make-threadpool destroy-threadpool)]
            [flocktory.libs.data-handler :refer (handle-data)]
            [flocktory.libs.rss :refer (get-rss)]
            [flocktory.libs.json :refer (make-json prettify)]
            ))

(defn make-pos-response
  "positive response"
  [data]
  {:status 200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body data})

(defn handle-error
  "negative response"
  [error]
  (let [error-str (.toString error)
        json (-> {:error error-str}
                 make-json
                 prettify)]
    {:status 500
     :headers {"Content-Type" "application/json; charset=utf-8"}
     :body json}
    ))

(defn search-handler
  "search route handler"
  [req]
  (let [{:keys [query-string]} req
        pool (make-threadpool)]
    (try
      (-> query-string
          (handle-data pool get-rss)
          make-pos-response)
      (catch Exception e (handle-error e))
      (finally (destroy-threadpool pool)))
    ))
