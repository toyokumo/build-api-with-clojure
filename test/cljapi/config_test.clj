(ns cljapi.config-test
  (:require
   [cljapi.config :as sut]
   [clojure.test :refer [deftest testing is]]))

(deftest read-config-test
  (testing "usable profiles"
    (is (map? (sut/read-config :dev)))
    (is (map? (sut/read-config :test)))
    (is (map? (sut/read-config :prod)))
    (is (thrown? AssertionError (sut/read-config :hoge))))

  (testing "profile included"
    (is (= :dev
           (:profile (sut/read-config :dev))))
    (is (= :test
           (:profile (sut/read-config :test))))
    (is (= :prod
           (:profile (sut/read-config :prod))))))
