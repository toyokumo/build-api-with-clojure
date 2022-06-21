(ns user
  (:require
   [cljapi.system :as system]))

(defonce system (atom nil))

(defn start []
  (reset! system (system/start :dev)))

(defn stop []
  (when @system
    (reset! system (system/stop @system))))

(defn go []
  (stop)
  (start))
