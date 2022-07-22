(ns cljapi.component.handler
  (:require
   [cljapi.handler.api.greeting]
   [cljapi.handler.health]
   [cljapi.router :as router]
   [com.stuartsierra.component :as component]
   [reitit.ring :as ring]
   [ring.middleware.lint :as m.lint]
   [ring.middleware.reload :as m.reload]
   [ring.middleware.stacktrace :as m.stacktrace]))

(defn- build-handler
  [profile]
  (ring/ring-handler
   router/router
   nil
   {:middleware (if (= profile :prod)
                  []
                  [[m.reload/wrap-reload {:dirs ["src"]
                                          :reload-compile-errors? true}]
                   m.lint/wrap-lint
                   [m.stacktrace/wrap-stacktrace {:color? true}]])}))

(defrecord Handler [handler profile]
  component/Lifecycle
  (start [this]
    (assoc this :handler (build-handler profile)))
  (stop [this]
    (assoc this :handler nil)))
