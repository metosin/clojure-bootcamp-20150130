(ns bootcamp.business
  (:require [clojure.tools.logging :refer [info]]
            [bootcamp.db :as db]
            [bootcamp.mq :as mq]))

(defn message-listener [message]
  (info "incoming message:" message)
  (db/insert-message! message))

(defn start! []
  (info "Starting business...")
  (mq/subscribe! message-listener))

(defn close! []
  )