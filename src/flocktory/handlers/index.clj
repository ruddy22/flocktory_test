(ns flocktory.handlers.index
  (:require [hiccup.page :refer (html5)]))

(defn index-handler
  "index route handler"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5 [:html
                 [:head
                  [:meta {:charset "utf-8"}]
                  [:title "Test task"]]
                 [:body
                  [:h1 "Greeting page"]
                  [:div "Test task for the Flocktory"]
                  [:br]
                  [:div "Main points:"]
                  [:div "1. Send request to Bing Search Engine"]
                  [:div "2. Process the response"]
                  [:div "3. Receive statistics that based on the response"]
                  [:h3 "Wanna try?"]
                  [:div [:a
                         {:href "http://localhost:8080/search?query=haskell&query=scala&query=clojure&query=lisp&query=javascript"}
                         "click me"]]]])})


