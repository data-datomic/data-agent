

Schema Agent Configuration
Outline and Definitions


Datomic Instance Connection Configuration


We will keep and maintain the definition of our configuration schema in here since we are unable to make comments in JavaScript.

Our current configuration schema has the following appearance:

::FILE-START
{
  "datomics" [
    {
      "datomic-type": "onprem",
      "hostname": "host",
      "port": 080909,
      "schema-name": "not sure if this is even a valid idea"
    },
    {
      "datomic-type": "cloud",
      "region": "us-east-1",
      "system": "dev",
      "endpoint": "http://entry.dev.us-east-1.datomic.net:8182/"
    },
    {
      "datomic-type": "cloud",
      region: "us-east-1",
      system": "dev",
      endpoint: "http://entry.dev.us-east-1.datomic.net:8182/",
      proxy-port": 8182
    },
  ]
}
::FILE-END


Let's go through each item in the order of its appearance.

   1. "datomics" - this is the collection of datomic instances that should be considered for operations within the schema-agent.
   2. The next entry immediately within the beginning of the array, is the definition for a Datomic-OnPrem configuration.
      It contains the following the properties:
         - datomic-type
         - hostname
         - port
         - schema-name
   3. The entry following the datomic-onprem configuration object is a configuration for a datomic-cloud instance.
      (NOTE: there are still other properties that must go here)
