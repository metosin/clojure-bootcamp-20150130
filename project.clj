(defproject clojure-bootcamp-20150130 "0.1.0-SNAPSHOT"
  :description "Clojure Bootcamp: Affecto"
  
  :dependencies [[org.clojure/clojure "1.6.0"]]
  
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}
             :uberjar {:main bootcamp.main
                       :aot [bootcamp.main]
                       :uberjar-name "bootcamp.jar"}})
