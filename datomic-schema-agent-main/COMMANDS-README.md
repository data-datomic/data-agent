

; Operations

;; Agent Configuration, setup, and maintenance operations
    - this is inherantly internal or, could be implemented later or not at all and, not effect the operation of the Agent.
      These are nice to haves but, do not enable any additional functionality, with the exception of verify-datestore-operational-status.

add-datastore
update-datastore
remove-datastore
list-datastores
verify-datestore-operational-status


; Data management operations (import/export, backup/restore, and, maybe for later on: visualization?, statistical-analysis?):

;;#######################################################
;; capture-dataset

;;#######################################################
;; extract-entities


    export-entities
    import-entities
    create-backup
    restore-backup


; Data migration operations:

;;#######################################################
;; generate-migration-plan
;;   Generates a 'migration-plan' -- does this by producing a gap-computation between the
;;   destination schema and the current state of the migration-target systems and
;;   calculated-set of property differances between them.
;;
;; --migration-targets
;;   The target database instances to apply the computed migrations to. This can take either
;;   CLI instance configurations or, names as quoted strings of data-store connection
;;   configurations in the overall (global) agent-config.
;;
;; --produce-unsatisfiable-field-definitions-report (alternatively: --alert-on-unsatisfiable-fields)


;;#######################################################
;; edit-field-transforms
;;   Allows the user to perform adjustments or enhancments to the calculated
;;   field-transformation operations. (and default values for new fields)
;;   Takes the user through all fields that will be created or transformed
;;   in some way and allows them to specify transformation strategies as
;;   well as defaults.
;;
; --interactive
; --each-entity
; --overall-entity


;;#######################################################
;; compute-destination-structure operation
;;   This operation is to perform a computation of what properties should be on the
;;   target-destination/updated schema. This operation can be performed using the
;;   following methods:
;;
;; --from-schema-def-in-source
;;   This is basically saying, use the current schema related properties in a
;;   provided source directory to determine an "absolute state of the world".
;;   This then has the ramification of not allowing the destination schema structure
;;   from updating itself as a result of processing.
;;
;; --within-single-instance
;;   This was the original idea for this project. Go through the extracted data-set
;;   and compute a super set of properties, using a the fact that they where tied to
;;   common entities as other members in the association to establish membership.
;;
;; --across-many-instances
;;   This method takes a collection of data-sets and applies the same strategy as before, looking for
;;   additions and subtractions or changes of properties but, does it from a multitude of running
;;   database instances or extracted data-sets.
