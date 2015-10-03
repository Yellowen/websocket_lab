(defproject websocketclj_lab "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.immutant/web "2.1.0"]
                  [compojure "1.4.0"]
                  [ring/ring-core "1.4.0"]
                  [environ "1.0.1"]]
  :main websocketclj_lab.core
  :uberjar-name "websocketclj_lab.jar"
  :profiles {:uberjar {:aot [websocketclj_lab.core]}}
  :min-lein-version "2.5.3")
