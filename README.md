# ServiceProvidingOffers

All requests and responses are JSON format.

Service Endpoints

create new offer - Post http://localhost:8085/offers

example body
{
	"name" : "rename",
	"description" : "Hello desgreggupdatemehere",
	"uniqueOfferId" : "sss",
	"expiryTime" : "2020-06-09T19:55:04"
}


get specific offer by id - Get - http://localhost:8085/offers/offerId

get all offers - Get - http://localhost:8085/offers

cancel (expire) an offer - Delete - http://localhost:8085/offers/offerId

Offers are created with a uniqueId, description, name and expiryTime which must be provided by the user.
The expiryTime parameter must be in the future. An appropriate message will display if the time does not meet the criteria.
The date follows the format of ISO date-time, such as '2020-07-09T10:15:30'.

Offers can be queried, cancelled and deleted using the unique id.

Offers will be marked as expired when the expiryTime provided is reached. This will be reflected in further requests.

Offers will be marked as cancelled when explicitly cancelled by the user. This will be reflected in further requests.


Other considerations for future implementations
Performance
