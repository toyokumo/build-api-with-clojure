(ns cljapi.system
  (:require
   [cljapi.component.handler :as c.handler]
   [cljapi.component.server :as c.server]
   [cljapi.config :as config]
   [clojure.tools.logging :as log]
   [com.stuartsierra.component :as component]
   [unilog.config :as unilog]))

(defn- new-system [{:as config :keys [:profile]}]
  (component/system-map
   :handler (c.handler/map->Handler {:profile profile})
   :server (component/using
            (c.server/map->Jetty9Server (:server config))
            [:handler])))

(defn- init-logging! [config]
  (unilog/start-logging! (:logging config)))

(defn start [profile]
  (let [config (config/read-config profile)
        system (new-system config)
        _ (init-logging! config)
        _ (log/info "system is ready to start")
        started-system (component/start system)]
    (log/info "system is started")
    started-system))

(defn stop [system]
  (component/stop system))
