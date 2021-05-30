# SOFT3202 Exam Pre-work
> The Pre-work for the final exam of SOFT3202

## Table of contents
* [API](#api)
* [Dependencies](#dependencies)
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
            
## Citation
* The scaffold codes for the GUI in package view are mainly from the code I wrote in this unit for task3.
* The scaffold codes for the HTTP GET and POST request in HTTPController.java are mainly from the code I wrote in this unit for task3.
* The scaffold codes for the SQLite JDBC read and update are mainly from the Sample.java in https://github.com/xerial/sqlite-jdbc .

## Contact
Created by bwan3675 baocheng wang - feel free to contact me via bwan3675@uni.sydney.edu.au!
