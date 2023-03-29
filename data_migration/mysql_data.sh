#!/bin/bash
MYSQL_LOCATION='C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin'
cd "$MYSQL_LOCATION"
./mysql --local-infile=1 "--user=root" "--password=1111" --execute="CREATE DATABASE schedule;"
./mysql --local-infile=1 "--user=root" "--password=1111" "--database=schedule" --execute="USE schedule;"
./mysql --local-infile=1 "--user=root" "--password=1111" "--database=schedule" --execute="CREATE TABLE  subjects ( lessonNumber int, subjectName1 nvarchar(255), teacher1 nvarchar(255), room1 int, subjectName2 nvarchar(255), teacher2 nvarchar(255), room2 int, weekday nvarchar(255), groupName nvarchar(255));"
./mysql --local-infile=1 "--user=root" "--password=1111" "--database=schedule" --execute="LOAD DATA LOCAL INFILE 'C:/Users/Roman_Mysiuk/Desktop/data1_comma.txt' INTO TABLE subjects CHARACTER SET UTF8 FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';"