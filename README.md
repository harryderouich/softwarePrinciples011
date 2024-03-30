# Certificate Generator Application

* Module: 2023 MOD003484 TRI2 F01CAM
* Assignment: 011 Element Design and implementation report
* Author: Harry Derouich (SID 2304874)
* Team: Skystone

## Overview
Skystone is a Java-based certificate generation application that facilitates the scalable creation of certificates. In addition, administrators can create and manage quizzes that provide automatic issuance of certificates upon an assigned user passing. 

## Features
- Plan Comparison (Personal, Business, Business+)
- Register an account
- Log in with a Username and Password
- Create a single certificate
- Create a number of certificates manually*
- Add additional custom fields to certificates*
- Create certificates in bulk via CSV upload*
- Generate login keys linking end-users to assigned quizzes**
- Log in with an assigned Login Key
- Add new multiple choice quizzes to the platform**
- Take assigned multiple choice quizzes and receive a certificate upon passing
- Help platform with FAQs and facility for support tickets(*) to be submitted
- Log out whilst maintaining application state
- Validated inputs throughout

``` 
*Business and Business+ Accounts only
**Business+ Accounts only
```

## Test Credentials
To test the application, you are free to register your own account/s, or use the provided test credentials below:
```
Account Type: Personal
Email: test@personal.com
Password: password1

Account Type: Business
Email: test@business.net
Password: password2

Account Type: Business+
Email: test@businessplus.co.uk
Password: password3
```

## Usage
To launch the program, compile and run the `Main.java` class.

## Libraries Used
- [OpenCSV 5.7.1](https://mvnrepository.com/artifact/com.opencsv/opencsv)
- [JSON In Java](https://mvnrepository.com/artifact/org.json/json)

## Application Flow
Upon launching the program, you will first start at the `Main Menu`

```
Main Menu
1. Register
2. Login
3. Help
0. Exit
Please enter a choice: 
```

### Registration
Pressing `1` takes you to the `Register` page.

```
Register:
1. Personal Account (£0)
2. Business Account (£99+)
3. Business+ Account (£399+)
0. Return
Please enter a choice: 
```
You now have the choice between the 3 different account types: 

- **Personal** accounts can generate single certificates manually and view FAQs. 
- **Business** accounts can additionally create multiple certificates by manually entering details, create certificates in bulk via a CSV upload, add custom fields to certificates and submit support tickets to receive help. 
- **Business+** accounts can also create quizzes, plus prefill an individuals details and assign them a quiz via a unique generated login key

Depending on the account type selected, users are requested to input the following details

#### Personal
- Email Address (validated as being valid via regular expression of the format `[username]@[domain].[tld]`)
- Password (validated against a minimum length of 8 characters)

#### Business and Business+
- Same as **Personal**, plus:
- Business Name. Validated against being empty.
- Required monthly quota. Validated as being one of the allowed values `100, 500, 1000, 2500, 5000 and 10000`)
- The selected account type (Business/Business+) and required monthly quota then returns the correct monthly price from a matrix
- Payment option: Monthly or annually (saving 25%)
- Long Card Number. Validated as being 16 digits (after any spaces removed) and only digits)
- Card expiry month. Validated against being in the range 1-12 or 01-09)
- Card expiry year. Converted to 4 digits if required and validated against being in the current year or greater/in the future)
- Card CVC. Validated against being 3-4 digits in length.

Upon successful creation of an account, the entered details are written to a `user_accounts` database, and you will be automatically logged in and can press enter to continue. 

### Log In
From the main menu, pressing `2` lets you log in with an account already stored in the `user_accounts` database (including any of the test credentials).

```
Login Menu
1. Enter a Login Key
2. Login with Email & Password
0. Return
Please enter a choice: 
```

First looking from an account holder's perspective, we press `2` to log in with an email and password. User is prompted for their email and password, which are queried in the database and if found, you will be logged in and can press enter to continue. 

The features visible from this point onwards are account dependent. 

#### Personal
```
Main Menu: 
User: test@personal.com
1. Certificate Creation
2. Log Out
3. Help
0. Exit
Please enter a choice: 
```

#### Business
```
Main Menu: 
User: test@business.com
1. Certificate Creation
2. Log Out
3. Help
0. Exit
Please enter a choice: 
```

#### Business+
```
Main Menu: 
User: test@businessplus.co.uk
1. Certificate Creation
2. Log Out
3. Help
4. Configure Quizzes
0. Exit
Please enter a choice: 
```

### Personal Account Certificate Generation
When choosing `1` at the main menu whilst logged in as a personal account, you will be taken to the following page:

```
Personal Account Certificate Builder
1. Create single certificate
0. Exit
Please enter a choice: 
```

Upon entering `1` you will be prompted for the following preset details:
- Business Name
- Participant Name
- Course Name
- Date
- Instructor Name

Upon completion, the certificate is formatted into a certificate and outputted, with labels and entered values left aligned:

```
--------------------------------------
Participant Name: John Smith
Instructor Name:  Michael Bell
Course Name:      Mathematics
Date:             30-03-2024
Business Name:    Example Business
--------------------------------------
 
Certificate created successfully
Press Enter key to continue...
```

### Business and Business+ Certificate Generation
Business and Business+ accounts both have the same certificate generation features. When pressing `1` at the main menu whilst logged in as a Business or Business+ account, you will be taken to the following page:

```
Business/+ Account Certificate Builder
1. Create certificates manually
2. Create certificates in bulk
0. Exit
Please enter a choice: 
```

#### Manual Generation
When selecting `1`, you are prompted for the number of certificates you require, which can be any positive number. 

After entering this, you are taken to a page allowing for further customisation: 

```
Additional Options
1. Add a custom field
0. Continue
Please enter a choice: 
```

By pressing `1` you can optionally add a custom field to the generated certificates. You are prompted for the label you wish your custom field to have. The field will be added, and you are able to add as many further custom fields as you like, or can press `0` when ready to proceed. 

After this, you are prompted for the required certificate fields (including any custom field/s), for the amount of times you entered as the required amount of certificates. 

Upon completion, you are prompted for a delivery method: 

```
Certificates ready!
Select a delivery method
1. Display all
2. Export to file
3. Schedule delivery
Please enter a choice: 
```

Option `1` outputs all the certificates to the console in one go. If selecting option `2`, you are prompted for a filename to be used (excluding extension). The certificates are then written to the filename specified in `.txt `format:

```
------------------------------------------
Business Name:    One Example Business
Participant Name: John Smith
Course Name:      Mathematics
Date:             30-03-2024
Instructor Name:  Michael Bell
Custom Label:     My Value 1
------------------------------------------
 
------------------------------------------
Business Name:    Example Business Two
Participant Name: Ben Jones
Course Name:      English Language
Date:             20-03-2024
Instructor Name:  James Timms
Custom Label:     My 2nd Value
------------------------------------------
```

Option `3` only differs in that you are requested for a date and time the certificates should be sent, which is stored as the filename to indicate  when this would be done. 

#### Bulk Generation
Certificates can be generated in bulk by selecting option `2` at the `Business/+ Account Certificate Builder` menu.

You will be prompted to add a CSV containing the labels and values for the certificates you wish to generate. `example_cert_data.csv`) is included in the project root directory, which can be used as a template, but the generator will take any CSV with the labels as the first row and subsequent rows containing the data. Once the CSV has been placed in the root directory, you press enter to continue. 

After this has been done, you are prompted for the exact filename of your CSV to be entered. If not found or there is a problem reading the file, you will be prompted again. Assuming the file is found, you are presented with the same set of delivery methods as before and your certificates can be viewed, saved to a `.txt` file or scheduled for future delivery.

### Help

You can view FAQs or submit a support ticket (Business and Business+ accounts only) if you are encountering trouble using the platform.

#### Personal Account Help
```
Help
1. View FAQs
0. Exit
Please select a choice: 
```

Pressing `1` brings you to a list of a few FAQs that can attempt to help answer your question
```
Frequently Asked Questions
1. Are there any limits on how many certificates I can generate?
2. How do I add custom fields to certificates?
3. What are the different certificate delivery methods?
4. How can I generate certificates in bulk?
0. Return
Please select a choice: 
```

Selecting from one of the numbered choices displays the answer to the FAQ.

#### Business and Business+ Account Help
```
Help
1. View FAQs
2. Open a support ticket
0. Exit
Please select a choice: 
```

Pressing `2` will prompt you for a short message, which is then appended to a text file containing the email addresses (automatically obtained from the logged-in user) and messages. 

### Quiz Platform
TODO

# Skipped features
* Scheduled delivery doesn't actually schedule
* Fields can't be repositioned because it'd impractical for an application with no gui
* User details stored in plain text 
* File upload requires placing into project directory
* Support tickets aren't actually sent anywhere