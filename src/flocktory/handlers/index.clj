(ns flocktory.handlers.index)

(defn index-handler
  "index route handler"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<h1>If you want to use this application, send request on `search` route.</h1>"} ;; TODO: add description with example
  )


