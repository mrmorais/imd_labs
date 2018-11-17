# IMD Laboratórios
Android app to open maintenance requests on IMD's computing labs

This app was created on demand for "Desenvolvimento de Dispositivos Móveis 2018.2" lecture. The general working flow aim is: the user opens a request on app (by identifying the computer, indicating the defect and giving some extra information), then the app creates a insertion on API that sends a email to the responsible sector (Diretoria de TI); this email contains a URL that must be accessed when the problem is solved to trigger a status update. At last, the user will be aware when the defect is fixed.

### Domains
I'm creating three subdomains in this repo, the first one is the "application" (the main Android app); the second one is the "server" (a Node.JS server, database less, responsible for manage requirements from app, email triggers and dispatch emails); the third one is "documentation" where I'm going to put all wireframes, prototypes links, reports, studies, etc.

### Colaboration
You are invited to open issues, fork and create pull requests to this project. Just follow a GitHub workflow.
