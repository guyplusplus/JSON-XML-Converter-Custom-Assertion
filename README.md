# XML-JSON Transform

This assertion transforms XML / JSON based on JSON schema object included in the OpenAPI 3 document. The schema objects contain XML hints that adds more capability. For more information:
* introduction: https://swagger.io/docs/specification/data-models/representing-xml/
* specifications: https://swagger.io/specification/#xmlObject

The core of the conversion is done via the jsonxmlconver library: https://github.com/guyplusplus/JSON-XML-Converter-via-OpenAPI-Schema

![Dialog Screenshot](./DialogScreenShot.png)

## Schema Caching
_Schema caching_ is controlled with the 3 following cluster properties. Every 5 minutes these properties are refreshed:
* jsonxml.schemaCache.maxAge (in ms): entries older than this age are flushed. Set to -1 to avoid cache flush. Default is -1
* jsonxml.schemaCache.maxDownloadSize (in characters). Default is 128KB
* jsonxml.schemaCache.maxEntries. Set to 0 to avoid cache. Default is 128
