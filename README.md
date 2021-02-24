# Assignment

* Steps to run the code *
1. Run `ReadScheduler.java`
2. On the Interactive Console, there will be three options
    - `Search flights` press 1 to search flights
    - `Print all flights` press 2 to print all flights (as present in source file)
    - `Exit` press 3 to exit from the program
3. There are some configurable parameters in `env.properties` file
    - `sourceFile` path to source file
    - `logFile` path to log file
    - `refreshPeriodInSeconds` to set the refresh period
    - `delimiter` delimiter used in source file (pipe)
    - `timeInMs1980` to ensure source file is loaded when the program starts

*Source file assumptions*
- already present data could be edited
- new row can be added anywhere (start, middle, end)
- lines with just white spaces could be present

*Logic for reading the file*
1. Last modified date would be used to decide whether to read the file or not.
2. Initially, last modified date would be set as 1980 (to ensure that file is read at least once)
3. If a file is modified, it's last modified date would be updated, this passes the test for reading the file next time

*Assumptions*
- Rows can be deleted from source file, this is handled by purging the Flights set everytime file is read

*Further improvements needed:*
- explore better way to handle deletions (instead of purging the set each time)
- refactor the code to better implement encapsulation
- add tests
