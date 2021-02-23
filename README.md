# Assignment

*Source file assumptions*
- already present data could be edited
- new row can be added anywhere (start, middle, end)
- lines with just white spaces could be present

*Logic for reading the file*
1. Last modified date would be used to decide whether to read the file or not.
2. Initially, last modified date would be set as 1980 (to ensure that file is read at least once)
3. If a file is modified, it's last modified date would be updated, this passes the test for reading the file next time