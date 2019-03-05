# Set up CyberArk Conjur


## Follow below instructions to setup conjur server, client, database
```
$ curl -o docker-compose.yml https://www.conjur.org/get-started/docker-compose.quickstart.yml

$ docker-compose pull

$ docker-compose run --no-deps --rm conjur data-key generate > data_key

$ export CONJUR_DATA_KEY="$(< data_key)"

# To run the Conjur server, database and client
$ docker-compose up -d

# Create a default account

$ docker-compose exec conjur conjurctl account create quick-start

# connect to the client 

$ docker-compose exec client bash

# Initialize the Conjur client using the account name and admin API key you created

$ conjur init -u conjur -a quick-start # or whatever account you created
$ conjur authn login -u admin
Please enter admin\'s password (it will not be echoed):
```


