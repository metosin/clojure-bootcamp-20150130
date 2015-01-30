(ns bootcamp.rest
  (:require [clojure.tools.logging :refer [info]]
            [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [compojure.route :refer [resources]]
            [org.httpkit.server :as http-kit]
            [schema.core :as sc]
            [bootcamp.db :as db]
            [bootcamp.mq :as mq]))

;
; REST API:
;

(defapi api
  (swagger-ui "/docs")
  (swagger-docs
    :title "Bootcamp example API"
    :description "Using RabbitMQ, SQL, and REST")
  (swaggered "messages" :description "REST API for messages"
    (context "/api" []
      (GET* "/ping" []
        (ok {:ping "pong"})))
    (context "/api/message" []
      (GET* "/" []
        :summary "Retrieve all messages"
        :return {:messages [{:message String
                             :ts org.joda.time.DateTime
                             :id String}]}
        (ok {:messages (db/find-messages)}))
      (POST* "/" []
        :summary "Append new message to MQ"
        :body-params [message :- String]
        (do
          (mq/publish! message)
          (ok {:message (format "published message: [%s]" message)}))))))

(def server (atom nil))

;
; HTTP server:
;

(defn start! []
  (info "Starting HTTP server...")
  (reset! server (http-kit/run-server #'api {:port 8080})))

(defn close! []
  (when-let [s @server]
    (s)
    (reset! server nil)))
