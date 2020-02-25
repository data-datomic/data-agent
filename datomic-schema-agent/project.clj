(defproject datomic-schema-agent "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.datomic/datomic-pro "0.9.5981"]
                 [com.datomic/client-pro "0.8.28"]
                 [com.datomic/client-cloud "0.8.78"]
                 [me.raynes/fs "1.4.6"]
                 [cheshire "5.9.0"]
                 [buddy/buddy-core "1.6.0"]
                 [clj-time "0.15.2"]
                 [org.clojure/data.json "0.2.6"]]

  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :creds :gpg
                                   :username "<USERNAME>"
                                   :password "<PASSWORD>"}}

  :repl-options {:init-ns datomic-schema-agent.core})
