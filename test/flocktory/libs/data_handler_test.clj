(ns flocktory.libs.data-handler-test
  (:require [clojure.test :refer :all]
            [flocktory.libs.data-handler :as dh]))

(deftest make-url-test
  (testing "put query get url-string"
    (is (= "https://www.bing.com/search?q=clojure&format=rss&count=10"
           (dh/make-url "clojure")))))

(deftest process-urls-test
  (testing "string -> url -> sld"
    (is (= '("wiki.com" "wiki.com" "wiki.com")
           (map dh/process-urls (list "http://en.wiki.com" "https://es.us.wiki.com" "http://ru-ru.wiki.com")))))
  (testing "exceptions"
    (testing "Unknown exception"
      (is (thrown? Exception (dh/process-urls nil)))
      (is (thrown-with-msg? Exception #"Unknown error" (dh/process-urls nil)))
      )))

(defn fake-get-feed [_]
  {:entries (list {:link "1"}
                  {:link "2"}
                  {:link "3"})
   :author nil
   :copyright nil}
  )

(deftest process-test
  (testing "get list of links"
    (is (= '("1" "2" "3")
           (dh/process fake-get-feed "someword")))))
