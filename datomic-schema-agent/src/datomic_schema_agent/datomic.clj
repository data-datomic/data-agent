(ns datomic-schema-agent.datomic
  [:require [clojure.pprint :as pp]
            [datomic-schema-agent.config.agent-configuration-reader :as conf]
            [datomic-schema-agent.connect.datomic-connector :as conn]])

;; Start using the Clojure Type System:
;;    - https://clojure.org/reference/protocols
;;    - https://clojure.org/reference/datatypes
;;
;; Clojure Object/Interface/Type related methods, macros, etc.
;;    - (deftype )
;;    - (defrecord )
;;    - (defstruct)
;;    - (definterface )
;;    - (defprotocol)
;;    - (reify )

;;
;; isa/instanceof type relationship and hierarchy
;; multimethods
;;    - defmulti
;;    - defmethod
;;    - remove-method
;;    - prefer-method

;
;        - defmulti - a dispatching function must be supplied. This function will be applied to the arguments to the multimethod in order to produce a dispatching value.
;                     The multimethod will then try to find the method associated with the dispatching value or a value from which the dispatching value is derived.
;                     If one has been defined (via defmethod), it will then be called with the arguments and that will be the value of the multimethod call.
;                     If no method is associated with the dispatching value, the multimethod will look for a method associated with the default dispatching value (which defaults to :default), and will use that if present. Otherwise the call is an error.
;
; The multimethod system exposes this API:
;;  defmulti creates new multimethods,
;;  defmethod creates and installs a new method of multimethod associated with a dispatch-value,
;;  remove-method removes the method associated with a dispatch-value and prefer-method creates an ordering between methods when they would otherwise be ambiguous.


;; Clojure Type Derivation
;;    - derive
;;    - make-hierarchy
;;    - isa?
;;    - parents
;;    - ancestors
;;    - decendants


;;
;; Extends constructs:
;;    - extends?
;;    - extenders
;;    - satisfies?
;;    - extend-type
;;    - extend-protocol
;;    - :extend-via-metadata i


(defprotocol PDatomicOnPremConnectionConfig
  (hostname [x])
  (port [x])
  (schema-name [x]))

(defprotocol PDatomicCloudConnectionConfig
  (server-type [x])
  (region [x])
  (system [x])
  (endpoint [x]))

(defprotocol PDatomicProxiedCloudConnectionConfig
  ; (server-type [x])
  ; (region [x])
  ; (system [x])
  ; (endpoint [x])
  (proxy-port [x]))


(deftype DatomicOnPremConnectionConfig [onprem-hostname onprem-port onprem-schema-name]
  PDatomicOnPremConnectionConfig
  (hostname [this] onprem-hostname)
  (port [this] onprem-port)
  (schema-name [this] onprem-schema-name))

(deftype DatomicCloudConnectionConfig [cloud-server-type cloud-region cloud-system cloud-endpoint]
  PDatomicCloudConnectionConfig
  (server-type [x] cloud-server-type)
  (region [x] cloud-region)
  (system [x] cloud-system)
  (endpoint [x] cloud-endpoint))

(deftype DatomicProxiedCloudConnectionConfig [prxycloud-server-type prxycloud-region prxycloud-system prxycloud-endpoint]
  PDatomicCloudConnectionConfig
  (server-type [x] prxycloud-server-type)
  (region [x] prxycloud-region)
  (system [x] prxycloud-system)
  (endpoint [x] prxycloud-endpoint))

(extend-type DatomicProxiedCloudConnectionConfig
  PDatomicProxiedCloudConnectionConfig
    (proxy-port [x] ""))

;; Given a connection-configuration, creates, associates, maintains, and updates a datomic-connection instance
(defprotocol PDatomicConnector
  (config [this])
  (client [this])
  (connection [this]))

(deftype DatomicConnector [config client connection]
  PDatomicConnector
  (config [this] config)
  (client [this] client)
  (connection [this] connection))

(defn make-datomic-connector
  ([name] (make-datomic-connector name nil))
  ([name config-file-path]
  (let [plain-config (conf/read-configuration config-file-path)
        config-inst (cond
                      (= (count (:datomics plain-config)) 1) {:datomic-connection-configuration (:datomic-connection-configuration (first (:datomics plain-config)))
                                                              :name (:name (first (:datomics plain-config)))}
                      (> (count (:datomics plain-config)) 1) {:datomic-connection-configuration (:datomic-connection-configuration (first (filter #(= (:name %) name) (:datomics plain-config))))
                                                              :name (:name (first (filter #(= (:name %) name) (:datomics plain-config))))}
                      :else (throw (ex-info "Datomic Database Server Connection-Configuration not found."
                                      {:causes #{:configuration-instances-empty}})))
        connector-set (conn/create-datomic-connection config-inst)]
    (->DatomicConnector config-inst (:client connector-set) (:connection connector-set)))))

(defn make-datomic-connectors
  ([] (make-datomic-connectors nil))
  ([config-file-path]
  (let [plain-config (conf/read-configuration config-file-path)
        datomic-insts (:datomics plain-config)
        datomic-connections (into [] (map (fn [config]
                                            (let [connection (conn/create-datomic-connection config)]
                                              (->DatomicConnector config (:client connection) (:connection connection)))) datomic-insts))]
    datomic-connections)))
