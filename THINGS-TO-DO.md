
Move configuration to global/referencable location (i.e. user-home):
   - ~/.datomic-schema-agent directory to hold all artifacts used to either run the
         schema-agent or, to store or otherwise publish generated artifacts.

In configuration, maintain an endpoint for publishing output from the initial base operations (i.e. capture-dataset)


Construct datomic schema models
   - idea-1: Entity models, modeled entity for each change event
   - idea-2: Capture all datom events as a series of change-events, indexed by schema/entity  
              This dual pairing will allow us to track both, which properties are applied or modified when
              as well as indicate which entities do not have the new values (i.e. older entities)
