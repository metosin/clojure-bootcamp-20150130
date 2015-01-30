(ns bootcamp.main
  (:gen-class))

(defn -main [& args]
  (println "server starting...")
  (require 'bootcamp.server)
  ((resolve 'bootcamp.server/start!) args))
