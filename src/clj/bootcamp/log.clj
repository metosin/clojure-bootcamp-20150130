(ns bootcamp.log
  (:require [clojure.tools.logging :as log])
  (:import [java.util.logging LogManager Logger Level]
           [org.slf4j.bridge SLF4JBridgeHandler]))

; Activate JUL -> SLF4J bridge:
(.reset (LogManager/getLogManager))
(SLF4JBridgeHandler/install)
(.setLevel (Logger/getLogger "global") Level/INFO)

(comment
  (log/info "message from clojure.tools.logging")
  (.info (org.apache.log4j.Logger/getLogger "bootcamp.log") "message from Log4J")
  (.info (java.util.logging.Logger/getLogger "bootcamp.log") "message from JUL"))
