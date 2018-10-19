(ns flocktory.handlers.search
  (:require [flocktory.libs.threadpool :refer (make-threadpool destroy-threadpool)]
            [flocktory.libs.data-handler :refer (handle-data)]
            [flocktory.libs.rss :refer (get-rss)]
            ))

(defn search-handler
  "search route handler"
  [req]
  (let [{:keys [query-string]} req
        pool (make-threadpool)
        res (handle-data query-string pool get-rss)
        response {:status 200
                  :headers {"Content-Type" "application/json"}
                  :body res}]
    (destroy-threadpool pool)
    response
    ))
