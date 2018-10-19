(ns flocktory.handlers.index)

(defn index-handler
  "index route handler"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "<h1>Greeting page</h1>"
              "<div>This is a test task for Flocktory.</div>"
              "<br/>"
              "<div>Main points:</div>"
              "<div>1. Send request to Bing Search Engine</div>"
              "<div>2. Process the response</div>"
              "<div>3. Receive statistics that based on the response</div>"
              "<h3>Wanna try?</h3>"
              "<div>click this link <a href=\"http://localhost:8080/search?query=haskell&query=scala&query=clojure&query=lisp&query=javascript\">http://localhost:8080/search?query=haskell&query=scala&query=clojure&query=lisp&query=javascript</a></div>"
              )}
  )


