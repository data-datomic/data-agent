(ns datomic-schema-agent.inner-core.datomic-schema-model

  )

; (->WereWolf "Jacob" "Lead Shirt Discarder")


; (defrecord KeyFieldMap [[key field]])
; ; (defrecord field-key-map [key field])
;
; (defprotocol KeyFields
;   (key-field-map [this])

(defprotocol datomic.PDatom
  "Our Protocol-Interface for capturing an easy and reliable representation of the Datomic Datom.

  **From Datomic**

  Datoms are associative and indexed:
  Key     Index        Value
  --------------------------
  :e      0            entity id
  :a      1            attribute id
  :v      2            value
  :tx     3            transaction id
  :added  4            boolean add/retract"
  ^{:sk :e :idx 0}
  (entity-id [this])
  ^{:sk :a :idx 1}
  (attribute-id [this])
  ^{:sk :v :idx 2}
  (value [this])
  ^{:sk :tx :idx 3}
  (transaction-id [this])
  ^{:sk :added :idx 4}
  (added? [this]))

(deftype datomic.Datom [entity-id attribute-id value transaction-id added?]
  datomic.PDatom
  (entity-id [x] entity-id)
  (attribute-id [x] attribute-id)
  (value [x] value)
  (transaction-id [x] transaction-id)
  (added? [x] added?))
  ; KeyFields
  ; (key-field-map [x])

(defprotocol datomic.PDatomicTransaction
  "Represents a single Datomic Transaction instance within the collection of transactions throughout the schema."
  ^{:sk :t :idx 0}
  (transaction-number [this])
  ^{:sk :data :idx 1}
  (datoms [this]))

(deftype datomic.DatomicTransaction [transaction-number datoms]
  datomic.PDatomicTransaction
  (transaction-number [x] transaction-number)
  (datoms [x] datoms))

(defprotocol datomic.PDatomicSchemaInstance
  ^{:sk :operation-timestamp :idx 0}
  (operation-timestamp [x])
  ^{:sk :connection-name :idx 1}
  (connection-name [x])
  ^{:sk :connection-configuration :idx 2}
  (connection-configuration [x])
  ^{:sk :connection-attributes :idx 3}
  (connection-attributes [x])
  ^{:sk :db-stats :idx 4}
  (db-stats [x])
  ^{:sk :history :idx 5}
  (history [x])
  ^{:sk :txs :idx 6}
  (transaction-collection [x])
  ^{:sk :eavt-datoms :idx 7}
  (eavt-datoms [x])
  ^{:sk :aevt-datoms :idx 8}
  (aevt-datoms [x])
  ^{:sk :avet-datoms :idx 9}
  (avet-datoms [x])
  ^{:sk :vaet-datoms :idx 10}
  (vaet-datoms [x]))

(deftype datomic.DatomicSchemaInstance [operation-timestamp connection-name connection-configuration
                                        connection-attributes db-stats history transaction-collection
                                        eavt-datoms aevt-datoms avet-datoms vaet-datoms]
  datomic.PDatomicSchemaInstance
  (operation-timestamp [x] operation-timestamp)
  (connection-name [x] connection-name)
  (connection-configuration [x] connection-configuration)
  (connection-attributes [x] connection-attributes)
  (db-stats [x] db-stats)
  (history [x] history)
  (transaction-collection [x] transaction-collection)
  (eavt-datoms [x] eavt-datoms)
  (aevt-datoms [x] aevt-datoms)
  (avet-datoms [x] avet-datoms)
  (vaet-datoms [x] vaet-datoms))
