(ns bootcamp.db
  (:require [clojure.tools.logging :refer [info]]
            [hikari-cp.core :as hikari]
            [clojure.java.jdbc :as jdbc]
            [yesql.core :refer [defqueries]]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]))

;
; Init:
;

(def datasource-config {:adapter      "h2"
                        :url          "jdbc:h2:mem:bootcamp;DB_CLOSE_DELAY=-1"
                        :auto-commit  false})

(def db (atom nil))

(defqueries "sql/queries.sql")

(defn connect! []
  (let [datasource (hikari/make-datasource datasource-config)
        db-spec    {:datasource datasource}]
    (reset! db db-spec)))

(defn init-db! []
  (jdbc/db-do-commands @db
    (jdbc/create-table-ddl :messages
                           [:id       "varchar(32) primary key"]
                           [:ts       "timestamp not null"]
                           [:message  "varchar(128) not null"])
    "create sequence id_seq cache 100"))

(defn nuke-db! []
  (jdbc/db-do-commands @db
    "drop all objects"))

(defn start! []
  (when-not @db
    (info "Starting H2 DB...")
    (connect!)
    (init-db!)))

(defn close! []
  (when-let [db-spec @db]
    (nuke-db!)
    (.close (:datasource db-spec))
    (reset! db nil)))

(defn ts->datetime [{ts :ts :as record}]
  (assoc record :ts (tc/from-sql-time ts)))



;
; API:
;

(defn find-messages []
  (map ts->datetime (db-find-messages @db)))

(defn find-messages-since [since]
  (map ts->datetime (db-find-messages-since @db (tc/to-timestamp since))))

(defn insert-message! [message]
  (jdbc/with-db-transaction [tx @db]
    (let [id (-> (db-query-next-id tx) first :id str)
          ts (tc/to-timestamp (t/now))]
      (db-insert-message! tx id ts message)
      id)))

;
; Test:
;

(comment
  (insert-message! "foo")
  (insert-message! "bar")
  (find-messages))
