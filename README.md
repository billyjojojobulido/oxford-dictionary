# SOFT3202 Exam Pre-work
> The Pre-work for the final exam of SOFT3202

## Table of contents
* [API](#api)
* [Dependencies](#dependencies)
* [Configuration](#configuration)
* [Operation](#operation)
* [TDD Logbook](#tdd-logbook)
* [Citation](#citation)
* [Contact](#contact)

## API
> INPUT API: https://developer.oxforddictionaries.com/ 
  * `Entity`: Given a word, check if it has an entry or a list of possible root forms. Given an entry, display an info
    panel about that entry.
  * `Report data`: data on a given entry.
  * `Database caching`: info on entries. 
> OUTPUT API: https://sendgrid.com/solutions/email-api/
  * Send the `report data` as an email to a single configured email address (can be configured in a text config file or
the app itself) 

## Dependencies
* Test - JUnit 4
* Test - Mockito
* JSON - json-simple
* Database - SQLIte-JDBC

## Configuration
Before you run the software, you should set up your configuration file first, which is stored in the path: `src/main/resources/configFile.json`

Simply fill in the 9 features:

 > "id": "your api id for oxford dictionary api",
  
 > "key": "your api key for oxford dictionary api",
  
 > "emailKey": "your api key for the SendGrid api",
  
 > "emailFrom": "the email address you want to use to send the email",
  
 > "emailTo": "the email address to receive the email",
  
 > "emailReply": "the email address you want to use to receive the reply form the receiver",
  
 > "senderName": "the name of the person who send the email",
  
 > "targetName": "the name of the person who receive the email",
  
 > "replyName": "the name of the person who receive the reply from the receiver"

If you did not set up the configuration file or the data you wrote in was invalid, the software will stop working and exit.

## Operation

You can start the software by entering the following command:

   `gradle run --args="<input api mode> <output api mode>"`
  
with the first controlling the input API, and the second controlling the output API. 

> `<input api mode> `:

          - offline:  dummy version of Oxford Dictionary API
          
          - online:   Oxford Dictionary API
          
> `<output api mode> `:

          - offline:  dummy version of SendGrid API
          
          - online:   SendGrid API

For example:

`gradle run --args="offline online"`

WARNING: if the arguments are not in the correct forms or not enough arguments are given, the software will not start.



## TDD Logbook
  > Round 1: 
  Testing:
  * api id, api key validation checking
  * sending email
  * check entity's existence in the database
  * retreive entity from database
  * update entity in database
  
      RED      `commit ID: 658d6e3fd39af27412b5516c7a359c5a2ae45a52`
      
      GREED    `commit ID: ded3f2e8cd84995871fc9bbf8bd421c3c9a40e4d`
      
      REFACTOR 
      
            1.0 --- `commit ID: 9fbb030d55aa94c54d5836d14f8584195d1df3ee`

            2.0 --- `commit ID: 130de9417f7826696de781f06e3ef089f910e48b`
            
            3.0 --- `commit ID: f3776a3177a0bb49e8f58db8cacf10b7a56288a8`
            
            4.0 --- `commit ID: 757a55db35398f08d1b033d5568651560ca4d33e`
            
            5.0 --- `commit ID: 3ad3cb0ed51da6c0562a5669f5ce39f92c2328b4`
            
   Final Commit : 
   
           `commit ID: 4e5615d7e30c9907532f322f4290e3fb80749335`
            
## Citation
* The scaffold codes for the GUI in package view(src/main/view/RequestWindow.java) are mainly from the code I wrote in this unit for task3.
* The scaffold codes for the HTTP GET and POST request(src/main/model/InputHTTP.java, OutputHTTP.java) are mainly from the code I wrote in this unit for task3.
* The scaffold codes for the SQLite JDBC read and update(src/main/model/Database.java) are mainly from the Sample.java in https://github.com/xerial/sqlite-jdbc .

## Contact
Created by bwan3675 baocheng wang - feel free to contact me via bwan3675@uni.sydney.edu.au!
