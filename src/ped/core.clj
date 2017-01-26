(ns ped.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn ok [body]
  {:status 200 :body body})

(defn greeting [name]
  (str "Hello, " name "!"))

(defn hello [request]
  (let [name (get-in request [:query-params :name] "Stranger")
        response (greeting name)]
    (ok response)))

(def routes
  (route/expand-routes
   #{["/greet" :get hello :route-name :greet]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type   :jetty
    ::http/port   8890}))

(defn start []
  (http/start (create-server)))
