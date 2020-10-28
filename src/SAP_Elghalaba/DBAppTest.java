package SAP_Elghalaba;
import java.awt.Polygon;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

public class DBAppTest {
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public static void main(String[] args) throws IOException, DBAppException, ParseException {
		
		String strTableName = "Student";
		DBApp dbApp = new DBApp( );
		
		Polygon poly = new Polygon();
		poly.addPoint(130, 120);
		poly.addPoint(100, 150);
		poly.addPoint(10, 190);
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
		
		Hashtable htblColNameType = new Hashtable( );
		htblColNameType.put("id", "java.lang.integer");
		htblColNameType.put("name", "java.lang.string");
		htblColNameType.put("gpa", "java.lang.double");
		
		dbApp.createTable( strTableName, "id", htblColNameType );
		
		Hashtable htblColNameValue = new Hashtable( );
		htblColNameValue.put("id", new Integer( 2343432 ));
		htblColNameValue.put("name", new String("Ramy Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 453455 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("name", new String("Dalia Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.25 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 23498 ));
		htblColNameValue.put("name", new String("John Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.5 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 78452 ));
		htblColNameValue.put("name", new String("Zaky Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.88 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		dbApp.displayTable(strTableName);
		
/********************************************************************************************************************************/
		//create another table : No problem
//		String strTableName2="Professor";
//		Hashtable htblColNameType2 = new Hashtable( );
//		htblColNameType2.put("id", "java.lang.integer");
//		htblColNameType2.put("name", "java.lang.string");
//		htblColNameType2.put("salary", "java.lang.double");
//		dbApp.createTable( strTableName2, "id", htblColNameType2 );
//		
//		Hashtable htblColNameValue2 = new Hashtable( );
//		htblColNameValue2.put("id", new Integer( 232 ));
//		htblColNameValue2.put("name", new String("Ramy Mahfouz" ) );
//		htblColNameValue2.put("salary", new Double( 87523.5 ) );
//		dbApp.insertIntoTable( strTableName2 , htblColNameValue2 );
//		dbApp.displayTable(strTableName2);
/********************************************************************************************************************************/
		//create another table with same name as existing one : No problem
//		Hashtable x = new Hashtable( );
//		x.put("id", "java.lang.Integer");
//		dbApp.createTable( "Student", "id", x );
/********************************************************************************************************************************/
		//create another table with null name : No problem
//		Hashtable y = new Hashtable( );
//		y.put("id", "java.lang.Integer");
//		dbApp.createTable( null, "id", y );
/********************************************************************************************************************************/
		//create another table with no name : No problem
//		Hashtable z = new Hashtable( );
//		z.put("id", "java.lang.Integer");
//		dbApp.createTable( "", "id", z );
/********************************************************************************************************************************/
		//update int :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343431 ));
//		htblColNameValue.put("name", new String("John Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.5 ) );
//		dbApp.updateTable( strTableName , "23498" , htblColNameValue );
//		dbApp.displayTable(strTableName);
		//another example
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 5674568 ));
//		htblColNameValue.put("name", new String("Ramy Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.25 ) );
//		dbApp.updateTable( strTableName , "2343432" , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//update String :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 23498 ));
//		htblColNameValue.put("name", new String("Salma Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.5 ) );
//		dbApp.updateTable( strTableName , "Ahmed Noor" , htblColNameValue );
//		dbApp.displayTable(strTableName);
		//another example
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 5674568 ));
//		htblColNameValue.put("name", new String("Zeze Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.25 ) );
//		dbApp.updateTable( strTableName , "Ramy Noor" , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//update Double :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("gpa", new Double( 1.6 ) );
//		dbApp.updateTable( strTableName , "0.95" , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//update Double,String,Int :No problem (solved)
//		htblColNameValue.clear( );
//		htblColNameValue.put("gpa", new Double( 1.7 ) );
//		dbApp.updateTable( strTableName , "1.6" , htblColNameValue );
//		dbApp.displayTable(strTableName);
		//example for String
//	    htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("Fady Noor" ) );
//		dbApp.updateTable( strTableName , "Zb" , htblColNameValue );
//		dbApp.displayTable(strTableName);
		//example for int
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 1234 ));
//		dbApp.updateTable( strTableName , "56745688" , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//Created another table to test date/polygon/boolean for simplicity
		String strTableName3="University";
		Hashtable htblColNameType3 = new Hashtable( );
		htblColNameType3.put("active", "java.lang.Boolean");
		htblColNameType3.put("cdate", "java.util.date");
		htblColNameType3.put("area", "java.awt.polygon");
		dbApp.createTable( strTableName3, "area", htblColNameType3 );
		
		Hashtable htblColNameValue3 = new Hashtable( );
		htblColNameValue3.put("active", new Boolean(false));
		htblColNameValue3.put("cdate", sdformat.parse("1989/02/04") );
		Polygon poly3 = new Polygon();
		poly3.addPoint(130, 120);
		poly3.addPoint(100, 150);
		poly3.addPoint(10, 190);
		htblColNameValue3.put("area", poly3 );
		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
		
		htblColNameValue3.clear( );
		htblColNameValue3.put("active", new Boolean(false));
		htblColNameValue3.put("cdate", sdformat.parse("1975/05/08") );
		Polygon poly4 = new Polygon();
		poly4.addPoint(50, 20);
		poly4.addPoint(500, 30);
		poly4.addPoint(15, 150);
		htblColNameValue3.put("area", poly4 );
		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
		
		htblColNameValue3.clear( );
		htblColNameValue3.put("active", new Boolean(true));
		htblColNameValue3.put("cdate", sdformat.parse("2000/01/30") );
		Polygon poly5 = new Polygon();
		poly5.addPoint(10,20);
		poly5.addPoint(30,70);
		poly5.addPoint(300,360);
		htblColNameValue3.put("area", poly5 );
		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
		
		htblColNameValue3.clear( );
		htblColNameValue3.put("active", new Boolean(true));
		htblColNameValue3.put("cdate", sdformat.parse("2003/08/15") );
		Polygon poly6 = new Polygon();
		poly6.addPoint(0,100);
		poly6.addPoint(330,140);
		poly6.addPoint(5,45);
		htblColNameValue3.put("area", poly6 );
		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
		
		htblColNameValue3.clear( );
		htblColNameValue3.put("active", new Boolean(true));
		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20") );
		Polygon poly7 = new Polygon();
		poly7.addPoint(505,450);
		poly7.addPoint(370,5);
		poly7.addPoint(35,50);
		htblColNameValue3.put("area", poly7 );
		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
		
		dbApp.displayTable(strTableName3);
		
/********************************************************************************************************************************/
		//update Boolean :No problem (Solved)
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		dbApp.updateTable( strTableName3 , "true" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//Update Date :No problem (Solved)
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("cdate", sdformat.parse("1999/08/20") );
//		dbApp.updateTable( strTableName3 , "1975/05/08" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//inserting invalid columns types :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new String("x"));
//		htblColNameValue3.put("cdate", sdformat.parse("2017/12/23") );
//		htblColNameValue3.put("area", poly7 );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//inserting invalid columns names :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		htblColNameValue3.put("cdate", sdformat.parse("2017/12/23") );
//		htblColNameValue3.put("areaaaaa", poly7 );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//inserting null records :No problem
//		htblColNameValue3.clear( );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//inserting records to not existing table :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20") );
//		htblColNameValue3.put("area", poly7 );
//		dbApp.insertIntoTable( "TA" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//updating invalid columns types :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new String("x"));
//		htblColNameValue3.put("cdate", sdformat.parse("2017/12/23") );
//		htblColNameValue3.put("area", poly7 );
//		dbApp.updateTable( strTableName3 , "false" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//updating invalid columns names :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		htblColNameValue3.put("cdate", sdformat.parse("2017/12/23") );
//		htblColNameValue3.put("areaaaaa", poly7 );
//		dbApp.updateTable( strTableName3 , "false" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//updating records with no change :No problem
//		htblColNameValue3.clear( );
//		dbApp.updateTable( strTableName3 , "false" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
		//another example
//		htblColNameValue.clear( );
//		dbApp.updateTable( strTableName , "0" , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//updating in non-existing table :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		htblColNameValue3.put("cdate", sdformat.parse("2017/12/23") );
//		htblColNameValue3.put("area", poly7 );
//		dbApp.updateTable( "x" , "false" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//deleting from table (tested with many conditions and examples not listed) :No problem
		//updating to remove page and move the record to another page :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		dbApp.displayTable(strTableName3);
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20"));
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		dbApp.displayTable(strTableName3);
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("cdate", sdformat.parse("2007/08/20") );
//		dbApp.updateTable( strTableName3 , "2000/01/30" , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//deleting from non-existing table :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		dbApp.deleteFromTable("x", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//displaying non-existing table :No problem
//		dbApp.displayTable("x");
/********************************************************************************************************************************/
		//delete polygon :No problem (Solved)
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("area",poly5);
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		dbApp.displayTable(strTableName3);
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("area",poly6);
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//delete with invalid column name :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("x", new Boolean(false));
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//delete with invalid column type :No problem
//				htblColNameValue3.clear( );
//				htblColNameValue3.put("area", new Boolean(false));
//				dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//				dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//insert then delete then insert then delete then insert(int):No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 152656 ));
//		htblColNameValue.put("name", new String("John Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.5 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("John Noor"));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 152656 ));
//		htblColNameValue.put("name", new String("Samy Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer(453455));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 100000 ));
//		htblColNameValue.put("name", new String("Fady Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//insert then delete then insert then delete then delete page then insert(String):No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 152656 ));
//		htblColNameValue.put("name", new String("John Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.5 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("John Noor"));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 152656 ));
//		htblColNameValue.put("name", new String("Samy Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer(453455));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer(5674567));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 100000 ));
//		htblColNameValue.put("name", new String("Fady Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//update then delete(String) :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343431 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.5 ) );
//		dbApp.updateTable( strTableName , "John Noor" , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("Ahmed Noor"));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//update then insert(String) :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("Zeze Noor" ) );
//		dbApp.updateTable( strTableName , "Dalia Noor" , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 100000 ));
//		htblColNameValue.put("name", new String("Abdo Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//insert to shift all pages and create new page(String) :No problem
		//page in the middle have places and insert to shift : No problem
		//insert in the middle to shift and create page :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 590280 ));
//		htblColNameValue.put("name", new String("Zeze Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 100000 ));
//		htblColNameValue.put("name", new String("Amr Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("Zaky Noor"));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 218115 ));
//		htblColNameValue.put("name", new String("Abdo Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 89469 ));
//		htblColNameValue.put("name", new String("Carla" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//delete first page then insert the smallest record :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("Ahmed Noor"));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("Dalia Noor"));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 89469 ));
//		htblColNameValue.put("name", new String("Carla" ) );
//		htblColNameValue.put("gpa", new Double( 1.4 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//delete boolean :No problem (Solved)
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//delete date :No problem (Solved)
//		htblColNameValue3.put("active", new Boolean(false));
//		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
//
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("cdate", sdformat.parse("1997/08/20"));
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//delete int :No problem (Solved)
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453455 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453456 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453456 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453457 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453457 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453458 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453458 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453459 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("Ahmed Noor" ));
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//testing touch date :No problem
//		for(int i=0;i<=100000;i++) {
//			for(int j=0;j<=100000;j++) {
//				for(int k=0;k<=10000;k++) {
//				}
//			}
//		}
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 1234 ));
//		dbApp.updateTable( strTableName , "5674567" , htblColNameValue );
//		dbApp.displayTable(strTableName);
//		for(int i=0;i<=100000;i++) {
//			for(int j=0;j<=100000;j++) {
//				for(int k=0;k<=10000;k++) {
//				}
//			}
//		}
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 65781 ));
//		htblColNameValue.put("name", new String("Fady Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.92 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		dbApp.displayTable(strTableName);
/********************************************************************************************************************************/
		//inserting null clustering key :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("cdate", sdformat.parse("2017/12/23") );
//		htblColNameValue3.put("area", poly7 );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//update polygon :No problem
		//my 5 polygon insertions sorted : 	//"(130,120),(100,150),(10,190)"
											//"(10,20),(30,70),(300,360)"
											//"(0,100),(330,140),(5,45)"
											//"(50,20),(500,30),(15,150)"
											//"(505,450),(370,5),(35,50)"
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(100,20);
//		poly8.addPoint(30,400);
//		poly8.addPoint(500,60);
//		poly8.addPoint(70,800);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(200,300);
//		poly8.addPoint(500,600);
//		poly8.addPoint(700,800);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(5,5);
//		poly8.addPoint(10,10);
//		poly8.addPoint(0,20);
//		poly8.addPoint(20,0);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(10,10);
//		poly8.addPoint(20,20);
//		poly8.addPoint(30,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,400);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,1000);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,50);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);	
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,300);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(130,120),(100,150),(10,190)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,1000);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(10,20),(30,70),(300,360)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(10,10);
//		poly8.addPoint(20,20);
//		poly8.addPoint(30,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(10,20),(30,70),(300,360)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,400);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(10,20),(30,70),(300,360)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(200,300);
//		poly8.addPoint(500,600);
//		poly8.addPoint(700,800);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(10,20),(30,70),(300,360)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,50);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(10,20),(30,70),(300,360)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,300);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(0,100),(330,140),(5,45)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,50);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(0,100),(330,140),(5,45)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(10,10);
//		poly8.addPoint(20,20);
//		poly8.addPoint(30,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(0,100),(330,140),(5,45)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,400);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(0,100),(330,140),(5,45)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,1000);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(0,100),(330,140),(5,45)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,300);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(50,20),(500,30),(15,150)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,50);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(50,20),(500,30),(15,150)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(10,10);
//		poly8.addPoint(20,20);
//		poly8.addPoint(30,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(50,20),(500,30),(15,150)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,400);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(50,20),(500,30),(15,150)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,1000);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(50,20),(500,30),(15,150)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);	
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,300);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(505,450),(370,5),(35,50)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(10,70);
//		poly8.addPoint(50,50);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(505,450),(370,5),(35,50)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(10,10);
//		poly8.addPoint(20,20);
//		poly8.addPoint(30,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(505,450),(370,5),(35,50)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(500,50);
//		poly8.addPoint(400,400);
//		poly8.addPoint(300,30);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(505,450),(370,5),(35,50)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
		//another test
//		htblColNameValue3.clear( );
//		Polygon poly8 = new Polygon();
//		poly8.addPoint(50,100);
//		poly8.addPoint(200,300);
//		poly8.addPoint(500,600);
//		poly8.addPoint(700,800);
//		htblColNameValue3.put("area", poly8 );
//		dbApp.updateTable(strTableName3, "(505,450),(370,5),(35,50)", htblColNameValue3);
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//testing binary searching insertion after some deletions with date :No problem
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		htblColNameValue3.put("cdate", sdformat.parse("1976/05/08") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("2001/01/30") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("2004/08/15") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("1998/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("1999/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("2002/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(false));
//		htblColNameValue3.put("cdate", sdformat.parse("2005/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//				
//		dbApp.displayTable(strTableName3);
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("cdate", sdformat.parse("2004/08/15") );
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("cdate", sdformat.parse("1998/08/20") );
//		dbApp.deleteFromTable(strTableName3, htblColNameValue3);
//		
//		dbApp.displayTable(strTableName3);
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("2019/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		dbApp.displayTable(strTableName3);
//		
//		htblColNameValue3.clear( );
//		htblColNameValue3.put("active", new Boolean(true));
//		htblColNameValue3.put("cdate", sdformat.parse("2018/08/20") );
//		dbApp.insertIntoTable( strTableName3 , htblColNameValue3 );
//		
//		dbApp.displayTable(strTableName3);
/********************************************************************************************************************************/
		//testing binary searching insertion after some deletions with int :No problem
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("A" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("B" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("C" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("D" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("E" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("F" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("G" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("D" ) );
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("name", new String("F" ) );
//		dbApp.deleteFromTable(strTableName, htblColNameValue);
//		
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("X" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		dbApp.displayTable(strTableName);
//		
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("Y" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		dbApp.insertIntoTable( strTableName , htblColNameValue );
//		
//		dbApp.displayTable(strTableName);
	
	}

}
