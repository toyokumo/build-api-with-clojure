(ns cljapi.router
  (:require
   [cljapi.handler :as h]
   [reitit.ring :as ring]
   [ring.middleware.defaults :as m.defautls]))

(def ^:private ring-defaults-config
  (-> m.defautls/api-defaults
      ;; ロードバランサーの後ろで動いていると想定して、
      ;; X-Forwarded-For と X-Forwarded-Proto に対応させる
      (assoc :proxy true)))

(def router
  (ring/router
   [["/health" {:name ::health
                :handler h/handler}]
    ["/api" {:middleware [[m.defautls/wrap-defaults ring-defaults-config]]}
     ["/hello" {:name ::hello
                :handler h/handler}]
     ["/goodbye" {:name ::goodbye
                  :handler h/handler}]]]))

(comment
  (require '[reitit.core :as r])

  (r/routes router)
  (r/match-by-path router "/health")
  (r/match-by-path router "/api/hello"))
