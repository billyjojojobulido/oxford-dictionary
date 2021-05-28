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
  
      RED `commit ID: 6b9653a07eacd2f1c682d2ef6aeeb0e3ba4ce3ec`

## Citation
* The scaffold codes for the GUI in package view are mainly from the code I wrote in this unit for task3.
* The scaffold codes for the HTTP GET and POST request in HTTPController.java are mainly from the code I wrote in this unit for task3.
* The scaffold codes for the SQLite JDBC read and update are mainly from the Sample.java in https://github.com/xerial/sqlite-jdbc .

## Contact
Created by bwan3675 baocheng wang - feel free to contact me via bwan3675@uni.sydney.edu.au!
