 mvn dependency:purge-local-repository
StatusCode

2xx: success response

200: ok
201: created
202: accepted
204: no-content: delete/put:

3xx: redirection
301: moved permanently : hateos: location
304: not modified/etag
307: temporary redirect

4xx: client error
400: Bad request
401: unauthorized
404 : page not found
405: Method not supported
406: Not acceptable: application/json: accept: xml
415: Unsupported Media type: server is sending some data in some media type 

5xx: server error
501: Not yet implemented

/persons/{emailId}




Versioning
/persons/{id}/addresses
version info in the request

1) in the url

Shallowetagfilter not working for put, patch
so use version column and set etag manually

deep-etag: