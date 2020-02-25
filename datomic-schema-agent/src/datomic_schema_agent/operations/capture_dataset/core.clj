(ns datomic-schema-agent.operations.capture-dataset.core
  [:require [clojure.pprint :as pp]
            [clj-time.local :as l]
            [datomic.client.api :as d]
            [datomic-schema-agent.datomic :as datomic]
            [buddy.core.hash :as hash]
            [buddy.core.codecs :as cdx]])

(defn make-persistable-schema-instance [datomic-connector schema-instance]
  (swap! schema-instance assoc :connection-name (:name (datomic/config datomic-connector)))
  (swap! schema-instance assoc :connection-configuration (:datomic-connection-configuration (datomic/config datomic-connector)))
  (swap! schema-instance assoc :connection-attributes (datomic/connection datomic-connector))
  (swap! schema-instance assoc :operation-timestamp (l/local-now))
  (let [schema-instance-str (with-out-str (pp/pprint @schema-instance))
        sha256-hash-bytes (cdx/bytes->hex (hash/sha256 schema-instance-str))]
    (with-out-str (pp/pprint {:captured-schema-sha256-hash sha256-hash-bytes :captured-schema @schema-instance}))))

(defn capture-schema-instance [connector-connection]
  (let [conn (datomic/connection connector-connection)
        db (d/db conn)
        txs-for-db (seq (d/tx-range conn {}))
        eavt-datoms (seq (d/datoms db {:index :eavt}))
        aevt-datoms (seq (d/datoms db {:index :aevt}))
        avet-datoms (seq (d/datoms db {:index :avet}))
        vaet-datoms (seq (d/datoms db {:index :vaet}))
        history (d/history db)
        db-stats (d/db-stats db)
        schema-instance (atom {})]
    (swap! schema-instance assoc :txs txs-for-db)
    (swap! schema-instance assoc :eavt-datoms eavt-datoms)
    (swap! schema-instance assoc :aevt-datoms aevt-datoms)
    (swap! schema-instance assoc :avet-datoms avet-datoms)
    (swap! schema-instance assoc :vaet-datoms vaet-datoms)
    (swap! schema-instance assoc :history history)
    (swap! schema-instance assoc :db-stats db-stats)
    schema-instance))
