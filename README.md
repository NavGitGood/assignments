# Assignment

***Steps to run the code***
1. Run `Main.java`
2. On the Interactive Console, there will be three options
    - `Search flights` press 1 to search flights
    - `Print all flights` press 2 to print all flights (as present in source file)
    - `Exit` press 3 to exit from the program
3. If option 1 is selected, user would be prompted to
   - Enter Departure Location (Case insensitive) and press enter
   - Enter Arrival Location (Case insensitive) and press enter
   - Enter Flight Date (dd-mm-yyyy) and press enter (*result would be displayed at this point (if there is any) and user can also make another search*) 
4. There are some configurable parameters in `env.properties` file
    - `sourceFile` path to source file
    - `logFile` path to log file
    - `refreshPeriodInSeconds` to set the refresh period
    - `delimiter` delimiter used in source file (pipe)
    - `timeInMs1980` to ensure source file is loaded when the program starts

***Source file assumptions***
- already present data could be edited
- new row can be added anywhere (start, middle, end)
- lines with just white spaces could be present

***Logic for reading the file***
1. Last modified date would be used to decide whether to read the file or not.
2. Initially, last modified date would be set as 1980 (to ensure that file is read at least once)
3. If a file is modified, it's last modified date would be updated, this passes the test for reading the file next time

***Assumptions***
- duplicate rows could be present in source file, so a `HashSet` is used instead of `ArrayList`
- rows can be deleted from source file, this is handled by purging the Flights set everytime file is read
- a flight detail would be returned in search result only when :
   - depLoc, arrLoc and flightDate exactly matches
   - seat is available

***Further improvements which could be done:***
- explore better way to handle deletions (instead of purging the set each time)
- refactor the code to better implement encapsulation
- add tests
- use better date validation (*current implementation is to showcase the use of functional interfaces which we learnt in a screening workshop*). 

***Highlights***
1. Result is formatted. Some examples:
   - fare as INR 4,900 instead of just 4900
   - date in dd-MM-yyyy format instead of datetime
   - seat availability as Yes/No instead of Y/N
2. File is read (at given interval) only if there was a modification
3. Logging is used to determine when the file was read
4. Use of Configurable parameters
5. Use of functional interfaces (for input validation), lambda functions and method references
6. Source file sanitization
   - ignoring lines with just white spaces
   - ignoring duplicate records
7. User Input is Case-Insensitive
8. Console output is tabular