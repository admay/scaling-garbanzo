(ns ped.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [clojure.data.json :as json]
            [io.pedestal.http.content-negotiation :as conneg]))

(def supported-types ["text/html" "application/odm" "application/json" "text/plain"])

(def content-neg-intc (conneg/negotiate-content supported-types))

(defn ok [body]
  {:status 200 :body body})

(def coerce-body
  {:name ::coerce-body
   :leave
   (fn [context]
     (let [accepted (get-in context [:request :accept :field] "text/plain")
           response (context :response)
           body (context :body)
           coerced-body (case accepted
                          "text/html" body
                          "text/plain" body
                          "application/edn" (pr-str body)
                          "application/json" (json/write-str body))
           updated-response (assoc response
                                   :headers {"Content-Type" accepted}
                                   :body coerced-body)]
       (assoc context :response updated-response)))})

(defn greeting [name]
  (str "Hello, " name "!"))

(defn hello [request]
  (let [name (get-in request [:query-params :name] "Stranger")
        response (greeting name)]
    (ok response)))

(def echo
  {:name ::echo
   :enter (fn [context]
            (let [request (context :request)
                  response (ok request)]
              (assoc context :response response)))})

(def routes
  (route/expand-routes
   #{["/greet" :get [coerce-body content-neg-intc hello] :route-name :greet]
     ["/echo" :get echo]}))


(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type   :jetty
    ::http/port   8080}))

(defn start []
  (http/start (create-server)))
