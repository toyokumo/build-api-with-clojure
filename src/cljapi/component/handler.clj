(ns cljapi.component.handler
  (:require
   [cljapi.handler.api.greeting]
   [cljapi.handler.health]
   [cljapi.router :as router]
   [com.stuartsierra.component :as component]
   [reitit.ring :as ring]
   [ring.logger :as m.logger]
   [ring.middleware.lint :as m.lint]
   [ring.middleware.reload :as m.reload]
   [ring.middleware.stacktrace :as m.stacktrace]))

(def ^:private dev-middlewares
  "開発時だけ有効化する"
  [[m.reload/wrap-reload {:dirs ["src"]
                          :reload-compile-errors? true}]
   m.lint/wrap-lint
   [m.stacktrace/wrap-stacktrace {:color? true}]])

(defn- build-handler
  [profile]
  (let [common-middlewares [m.logger/wrap-with-logger]
        middlewares (if (= profile :prod)
                      common-middlewares
                      (apply conj dev-middlewares common-middlewares))]
    (ring/ring-handler
     router/router
     nil
     {:middleware middlewares})))

(defrecord Handler [handler profile]
  component/Lifecycle
  (start [this]
    (assoc this :handler (build-handler profile)))
  (stop [this]
    (assoc this :handler nil)))
