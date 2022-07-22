(ns cljapi.handler.health
  (:require
   [cljapi.handler :as h]
   [cljapi.router :as r]
   [ring.util.http-response :as res]))

(defmethod h/handler [::r/health :get]
  [_]
  (res/ok "Application is runnig"))
