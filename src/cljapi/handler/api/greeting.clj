(ns cljapi.handler.api.greeting
  (:require
   [cljapi.handler :as h]
   [cljapi.router :as r]
   [ring.util.http-response :as res]))

(defmethod h/handler [::r/hello :get]
  [_]
  (res/ok "Hello cljapi!!"))

(defmethod h/handler [::r/goodbye :get]
  [_]
  (res/ok "Goodbye!"))
