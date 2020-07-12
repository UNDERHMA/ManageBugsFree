# ManageBugsFree
## Bug Tracking Application

ManageBugsFree is a simple bug tracking application written in Java. This demo application mimics how businesses set up applications to track and manage bugs internally. 

It loosely follows an MVC architectural pattern, with JavaServer Faces (JSF) pages serving as the view, CDI Managed Beans and the Faces Servlet as the controller, and stateless Enterprise Java Beans (EJBs) and Entities as the model. The view also uses the PrimeFaces and OmniFaces JSF component libraries in some instances.

### Authentication and Authorization

This project uses Auth0 for authentication and authorization, based on the Auth0 Java EE SDK Quickstart example (https://auth0.com/docs/quickstart/webapp/java-ee/01-login). The website.managebugsfreeapp.security.auth0javaeesample package consists of:

-classes that prepare for authentication by creating an Auth0 authorization URL where users log in (Auth0AuthenticationConfig, Auth0AuthenticationProvider, Auth0JwtCredential, Auth0JwtIdentityStore, Auth0JwtPrincipal and LoginServlet)

-classes that create a JSON Web Token (JWT) after Auth0 returns an authorization code upon successful authentication. Auth0 issues an Http Request to the CallbackServlet class which redirects to the Home.xhtml web page, then the Auth0AuthenticationMechanism processes the authorization code and creates the JWT after an Http Request to the CallbackServlet is made). 

If the user has a valid com.auth0.state cookie or entered the correct username/password, their HttpSession is given accessToken, idToken and User attributes that permit them to enter the application. The website.managebugsfreeapp.authenticationfilter package's AuthenticationFilter class applies a filter to all .xhtml pages and only permits access if the user is deemed active based on the HttpSession accessToken, idToken and User attributes. 

If the user is not deemed active, he or she is redirected to the login page created by the LoginServlet. The Logout link at the top right of the application provides access to the website.managebugsfreeapp.security.auth0javaeesample LogoutServlet class, which invalidates the HttpSession and sends the user back to the login page.

### Users and Roles

Users and roles for authentication and authorization are created and stored on Auth0's website. User and role information is passed to this application as part of authorization and is stored in the session for use.

### Hosting

Originally, this project ran on localhost. A 6/28/2020 commit included changes to the web.xml and persistence.xml that allows this project to run over the internet through Jelastic's PaaS platform. Some details from the web.xml and persistence.xml were omitted for security. 

Jelastic's PaaS platform hosts this application in the cloud on a Glassfish 5.1 server with a Postgres 12.0 database. This application can also be deployed in a docker container onto AWS or Azure, provided a good Glassfish 5.1 image is found and configured correctly. I found that Jelastic allowed for easier container configuration, although I am new to docker, so a more experienced user may find it easier to work with docker directly.

### Scripts

The src/test/java/website/managebugsfreeapp/testdata folder includes scripts that create database tables and sequences, insert dummy data and update sequences after dummy data is inserted.

&nbsp;
&nbsp;
### ManageBugsFree Application Video Overview:
[![ManageBugsFree](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/ManageBugsFree_Video.JPG)](https://www.youtube.com/embed/MUA-t4On4fA)
&nbsp;
&nbsp;
&nbsp;
&nbsp;
### Auth0 Configuration Video Overview:
[![Auth0](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/Auth0_Video.JPG)](https://www.youtube.com/embed/keYJ4eZDPI4)
&nbsp;
&nbsp;
&nbsp;
&nbsp;
### Jelastic eApps Environment Video Overview:
[![Jelastic](https://github.com/UNDERHMA/ManageBugsFree/blob/master/Images/eApps_Video.JPG)](https://www.youtube.com/embed/zAexF2q4VXM)
&nbsp;
&nbsp;
