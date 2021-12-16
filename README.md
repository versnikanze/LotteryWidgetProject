# LotteryWidgetProject

## Deployment Lottery Widget

- Download and install Java SE Development Kit
  17.0.1 (https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
- Download and install PostgresSQL (https://www.postgresql.org/download/)
- Set the password for the user postgres as 1234
- Create a new database with the name loterry_widget_db
- Execute the create_database.sql query in the file to create the database
- Open Intellij idea (install if needed)
- Go to vcs -> get from version control
- Select your desired location and set the url to https://github.com/versnikanze/LotteryWidgetProject
- Press clone
- Press trust project
- Download and unzip Tomcat 10.0.14 (https://tomcat.apache.org/download-10.cgi)
- In Intellij idea press add configuration
- Press add new configuration
- Select tomcat server -> local
- Under application server put the path to the Tomcat folder
- Under deployment select LotteryWidget_war_exploded as the artifact to be deployed
- Press ok
- Build the project
- Edit the tomcat configuration. Under deployment delete the application context
- Press ok

## Testing Lottery Widget

- Run the Tomcat server in IntelliJ
- Webpage opens up on its own in your preferred browser (http://localhost:8080/)
- Select input field contestant name
- Type in a valid name
- Select input field picked number
- Type in a valid number between 1 and 30
- Press the submit button (message appears that the contestant was registered)
- Every 30 seconds the winners will be picked and the table with them updated

## Testing bonus points submission

- No deployment needed
- Open bonus_point_submission.html located in src/main/webapp with desired browser
- Animation will start on its own
- Press and hold the slow motion button to slow down the movement of the rectangle
- Release the slow motion button to speed up the animation again