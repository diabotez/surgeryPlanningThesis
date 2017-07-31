# opep_osd
Operation Planning of Elective Patients  in an Orthopedic Surgery Department

# Version
    0.1 (Beta)
    
# Instructions
    * Clone the repository
    * Open the project in a java IDE (like NetBeans 8.2, eclipse, etc.)
    * Import "surgeryPlaning" to the workspace as an existing project
    * Run it in release or debug mode
    
The app is connecting to a local database with:

        user: rootUser
        pass: Z4r4g0Z4

# Credentials
To use the app, enter the credentals for one of the available user types in the log-in form.

    Head of departmert
        username: admin  
        password: 1234
        
    Coordinator
        username: teamlead 
        password: 1234
        
    Medic
        username: doctor 
        password: 1234
        
    Assistant
        username: assistant 
        password: 1234
    
# Dependencies
    * JDK 1.8 & JRE 1.8

# MySQL Server
    Version 
        5.7.18
        
    Linux (Ubuntu)
        sudo apt-get install mysql-server
        sudo mysql-secure-install    
        
    Windows
        download MySQL installer (https://dev.mysql.com/downloads/installer/)
        install regarding personal preferences

# running command
    Linux OS:
        use the following '.sh' file.
        ```
        sh unix-operationPlanning.sh
        ```

    Windows OS:
        use the '.bat' file to run.
