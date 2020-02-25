(ns datomic-schema-agent.connect.datomic-connector
  [:require [clojure.pprint :as pp]
            [datomic.client.api :as d]])

;; defn verify-config

(defn- make-datomic-client [config]
  (let [client (d/client (:datomic-connection-configuration config))]
    ;; Do some kind of validation with the client-inst here...
    client))

(defn- make-datomic-connection-inst [config client]
  (let [dbname (:db-name (:datomic-connection-configuration config))
        connection (d/connect client {:db-name dbname})]
    ;; verify/validate the connection, see that the endpoint is present...
    connection))

(defn create-datomic-connection [connection-config]
  (let [client (make-datomic-client connection-config)
        connection (make-datomic-connection-inst connection-config client)]
    {:connection connection :client client}))


    ;
    ; require 'compute.datomic-client-memdb.core)
    ;                                           (if-let [v (resolve 'compute.datomic-client-memdb.core/client)]
    ;                                             (@v {})
    ;                                             (throw (ex-info "compute.datomic-client-memdb.core is not on the classpath." {}))))
    ;                                       ;(d/client (merge cfg (when (:dev env) {:proxy-port 8182})))
    ;                                       (d/client (:datomic-config env))))
    ;                         (d/create-database client {:db-name (:db-name env)})
    ;                         (d/connect client {:db-name (:db-name env)}))
