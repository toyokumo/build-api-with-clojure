(ns cljapi.core
  (:require
   [cljapi.system :as system]))

(defn -main
  [& _args]
  (system/start))
