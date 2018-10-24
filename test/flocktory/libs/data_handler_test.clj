(ns flocktory.libs.data-handler-test
  (:require [clojure.test :refer :all]
            [flocktory.libs.data-handler :as dh]))

(deftest make-url-test
  (testing "put query get url-string"
    (is (= "https://www.bing.com/search?q=clojure&format=rss&count=10"
           (dh/make-url "clojure")))))

(deftest query-string->words-test
  (testing "clean and convert query-string to list of strings"
    (is (= ["clojure" "haskell"]
           (dh/query-string->words "query=clojure&query=haskell")))))

(deftest domains->json-test
  (testing "perform frequencies calculation and convert result to pretty json"
    (is (= "{\n\t\"clojure.org\":2\n}\n"
           (dh/domains->json '("clojure.org" "clojure.org"))))))

(deftest process-urls-test
  (testing "get sld"
    (is (= ["wiki.com" "wiki.com" "wiki.com"]
           (map dh/process-urls (list "http://en.wiki.com" "https://es.us.wiki.com" "http://ru-ru.wiki.com")))))
  (testing "get exceptions"
    (testing "Null Pointer Exception"
      (is (thrown? java.lang.Exception (dh/process-urls nil)))
      (is (thrown-with-msg? java.lang.Exception #"null pointer exception" (dh/process-urls nil))))
    (testing "Wrong Protocol"
      (is (thrown? java.lang.Exception (dh/process-urls "wap://en.wiki.com")))
      (is (thrown-with-msg? java.lang.Exception #"unknown protocol" (dh/process-urls "wap://en.wiki.com"))))
    (testing "No Protocol"
      (is (thrown? java.lang.Exception (dh/process-urls "en.wiki.com")))
      (is (thrown-with-msg? java.lang.Exception #"no protocol" (dh/process-urls "en.wiki.com"))))
    ))

(defn fake-get-feed [_]
  {:entries (list {:link "1"}
                  {:link "2"}
                  {:link "3"})
   :author nil
   :copyright nil}
  )

(deftest process-test
  (testing "get list of links"
    (is (= ["1" "2" "3"]
           (dh/process fake-get-feed "someword")))))
