(defproject alpha_client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.2.374"]
                 [stylefruits/gniazdo "0.4.1"]
                 [clj-json "0.5.3"]]
  :main ^:skip-aot alpha-client.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
