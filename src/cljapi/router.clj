(ns cljapi.router
  (:require
   [cljapi.handler :as h]
   [reitit.ring :as ring]))

(def router
  (ring/router
   [["/health" {:name ::health
                :handler h/handler}]
    ["/api"
     ["/hello" {:name ::hello
                :handler h/handler}]
     ["/goodbye" {:name ::goodbye
                  :handler h/handler}]]]))

(comment
  (require '[reitit.core :as r])

  (r/routes router)
  (r/match-by-path router "/health")
  (r/match-by-path router "/api/hello"))
