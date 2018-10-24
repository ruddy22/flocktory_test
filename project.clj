(defproject flocktory "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  ;; I did that because -> https://www.deps.co/blog/how-to-upgrade-clojure-projects-to-use-java-9/
  :jvm-opts ["--add-modules" "java.xml.bind"]
  :main flocktory.main
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [http-kit "2.2.0"]
                 ;; use `claypoole` because -> https://www.youtube.com/watch?v=BzKjIk0vgzE
                 [com.climate/claypoole "1.1.4"]
                 [org.clojars.scsibug/feedparser-clj "0.4.0" :exlusions [org.clojure/clojure]]
                 [org.clojure/data.json "0.2.6"]
                 [hiccup "1.0.5"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-core "1.7.0"]])
