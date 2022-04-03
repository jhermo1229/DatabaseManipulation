# Grade-Application
Grade application android app using the following:

Navigation
Fragments
Recyclerview
SQLite as the database

******* Application requirements *********

Creating your ‘Grade Entry’ functionality
• Design the fragment to input the First Name, Last Name, Course, Credits and Marks.
• Use EditText for First Name, Last Name and Marks
• Use ListView for Course. Populate the list view with 4 courses PROG 8480, PROG 8470, PROG 8460, and PROG 8450
• Use4 RadioButtons for Credits 1, 2, 3, & 4
• Use a ‘Submit’ button to enter the record into the SQLite table. ID should be auto-generated. 

A toast message will appear indicating that the data was successfully added.
Creating your ‘View Grades’ functionality
• Design the fragment with a RecyclerView to show all the records from the SQLite table
• A toast message will appear if no record is found in the table
Creating your ‘Search’ functionality
• Design the fragment with two radio buttons as below:


• If the option ‘Id’ is selected, show an EditText and enter the Id. Search for the record with this Id in the database and
display the record, when a button is clicked. If record not found, toast suitable message.
• If the option ‘Program Code’ is selected, show a List View with the 4 program codes. Search for all the records with the
selected program code in the database and display the records in a Recycler View, when a button is clicked. If record not
found toast suitable message. 
