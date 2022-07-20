(ns cljapi.router
  (:require
   [cljapi.handler.api.greeting :as api.greeting]
   [cljapi.handler.health :as health]
   [reitit.ring :as ring]))

(def router
  (ring/router
   [["/health" health/health]
    ["/api"
     ["/hello" api.greeting/hello]
     ["/goodbye" api.greeting/goodbye]]]))
