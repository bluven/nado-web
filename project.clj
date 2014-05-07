(defproject
  nado-web
  "0.1.0-SNAPSHOT"
  :repl-options
  {:init-ns nado-web.repl}
  :dependencies
  [[ring-server "0.3.1"]
   [environ "0.5.0"]
   [ragtime "0.3.7"]
   [markdown-clj "0.9.43"]
   [com.taoensso/timbre "3.1.6"]
   [org.clojure/clojure "1.6.0"]
   [com.taoensso/tower "2.0.2"]
   [log4j
    "1.2.17"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]
   [mysql/mysql-connector-java "5.1.6"]
   [korma "0.3.1"]
   [compojure "1.1.6"]
   [selmer "0.6.6"]
   [lib-noir "0.8.2"]
   [clj-redis "0.0.12"]]
  :ring
  {:handler nado-web.handler/app,
   :init nado-web.handler/init,
   :destroy nado-web.handler/destroy}
  :ragtime
  {:migrations ragtime.sql.files/migrations,
   :database
   "jdbc:mysql://localhost:3306/nado_web?user=db_user_name_here&password=db_user_password_here"}
  :profiles
  {:uberjar {:aot :all},
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.2"]],
    :env {:dev true}}}
  :url
  "http://example.com/FIXME"
  :plugins
  [[lein-ring "0.8.10"]
   [lein-environ "0.5.0"]
   [ragtime/ragtime.lein "0.3.7"]]
  :description
  "FIXME: write description"
  :min-lein-version "2.0.0")
