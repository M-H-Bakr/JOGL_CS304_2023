--Create state table (for sampling only 5 city from the 5 regions,
--Could use a check constraint on the name to be one of the current 50 states):
Create Table State(State_Name Varchar2(25) Constraint X1 Primary Key, Region Varchar2(20) Constraint X2 
Check(Region In('Northeast', 'Midwest', 'Southeast', 'Southwest','West')));

--Create congress_person table (for sampling only 15 congress persons):
Create Table Congress_Person (Person_Id Number(3) Constraint X3 Primary Key, Person_Name Varchar2(25) Constraint X4 Not Null,
District Varchar2(25) Constraint X5 Not Null, Start_Date Date Constraint X6 Not Null, Party Varchar2(25) Constraint X7
Check(Party In('Republican','Democrat', 'Independent','Other')),
State_Name Varchar2(25) Constraint X8 References State(State_Name));

--Create bill table (for sampling only 4 bills):
Create Table Bill(Bill_Name Varchar2(25) Constraint X9 Primary Key, Date_Of_Vote Date Constraint X10 Not Null,
Passed_Or_Failed Varchar2(25) Constraint X11 Check (Passed_Or_Failed In('Yes', 'No')) );

--Create voting_sponsorship table that is going to have 60 rows:
Create Table Voting_Sponsorship (Person_Id Number(3) Constraint X12 References Congress_Person(Person_Id),
Bill_Name Varchar2(25) Constraint X13 References Bill(Bill_Name), Voting_Status Varchar2(25) Constraint X14
Check(Voting_Status In('Yes', 'No', 'Abstain', 'Absent')),
Sponsorship Varchar2(25) Constraint X15 Check (Sponsorship In('Yes', 'No')), Constraint X16 Primary Key(Person_Id, Bill_Name));

--Insert dynamically into state table:
Insert Into State
Values('&state_name', '&region');

--Insert dynamically into congress_person table:
Insert Into Congress_Person
Values (&person_Id, '&person_name', '&district','&date', '&party', '&state_name');

--Insert dynamically into bill table:
Insert Into Bill
Values ('&bill_name', '&date_of_vote', '&passed_or_failed');

--Insert dynamically into voting_sponsorship:
Insert Into Voting_Sponsorship
Values (&person_Id, '&bill_name', '&voting_status', '&sponsor_ship');

--Create the view:
Create Or Replace View SponsoredBills
As Select Bill_Name "Bill", Person_Id "Congresspersons"
From Voting_Sponsorship
Where Sponsorship = 'Yes'
With Read Only;
