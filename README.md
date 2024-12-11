# Productivity project

## Application info
The application uses:

springboot to build the project
thymeleaf for templating
flyway to manage postgres db migrations
playwright for feature testing
faker to generate fake names for testing
auth0 for authentication
postgres for databases
render for deployyment

## Setup

### 1. Clone the repo
`git clone https://github.com/hansiallen/makers-productivity-project/`

### 2. Set up Auth0
From the Auth0 dashboard, add to Allowed Callback URLs: http://localhost:8080/login/oauth2/code/okta
Add to Allowed Logout URLs: http://localhost:8080

### 3. Set up db in postgress
Set up new db in postgress called `productivity_dev_db`

### 4. Create the env
After cloning the projet you will need to add the .env file set up with your auth0 credentials
```
OKTA_ISSUER=https://[put auth0 url here]/
OKTA_CLIENT_ID=[put client id here]
OKTA_CLIENT_SECRET=[put clident secret here]
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_URL=postgresql://localhost:5432/productivity_dev_db
```
### 5. Start the server
Build the app and start the server, using the gradle command  `gradle bootRun`
Build the app and start the server with debug, using the gradle command  `gradle bootTestRun`
Run tests using `gradle test`
