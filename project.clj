(defproject clojure-bootcamp-20150130 "0.1.0-SNAPSHOT"
  :description "Clojure Bootcamp: Affecto"
  
  :dependencies [; Common:
                 [org.clojure/clojure "1.6.0"]
                 [prismatic/schema "0.3.5"]
                 [clj-time "0.9.0"]
                 [instaparse "1.3.5" :exclusions [org.clojure/clojure]]
                 [slingshot "0.12.1"]
                 [potemkin "0.3.11"]
                 
                 ; REST:
                 [http-kit "2.1.19"]
                 [ring/ring-core "1.3.2"]
                 [metosin/compojure-api "0.17.0" :exclusions [ring]]
                 [metosin/ring-swagger-ui "2.0.24"]
                 
                 ; DB:
                 [com.h2database/h2 "1.4.185"]
                 [hikari-cp "1.1.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [yesql "0.4.0"]
                 
                 ; Rabbit:
                 [com.novemberain/langohr "3.0.1"]
                 
                 ; Logging: use logback with slf4j, redirect JUL, JCL and Log4J:
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.2"]
                 [org.slf4j/slf4j-api "1.7.10"]
                 [org.slf4j/jul-to-slf4j "1.7.10"]        ; JUL to SLF4J
                 [org.slf4j/jcl-over-slf4j "1.7.10"]      ; JCL to SLF4J
                 [org.slf4j/log4j-over-slf4j "1.7.10"]]   ; Log4j to SLF4J
  
  :source-paths ["src/clj"]
  :profiles {:dev {:plugins [[lein-midje "3.1.3"]]
                   :dependencies [[midje "1.6.3"]
                                  [ring-mock "0.1.5"]]}
             
             :uberjar {:main bootcamp.main
                       :aot [bootcamp.main]
                       :uberjar-name "bootcamp.jar"}})
