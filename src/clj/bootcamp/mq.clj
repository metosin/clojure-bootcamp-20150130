(ns bootcamp.mq
  (:require [clojure.tools.logging :refer [info]]
            [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]))

;
; Init:
;

(def queue "testing.queue")
(def exchange "amq.direct")
(def routing-key "key1")

(def mq (atom nil))

(defn start! []
  (info "Starting MQ...")
  (let [conn  (rmq/connect)
        ch    (lch/open conn)]
    (info "connected to RabbitMQ, channel id" (.getChannelNumber ch))
    (lq/declare ch queue {:durable true :auto-delete false :exclusive false})
    (lq/bind ch queue exchange {:routing-key routing-key})
    (reset! mq {:conn conn
                :ch   ch})))

(defn close! []
  (when-let [conn (:conn @mq)]
    (.close conn)
    (reset! mq nil)))

;
; Public API:
;

(defn subscribe! [listener]
  (let [handler (fn [ch {:keys [delivery-tag]} ^bytes payload]
                  (listener (String. payload "UTF-8"))
                  (lb/ack ch delivery-tag))]
    (lc/subscribe (:ch @mq) queue handler {:auto-ack false})))

(defn publish! [message]
  (lb/publish (:ch @mq) exchange routing-key message {:content-type "text/plain"}))

(defn status []
  (lq/status (:ch @mq) queue))

;
; Test:
;

(comment
  (subscribe! listener)
  (publish! "jiihaa"))
