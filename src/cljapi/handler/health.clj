(ns cljapi.handler.health
  (:require
   [ring.util.http-response :as res]))

(defn health
  "ヘルスチェックに対応するためのhandlerとして意図しています"
  [_]
  (res/ok "Application is runnig"))
