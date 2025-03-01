# Get Token
Get token reads the token from the external provider through a Feign Client. 

With Feign client we can easely call an external method through an interface without explicitly implementing the client internals. 

Then the date is generated  by Java traditional methods transforming it to its proper format (ISO 8601). 

Then we validate the token checking the structure (analized with json.io), and with Jackson we check the structure and the integrity of the values inside. 

To check the time validity, we will use the exp field, and calculate the validity against the now date. If token is null or "", the authentication is FORBIDDEN. If token is expired or structure is bad formed, then the authentication is UNAUTHORIZED. 

Token persistence in RAM is done with one string variable encapsulated on a service, if this token is invalid, is overwritten by a new token. There are no more users of the external provider than one (the microservice), so we can mantain only one token. 

As far as we don't know the secret, we can't validate the sign of the token, so we won't put it on the basic validation. 

We also check on tokens that token emitter is 'auth-vivelibre'. 

A logging service have been implemented, with custom file generation for simplicity to be exploited by external apps like Graphana.

# Get Metrics
For metrics, we will expose an endpoint dedicated to this. 

The external consumer service can retrieve the metrics calling it as a JSON object, and can be expandable easely adding more fields to the Metrics java class. 

The only metric we have is 'petittions processed', which is incremented on each call.