(ns datomic-schema-agent.config.agent-configuration-reader
  [:require [clojure.pprint :as pp]
            [me.raynes.fs :as fs]
            [cheshire.core :refer :all]])

(def default-configuration-file-path (str (.getAbsolutePath fs/*cwd*) "/" "schema-agent-conf.json"))

(defn read-configuration
  ([] (read-configuration nil))
  ([configuration-file]
  (let [config-file (if (nil? configuration-file)
                      default-configuration-file-path
                      configuration-file)
        config-exists (fs/exists? config-file)]

    (if (not config-exists)
      (throw (ex-info "Configuration file does not exist at specified location."
                       {:causes #{:file-not-found-exception}
                        :fixes #{:create-config-file-at-location :specify-another-location}
                        :specified-config-file-path {:value config-file :unit :celsius}})))

    (let [config-text-as-str (slurp config-file)
          config-edn (parse-string config-text-as-str true)
          config (into [] (map (fn [x] (update-in x [:datomic-connection-configuration :server-type] #(keyword (clojure.string/replace % ":" "")))) (:datomics config-edn)))]
      (assoc config-edn :datomics config)))))
