(ns datomic-schema-agent.inner-core.captured-schema-reader
  [:require [clojure.edn :as edn]
            [clojure.pprint :as pp]
            [clj-time.core :as t]
            ; [clj-time/date-time
            [clj-time.coerce :as c]
            [me.raynes.fs :as fs]

            [cognitect.transit :as transit]
            [datomic.db]
            [datomic.function]
            [datomic.codec]
            [datomic.client.impl.shared.datom :as datom]
            [datomic.client.impl.shared.exceptions :as ex :refer (anom)]
            ]
  (:import
            [com.cognitect.transit ReadHandler]

            ))

; (def captured-schema-typed-reader
(defn print-data-readers []
  (let [cl (.. Thread currentThread getContextClassLoader)
        data-readers-from-cp (concat
                               (enumeration-seq (.getResources cl "data_readers.clj"))
                               (enumeration-seq (.getResources cl "data_readers.cljc")))]
    (pp/pprint data-readers-from-cp)
    data-readers-from-cp))

(def read-handlers
  {"datom" (reify ReadHandler
             (fromRep [_ v] (apply datom/create v)))})

(defn read-captured-schema-from-file [captured-schema-filepath]
  (print-data-readers)

  (let [schema-exists (fs/exists? captured-schema-filepath)]
    (if (not schema-exists)
      (throw (ex-info "Captured-Schema file does not exist at the specified location."
                       {:causes #{:file-not-found-exception}
                        :fixes #{:create-review-file-location :specify-another-location}
                        :specified-captured-schema-file-path {:value captured-schema-filepath}})))
    (let [schema-text (slurp captured-schema-filepath)
          types (atom [])
          captured-schema (edn/read-string {:readers {'clj-time/date-time c/from-string
                                                      'db/id datomic.db/id-literal
                                                      'db/fn datomic.function/construct
                                                      'base64 datomic.codec/base-64-literal
                                                      ; 'datom (reify ReadHandler (fromRep [_ v] (apply datom/create v)))
                                                      'datom (fn [obj]
                                                               (let [r (transit/reader "datom" obj {:handlers read-handlers})
                                                                     res (transit/read r)]
                                                           res))
;; Add this after the meeting:
    (json/write-str {:a 1 :b 2} :key-fn #(.toUpperCase %))
                                                      }}
                                          schema-text )]
      (pp/pprint captured-schema)

      ; (prn "end types")
      ; (prn "read captured-schema into edn: ")
      ; (pp/pprint captured-schema)``
      )))

 ; If path is a period, replaces it with cwd and creates a new File object
 ; out of it and paths. Or, if the resulting File object does not constitute
 ; an absolute path, makes it absolutely by creating a new File object out of
 ; the paths and cwd.



; (defn read-captured-schema [captured-schema]
;   (isa? captured-schema String)
;   (isa? captured-schema String)
;
;
;   )
