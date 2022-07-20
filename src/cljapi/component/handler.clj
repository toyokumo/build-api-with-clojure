(ns cljapi.component.handler
  (:require
   [cljapi.router :as router]
   [com.stuartsierra.component :as component]
   [reitit.ring :as ring]))

(defn- build-handler
  []
  (ring/ring-handler router/router))

(defrecord Handler [handler]
  component/Lifecycle
  (start [this]
    (assoc this :handler (build-handler)))
  (stop [this]
    (assoc this :handler nil)))
