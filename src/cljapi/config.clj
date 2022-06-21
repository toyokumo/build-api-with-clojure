(ns cljapi.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]))

(defn read-config [profile]
  {:pre [(contains? #{:dev :prod :test} profile)]}
  (-> (io/resource "config.edn")
      (aero/read-config {:profile profile})
      (assoc :profile profile)))
