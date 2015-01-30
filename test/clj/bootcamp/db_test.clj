(ns bootcamp.db-test
  (:require [midje.sweet :refer :all]
            [bootcamp.db :as db]))

(background (around :facts (do
                             (db/start!)
                             ?form
                             (db/close!))))

(fact
  (db/find-messages) => empty?)

(fact
  (db/insert-message! "jiihaa") => irrelevant
  (first (db/find-messages)) => (contains {:message "jiihaa"}))
