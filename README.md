# Database-Engine
 small database engine with support for B+ trees and R trees

 In this project, I built a small database engine with support for B+ trees and R trees. The required functionalities are 1) creating tables, 2) inserting tuples, 3) deleting tuples, 4) searching in tables linearly, 5) creating a B+ tree index, 6) searching using B+ tree index, 7) creating an R tree index, and 8) searching using an R tree index.

 Tables
  1) Each table/relation will be stored as pages on disk.
 2) Supported type for a table’s column is one of: java.lang.Integer, java.lang.String, java.lang.Double, java.lang.Boolean, java.util.Date and java.awt.Polygon
 Note: java.awt.Polygon will be used to store spatial data, while the other types are non- spatial. The user will be able to create an R tree index on any column whose type is java.awt.Polygon while for the remaining 5 types, the appropriate index is a B+ tree.
 3) Each table should have an additional column beside those specified by the user. The additional column must be called TouchDate and is initialized with the date/time of row insertion and updated with current date/time every time a row is updated.
  Pages
 4) A page has a predetermined fixed maximum number of rows (N). For example, if a table has 40000 tuples, and N=200, the table will be stored in 200 binary files.
 5) I used Java’s binary object file (.class) for emulating a page. A single page must be stored as a serialized Vector (java.util.Vector). Note that you can save/load any Java object to/from disk by implementing the java.io.Serializable interface.
 6) A single tuple should be stored in a separate object inside the binary file.
 7) I postponed the loading of a page until the tuples in that page are actually needed. Note that the purpose of using pages is to avoid loading the entire table’s content into memory. Hence, it defeats the purpose to load all pages upon program startup.
 8) If all the rows in a page are deleted, then you are required to delete that page. Do not keep around completely empty pages. In the case of insert, if you are trying to insert in a

 full page, shift one row down to the following page. Do not create a new page unless you are in the last page of the table and that last one was full.
 9)  Note that to prevent serializing an attribute, you will need to use the transient keyword in your attribute declaration. Read more about that here:
 https://en.wikibooks.org/wiki/Java_Programming/Keywords/transient
 Meta-Data File
 10) Each user table has meta data associated with it; number of columns, data type of columns, which columns have indices built on them.
 11) I stored the meta-data in a text file. This structure should have the following layout:
 Table Name, Column Name, Column Type, ClusteringKey, Indexed
 For example, if a user creates a table/relation CityShop, specifying several attributes with their types, etc... the file will be:
    Table Name, Column Name, Column Type, ClusteringKey, Indexed CityShop, ID, java.lang.Integer, True, False
 CityShop, Name, java.lang.String, False, False
 CityShop, X, java.lang.Double, False, True
 CityShop, Y, java.util.Double, False, True
 CityShop, Z, java.lang.Double, False, True
 CityShop, Specialization, java.lang.String, False, True CityShop, Address, java.lang.String, False, false
 The above meta data teaches that there are 1 table of 7 tuples (ID, Name, X,Y,Z, Specialization, Address). There are 4 indices created on this table CityShop.
 13) I used the metadata.csv file to learn about the types of the data being passed and verify it is of the correct type. So, do not treat metadata.csv as decoration!☺

 Indices
 14) I used reflection to load the data type and also value of a column, for example:
 strColType = "java.lang.Integer";
 strColValue = "100";
 Class class = Class.forName( strColType ); Constructor constructor = class.getConstructor( ....); ... = constructor.newInstance( );
 For more info on reflection, check this article:
 http://download.oracle.com/javase/tutorial/reflect/
   15) I used B+ trees to support creating primary and secondary dense indices.
 16) I used an R tree to support creating primary and secondary dense indices.
 17) Once a table is created, I do not need to create any page until the insertion of the first row/tuple.
 18) I updated existing relevant indices when a tuple is inserted/deleted.
 19) If a secondary index is created after a table has been populated, I have no option but to scan the whole table.
 20) Upon application startup; to avoid having to scan all tables to build existing indices, I should save the index itself to disk and load it when the application starts next time. 21) When a table is created, I do not need to create an index. An index will be created later on when the user requests that through a method call to createBTreeIndex or createRTreeIndex
 22) Note that once an index exists, it should be used in executing queries. Hence, if an index did not exist on a column that is being queried (select * from x = 20;), then it will be answered using linear scan on x, but if an index is created on x, then x’s index should be used to find if there are tuples with x=20. Hence, if a select is executed (by calling

 selectFromTable method below), and a column is referenced in the select that has been already indexed, then I should search in the index.
 23) Note that indices should be used in answering multi-dimension queries if an index has been created on any of columns used in the query, i.e. if the query is on Table1.column1 and Table1.column2, and an index has been created on Table.column1, then it should be used in answering the query.
 Required Methods/Class

 public void init( );  // or leave it empty if there is no code you want to
                         // execute at application startup
 public void createTable(String strTableName,
 String strClusteringKeyColumn,
 Hashtable<String,String> htblColNameType )
 throws DBAppException
 public void createBTreeIndex(String strTableName,
 String strColName) throws DBAppException
 public void createRTreeIndex(String strTableName,
 String strColName) throws DBAppException
 // following method inserts one row at a time
 public void insertIntoTable(String strTableName, Hashtable<String,Object> htblColNameValue)
                                                         throws DBAppException
 // updateTable notes:
 // htblColNameValue holds the key and new value
 // htblColNameValue will not include clustering key as column name // htblColNameValue enteries are ANDED together
 public void updateTable(String strTableName,
 String strClusteringKey, Hashtable<String,Object> htblColNameValue )
                                                       throws DBAppException

 // deleteFromTable notes:
 // htblColNameValue holds the key and value. This will be used in search // to identify which rows/tuples to delete.
 // htblColNameValue enteries are ANDED together
 public void deleteFromTable(String strTableName,
 Hashtable<String,Object> htblColNameValue) throws DBAppException
 public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators)
                                                   throws DBAppException
 Here is an example code that creates a table, creates an index, does few inserts, and a select;
 String strTableName = "Student";
 DBApp dbApp = new DBApp( );
 Hashtable htblColNameType = new Hashtable( ); htblColNameType.put("id", "java.lang.Integer"); htblColNameType.put("name", "java.lang.String"); htblColNameType.put("gpa", "java.lang.double"); dbApp.createTable( strTableName, "id", htblColNameType ); dbApp.createBIndex( strTableName, "gpa" );
 Hashtable htblColNameValue = new Hashtable( ); htblColNameValue.put("id", new Integer( 2343432 )); htblColNameValue.put("name", new String("Ahmed Noor" ) ); htblColNameValue.put("gpa", new Double( 0.95 ) ); dbApp.insertIntoTable( strTableName , htblColNameValue );
 htblColNameValue.clear( );
 htblColNameValue.put("id", new Integer( 453455 )); htblColNameValue.put("name", new String("Ahmed Noor" ) ); htblColNameValue.put("gpa", new Double( 0.95 ) ); dbApp.insertIntoTable( strTableName , htblColNameValue );
 htblColNameValue.clear( );
 htblColNameValue.put("id", new Integer( 5674567 )); htblColNameValue.put("name", new String("Dalia Noor" ) ); htblColNameValue.put("gpa", new Double( 1.25 ) ); dbApp.insertIntoTable( strTableName , htblColNameValue );
 htblColNameValue.clear( );
 htblColNameValue.put("id", new Integer( 23498 )); htblColNameValue.put("name", new String("John Noor" ) ); htblColNameValue.put("gpa", new Double( 1.5 ) ); dbApp.insertIntoTable( strTableName , htblColNameValue );
 htblColNameValue.clear( );
 htblColNameValue.put("id", new Integer( 78452 )); htblColNameValue.put("name", new String("Zaky Noor" ) ); htblColNameValue.put("gpa", new Double( 0.88 ) ); dbApp.insertIntoTable( strTableName , htblColNameValue );

 SQLTerm[] arrSQLTerms;
 arrSQLTerms = new SQLTerm[2]; arrSQLTerms[0] ._strTableName = "Student"; arrSQLTerms[0]._strColumnName= "name"; arrSQLTerms[0]._strOperator = "=";

 arrSQLTerms[0]._objValue = "John Noor";
 arrSQLTerms[1]._strTableName = "Student"; arrSQLTerms[1]._strColumnName= "gpa"; arrSQLTerms[1]._strOperator = "="; arrSQLTerms[1]._objValue = new Double( 1.5 );
 String[]strarrOperators = new String[1];
 strarrOperators[0] = "OR";
 // select * from Student where name = “John Noor” or gpa = 1.5;
 Iterator resultSet = dbApp.selectFromTable(arrSQLTerms , strarrOperators);
 25) For the parameters, the name documents what is being passed – for example htblColNameType is a hashtable with key as ColName and value is the Type.
 26) Operator Inside SQLTerm can either be >, >=, <, <=, != or =
 27) Operator between SQLTerm (as in strarrOperators above) are AND, OR, or XOR. 28) DBAppException is a generic exception to avoid breaking the test cases when they run. I customized the Exception by passing a different message upon creation.
 29) SQLTerm is a class with 4 attributes: String _strTableName, String _strColumnName, String _strOperator and Object _objValue
 30) Iterator is java.util.Iterator It is an interface that enables client code to iterate over the results row by row. Whatever object you return holding the result set, it should implement the Iterator interface.
 31) I checked on the passed types and do not just accept any type – otherwise, the code will crash will invalid input.
 32) not supporting SQL Joins in this mini-project.
 Directory Structure


 35) data directory contains the important metadata.csv which holds meta information about the user created tables. Also, it will store binary files storing user table pages, indices, and any other data related files needed to store.
 36) docs directory contains html files generated by running javadoc on the source code 37) src directory is a the parent directory of all java source files.
 38) classes directory is the parent directory of all java class files. When you run make all, the java executables should be generated in classes.
 39) libs directory contains any third-party libraries/jar-files/java-classes.
 40) config directory contains the important configuration DBApp.properties which holds a two parameters as key=value pairs
 MaximumRowsCountinPage = 200
 NodeSize = 15
 Where
  MaximumRowsCountinPage as the name indicates specifies the maximum number of rows in a page.
 NodeSize specifies the count of keys that could be stored in a single Node in a B+ tree or in an R tree.
 41) DBApp.properties file could be read using java.util.Properties class
