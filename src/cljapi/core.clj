(ns cljapi.core
  (:require
   [ring.adapter.jetty9 :as jetty]))

(defn ring-handler
  [_req]
  {:status 200
   :body "Hello, Clojure API"})

(defn -main
  [& _args]
  (jetty/run-jetty ring-handler {:port 8000}))

(comment
  (-main)
  )
