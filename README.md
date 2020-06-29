# ManageBugsFree
## Bug Tracking Application

ManageBugsFree is a simple bug tracking application written in Java. It is a demo application that mimics how businesses set up applications to track and manage bugs internally. It loosely follows an MVC architectural pattern, with JavaServer Faces (JSF) pages serving as the view, CDI Managed Beans and the Faces Servlet as the controller, and stateless Enterprise Java Beans (EJBs) and Entities as the model. The view also uses the PrimeFaces and OmniFaces JSF component libraries in some instances.

This project uses Auth0 for authentication and authorization, based on the Auth0 Java EE SDK Quickstart example (https://auth0.com/docs/quickstart/webapp/java-ee/01-login). The website.managebugsfreeapp.security.auth0javaeesample package consists of classes that perform the steps that prepare for authentication by creating an Auth0 authorization URL where users log in (Auth0AuthenticationConfig, Auth0AuthenticationProvider, Auth0JwtCredential, Auth0JwtIdentityStore, Auth0JwtPrincipal and LoginServlet) and classes that create a JSON Web Token (JWT) once Auth0 sends back an authorization code upon successful authentication (Auth0 issues an Http Request to the CallbackServlet class which redirects to the Home.xhtml web page, and the Auth0AuthenticationMechanism processes the authorization code and creates the JWT once an Http Request to the CallbackServlet is made). If the user has a valid com.auth0.state cookie or if they entered the correct username/password, then their HttpSession is given accessToken, idToken and User attributes which permit them to enter the application. The website.managebugsfreeapp.authenticationfilter package's AuthenticationFilter class applies a filter to all .xhtml pages and only permits access if the user is deemed active based on the HttpSession accessToken, idToken and User attributes. If the user is not deemed active, the user is redirected to the login page that is created by the LoginServlet. The Logout link in the top right corner of the application provides access to the website.managebugsfreeapp.security.auth0javaeesample LogoutServlet classs, which invalidates the HttpSession and sends the user back to the login page.

Users and roles for authentication and authorization are created and stored on Auth0's website. User and role information is passed to this application as part of authorization and is stored in the session for use. Please see the snapshots at the end of this README for images of the Auth0 users and roles pages. 

Originally, this project ran on localhost. A 6/28/2020 commit included changes to the web.xml and persistence.xml that allows for this project to run over the internet through Jelastic's Paas platform. I omitted some details from the web.xml and persistence.xml for security. 

The src/test/java/website/managebugsfreeapp/testdata folder includes scripts that create database tables and sequences, insert dummy data and update sequences after dummy data is inserted.

Jelastic's Paas platform was used to host this application in the cloud on a Glassfish 5.1 server with a Postgres 12.0 database. This application could also be deployed in a docker container onto AWS or Azure if you can find a good Glassfish 5.1 image and configure it correctly to run. I found Jelastic to be easier, as it allowed for easier container configuration, although I am new to docker, so a more experienced user may find it easier to work with docker directly.

### Here are some snapshots that show various parts of the application:  
### Auth0 User Management Page:
![alt text](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/Users.PNG?raw=true)  
### Auth0 Role Management Page:
![alt text](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/Roles.PNG?raw=true)  
### www.managebugsfree-app.website login page provided by Auth0:
![alt text](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/Login.PNG?raw=true)  
### www.managebugsfree-app.website home page, displayed after successful authentication at the login page provided by Auth0:
![alt text](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/Home.PNG?raw=true)  
### www.managebugsfree-app.website bug reports page, showing the most recent bug reports in a sortable and searchable grid:
![alt text](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/Bug%20Reports.PNG?raw=true)  
### Jelastic's PaaS Eapps Dashboard, where www.managebugsfree-app.website is hosted:
![alt text](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/Eapps.PNG?raw=true) 
