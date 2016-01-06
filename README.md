This project is basically a demonstration of Java Swing Application. The back end of this desktop application is designed following model view controller (MVC) and integrated different aspects which target for medium level industrial production. Major feature of this project
 
The project is converted to maven and most interestingly I integrated spring with this swing project. Now many features of swing can easily be used with this application. Here I need to mention, to facilitate the portability I used derby as embedded database.

Different Swing Object: In this project I used simple JTextField to complex JTable and JTree structure. Almost all type of GUI interface is used here.

Data Pool Connection: To distribute the work load and optimize the database connection I have integrate here DHCP database pool connection.

Middle layer of Data Manipulation: Between table view and database, a middle layer is designed for  data manipulation. User activity is logged to this layer until saved button is clicked and then bunch execution is take place. This will significantly reduce the database and network load.

Tree View: Custom tree view renderer is used to visually represent the table data. This can be more customizable and use for more complex operation.

Import and Export file: Table data is 'Serializable' and can be converted to binary format for export. Same data can be import for reverse operation. 
