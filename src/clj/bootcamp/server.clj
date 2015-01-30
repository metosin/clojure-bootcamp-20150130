(ns bootcamp.server
  (:require [bootcamp.log]
            [clojure.tools.logging :refer [info]]))

(def sub-systems '[bootcamp.db
                   bootcamp.mq
                   bootcamp.business
                   bootcamp.rest])

(doseq [sub-system sub-systems]
  (info "Loading namespace" sub-system)
  (require sub-system))
(info "Server ready")

(defn invoke-for-all! [f-name sub-systems]
  (doseq [sub-system sub-systems]
    (let [f-sym (symbol (str sub-system) f-name)
          f     (resolve f-sym)]
      (info "calling" f-sym)
      (f))))

(defn start! []
  (info "server starting...")
  (invoke-for-all! "start!" sub-systems)
  (info "server ready"))

(defn close! []
  (info "server closing...")
  (invoke-for-all! "close!" (reverse sub-systems))
  (info "server closed"))
