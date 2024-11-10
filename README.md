# Project Summary :
It is an project where we have automated Different scenarios using Selenium and TestNG. We have followed the Page Object Model (POM) for designing and developing the project.
Also done email reading using Rest Assured.
## Technology used:
- Language: Java
- Build System: Gradle
- Automation tool and framework: Selenium and TestNG, Rest Assured
- Data manipulation: Simple JSON and CSV Parser

## Peoject Description -

1. Visit the site https://dailyfinance.roadtocareer.net/. Register a new user. ANd also check the confirmation email is received or not.
2. Now click on the reset password link. Write  negative test cases and assert. 
3. Now Input valid gmail account you have registered and and click on send reset link button
4. Now retrieve password reset mail from your gmail and set new password
5. Now login with the new password to ensure login successful and login with old password fails.
6. Update the user's profile image and also update gmail of the user.
7. Now logout and login with the updated gmail account. Assert that using new email login is successful and using previous email login is failed.
8. Add a cost/expenditure from a CSV file. Create a CSV file with 5 rows, each containing different item names, amounts, quantity, purchase dates, months, and remarks. This test will loop 5 times, as there are 5 data sets in the CSV.
9. Assert 5 items are showing on the item list.
10. Print the total cost and assert it against your expected total sum of the amounts.
11. Search for an item by name from the list and assert that the total cost matches the item's price.
12. Log in as admin (pass admin credentials from the terminal)
13. Search by the updated gmail and Assert that updated user email credentials are is showing on admin dashboard.
14. Create 2 test suites: one for regression and one for smoke testing.

## How to run this project
- Clone the project
- Open the project from IntellIJ; File>Open>Select and expand folder>Open as project
- Hit this command: `gradle clean test -PsuiteName="regressionSuite.xml"` to run the regression suite or `gradle clean test -PsuiteName="smokeSuite.xml"` to run the smoke suite.
## Generate Allure report:

- ``` allure generate allure-results --clean -output ``` 
-   ``` allure serve allure-results ```


## Output -
### Smoke Suite Report :
![image](https://github.com/user-attachments/assets/3f0d4edc-1716-4fe4-b540-ed2634073862)
![image](https://github.com/user-attachments/assets/0e47404a-1d02-4905-9a78-c3b434f91754)

 ### Regression Suite Report :
 ![image](https://github.com/user-attachments/assets/8365adc8-f61e-4d1d-aebb-db87f17baaba)
![image](https://github.com/user-attachments/assets/6aa8f5f6-2b7e-4f33-9b4a-8f0c068599ef)
![image](https://github.com/user-attachments/assets/ae1bac8b-197e-470f-aafd-def4461ced64)

### Excel Test Case Report
- [Click Here To See The Test Case Excel Report](https://docs.google.com/spreadsheets/d/1r-2_TqiQ_RXdBcFvSuvhAYKNsxrNkuxZmJHiVHEvxko/edit?usp=sharing)

### Output Video Link :
-  [Click Here To see the output video of Smoke Suite Automation Script ](https://drive.google.com/file/d/12um3Q8S9yi5vAdu84HVxBL7EbaR8YfeV/view?usp=sharing)
- [Click Here To see the output video of Regression Suite Automation Script ](https://drive.google.com/file/d/1lId0Ne5SLCDUqD8mv7lz0wPfBm46r8Po/view?usp=sharing)
