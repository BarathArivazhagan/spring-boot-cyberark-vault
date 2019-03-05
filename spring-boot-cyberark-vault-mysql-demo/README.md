# spring boot cyberark vault mysql demo

Spring boot application integrated with spring boot cyberark vault client and mysql database

## Steps:

- Setup Cyberark conjur

- Follow below instructions to load the policy

```
$ docker-compose exec client bash
$ conjur init -u conjur -a demo-account
$ conjur authn login -u admin
# copy quickstart.yml under samples folder
$ conjur policy load root samples/quickstart.yml
$ conjur list
$ conjur variable values add mysql-password  root
```

-  Maven build

```
$ mvn clean package
```

- Run MYSQL database 

```
$docker run --name mysql-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root  -d mysql:latest
```

- Run the demo application

```
$ mvn spring-boot:run
```

- Verify spring boot application connected to mysql database