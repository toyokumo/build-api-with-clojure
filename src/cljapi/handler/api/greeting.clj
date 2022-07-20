(ns cljapi.handler.api.greeting
  (:require
   [ring.util.http-response :as res]))

(defn hello [_]
  (res/ok "Hello cljapi"))

(defn goodbye [_]
  (res/ok "Goodbye!"))
