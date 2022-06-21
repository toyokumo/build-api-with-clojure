(ns cljapi.system
  (:require
   [cljapi.component.handler :as c.handler]
   [cljapi.component.server :as c.server]
   [cljapi.config :as config]
   [com.stuartsierra.component :as component]))

(defn- new-system [config]
  (component/system-map
   :handler (c.handler/map->Handler {})
   :server (component/using
            (c.server/map->Jetty9Server (:server config))
            [:handler])))

(defn start [profile]
  (let [config (config/read-config profile)
        system (new-system config)]
    (component/start system)))

(defn stop [system]
  (component/stop system))
