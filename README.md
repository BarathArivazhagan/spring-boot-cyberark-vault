# spring-boot-cyberark-vault

Java client integrated with cyberark vault management providing spring boot bootstrap features.

How to setup CyberArk Conjur :

[Conjur Setup](https://www.conjur.org/get-started/install-conjur.html)


   - conjur policy load root quickstart.yml
   - conjur list
   - conjur variable values add mysql-password  root

[CyberArk Rest API](https://documenter.getpostman.com/view/998920/cyberark-rest-api-v10-public/2QrXnF)


Features:

- Bootstrap configuration with spring boot
- Vault secrets are injected into spring environment


