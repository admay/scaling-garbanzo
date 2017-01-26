(defproject ped "0.1.0-SNAPSHOT"
  :description "Toy project for learning pedestal"
  :url "http://github.com/admay/scailing-garbanzo"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.pedestal/pedestal.service "0.5.2"]
                 [io.pedestal/pedestal.jetty "0.5.2"]
                 [io.pedestal/pedestal.route "0.5.2"]
                 [org.slf4j/slf4j-simple "1.7.21"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot ped.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
