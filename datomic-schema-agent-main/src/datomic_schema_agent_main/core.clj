(ns datomic-schema-agent-main.core
  (:require [clojure.pprint :as pp]
            [clojure.tools.cli :refer [parse-opts]]

            [datomic.client.api :as d]
            [datomic-schema-agent.datomic :as datomic]
            [datomic-schema-agent.operations.capture-dataset.core :as capture]
            [datomic-schema-agent.config.agent-configuration-reader :as conf]
            [datomic-schema-agent.inner-core.captured-schema-reader :as csr]
            [datomic-schema-agent.connect.datomic-connector :as connect]
            [cheshire.core :as json])
  (:gen-class))

(defn -main
  [& args]
  (let [datomic-connector (datomic/make-datomic-connector "aws-dev-datomic-cloud")
        captured-schema (capture/capture-schema-instance datomic-connector)
        persistable-schema (capture/make-persistable-schema-instance datomic-connector captured-schema)]

    (pp/pprint (d/list-databases (datomic/client datomic-connector) {}))
    (spit "captured-schema.edn" persistable-schema)

    (prn "loading captured schema: ")
    (pp/pprint (csr/read-captured-schema-from-file "captured-schema.edn"))





    ; (prn "transactions found")
    ; (seq tx-ranges-for-db)
    ; (->> tx-ranges-for-db
    ;      (prn)
    ;      )


    ; (d/q '[:find [?tx ...]
    ;        :in ?log
    ;        :where [(tx-ids ?log 1000 1050) [?tx ...]]]
    ;      (d/log connector-connection))
    ;
    ; [:find [?tx ...]
    ;  :in ?log
    ;  :where [(tx-ids ?log 1000 1050) [?tx ...]]]


))
