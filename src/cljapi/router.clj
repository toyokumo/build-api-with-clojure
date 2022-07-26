(ns cljapi.router
  (:require
   [camel-snake-kebab.core :as csk]
   [cljapi.handler :as h]
   [clojure.core.memoize :as memo]
   [muuntaja.core :as muu]
   [muuntaja.middleware :as muu.middleware]
   [reitit.ring :as ring]
   [ring.middleware.defaults :as m.defautls]))

(def ^:private ring-defaults-config
  (-> m.defautls/api-defaults
      ;; ロードバランサーの後ろで動いていると想定して、
      ;; X-Forwarded-For と X-Forwarded-Proto に対応させる
      (assoc :proxy true)))

(def ^:private memoized->camelCaseString
  "実装上kebab-case keywordでやっているものをJSONにするときにcamelCaseにしたい。
   バリエーションはそれほどないはずなのでキャッシュする"
  (memo/lru csk/->camelCaseString {} :lru/threshold 1024))

(def ^:private muuntaja-config
  "https://cljdoc.org/d/metosin/muuntaja/0.6.8/doc/configuration"
  (-> muu/default-options
      ;; JSONにencodeする時にキーをcamelCaseにする
      (assoc-in [:formats "application/json" :encoder-opts]
                {:encode-key-fn memoized->camelCaseString})
      ;; JSON以外のacceptでリクエストされたときに返らないように制限する
      (update :formats #(select-keys % ["application/json"]))
      muu/create))

(def router
  (ring/router
   [["/health" {:name ::health
                :handler h/handler}]
    ["/api" {:middleware [[m.defautls/wrap-defaults ring-defaults-config]
                          [muu.middleware/wrap-format muuntaja-config]
                          muu.middleware/wrap-params]}
     ["/hello" {:name ::hello
                :handler h/handler}]
     ["/goodbye" {:name ::goodbye
                  :handler h/handler}]]]))

(comment
  (require '[reitit.core :as r])

  (r/routes router)
  (r/match-by-path router "/health")
  (r/match-by-path router "/api/hello"))
