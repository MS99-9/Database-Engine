package SAP_Elghalaba;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;


public class DBApp {

	@SuppressWarnings("rawtypes")
	Vector tables;
	int maximumPageSize;

	public DBApp() throws IOException {
		init();
	}

	@SuppressWarnings( "rawtypes")
	public void init() {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter("D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\metadata.csv");
			csvWriter.append("Table Name");
			csvWriter.append(",");
			csvWriter.append("Column Name");
			csvWriter.append(",");
			csvWriter.append("Column Type");
			csvWriter.append(",");
			csvWriter.append("Clustering Key");
			csvWriter.append(",");
			csvWriter.append("Indexed");

			csvWriter.append("\n");
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tables = new Vector();
		
		File properties = new File("config/DBApp.properties");
		if(!properties.exists()){
			Properties p=new Properties();  
			p.setProperty("MaximumRowsCountinPage","200");  
			p.setProperty("NodeSize","15");  
		}
		
		 FileReader reader;
		try {
			reader = new FileReader("config/DBApp.properties");
			Properties p=new Properties();  
			 p.load(reader);
			 //System.out.println(p.getProperty("MaximumRowsCountinPage")); 
			 maximumPageSize=Integer.parseInt(p.getProperty("MaximumRowsCountinPage"));
			 //System.out.println(maximumPageSize);
		} catch (IOException e) {
			e.printStackTrace();
		}   
		   
		 
		
//		File config = new File("config/DBApp.config");
//		if(!config.exists()){
//			PrintWriter  pw = new PrintWriter(config);
//			pw.println("MaximumRowsCountinPage=200");
//			pw.println("NodeSize=15");
//			pw.flush();
//			pw.close();
//		}
//		BufferedReader br = new BufferedReader(new FileReader("config/DBApp.config"));
//		String MaximumRowsCountinPage = br.readLine();
//		StringTokenizer st = new StringTokenizer(MaximumRowsCountinPage,"=");
//		st.nextToken();
//		maximumPageSize=Integer.parseInt(st.nextToken());
		//System.out.println(maximumPageSize);
	}

	@SuppressWarnings("unchecked")
	public void createTable(String strTableName, String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType) throws DBAppException {

		if (strTableName == null||strTableName.equals("")) {
			throw new DBAppException("You should set name for the table");
		}
		if (tables.contains(strTableName)) {
			throw new DBAppException("Table with this name already exists");
		}

		tables.add(strTableName);
		try {
			Table t = new Table(strTableName, maximumPageSize, strClusteringKeyColumn, htblColNameType);
			serializeTable(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue)
			throws DBAppException {

		if (strTableName == null || !(tables.contains(strTableName))) {
			throw new DBAppException("This table does not exist");
		}

		Table t = null;
		t = deserialize(strTableName);
		boolean valuenotnull = false;

		// System.out.println(coloumnsValid(t, htblColNameValue));

		try {
			if (!(coloumnsValid(t, htblColNameValue))) {
				throw new DBAppException("Invaild coloumns");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Set<String> types = t.htblColNameType.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

			if (!(htblColNameValue.get(i) == null)) {
				valuenotnull = true;
			}
		}
		if (!valuenotnull) {
			throw new DBAppException("Cannot insert null records");
		}
		
		try {
			t.insert(tuple);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(tuple);
		serializeTable(t);

	}

	private boolean coloumnsValid(Table t, Hashtable<String, Object> htblColNameValue) throws IOException {
		
		
		
		for (String s : htblColNameValue.keySet()) {
			//System.out.println(s);

			if (!t.htblColNameType.keySet().contains(s)) {
				return false;
			}

			if (htblColNameValue.get(s) != null) {

//				 System.out.println(htblColNameValue.get(s));
//				System.out.println(t.htblColNameType.get(s));
				
				BufferedReader br = new BufferedReader(new FileReader("D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\metadata.csv"));
				String line = br.readLine();
				String type = "";
				while (line != null) {
					String[] info = line.split(",");
					if (info[0].equals(t.tableName)) {
						if (info[1].equalsIgnoreCase(s)) {
							type = info[2];
							break;
						}
					}

					line = br.readLine();
				}
				br.close();
				type = type.toLowerCase();
				//System.out.println(type);
				//System.out.println(htblColNameValue.get(s));
				switch (type) {
				case "java.lang.double":
					if (!(htblColNameValue.get(s) instanceof Double))
						return false;
					break;
				case "java.lang.string":
					if (!(htblColNameValue.get(s) instanceof String))
						return false;
					break;
				case "java.lang.integer":
					if (!(htblColNameValue.get(s) instanceof Integer))
						return false;
					break;
				case "java.lang.boolean":
					if (!(htblColNameValue.get(s) instanceof Boolean))
						return false;
					break;
				case "java.util.date":
					if (!(htblColNameValue.get(s) instanceof Date))
						return false;
					break;
				case "java.awt.polygon":
					if (!(htblColNameValue.get(s) instanceof Polygon))
						return false;
					break;
				default:
					return false;
				}



			}
		}

//		for (String s : htblColNameValue.keySet()) {
//
//			if (!t.htblColNameType.keySet().contains(s)) {
//				return false;
//			}
//
//			if (htblColNameValue.get(s) != null) {
//
////				 System.out.println(htblColNameValue.get(s));
////				System.out.println(t.htblColNameType.get(s));
//
//				if (t.htblColNameType.get(s).equals("java.awt.polygon")) {
//					if (!(htblColNameValue.get(s) instanceof Polygon))
//						return false;
//				} else if (t.htblColNameType.get(s).equals("java.util.date")) {
//					if (!(htblColNameValue.get(s) instanceof Date))
//						return false;
//				} else if (t.htblColNameType.get(s).equals("java.lang.double")) {
//					if (!(htblColNameValue.get(s) instanceof Double))
//						return false;
//				} else if (t.htblColNameType.get(s).equals("java.lang.string")) {
//					if (!(htblColNameValue.get(s) instanceof String))
//						return false;
//				} else if (t.htblColNameType.get(s).equals("java.lang.integer")) {
//					if (!(htblColNameValue.get(s) instanceof Integer))
//						return false;
//				} else if (t.htblColNameType.get(s).equals("java.lang.Boolean")) {
//					if (!(htblColNameValue.get(s) instanceof Boolean))
//						return false;
//				}
//				else
//					return false;
//
//			}
//		}
		return true;
	}

	// updateTable notes:
	// htblColNameValue holds the key and new value
	// htblColNameValue will not include clustering key as column name //
	// htblColNameValue enteries are ANDED together

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateTable(String strTableName, String strClusteringKey, Hashtable<String, Object> htblColNameValue)
			throws DBAppException {
		
		if (strTableName == null || !(tables.contains(strTableName))) {
			throw new DBAppException("This table does not exist");
		}
		
		Table t = null;
		t = deserialize(strTableName);
		
		// System.out.println(coloumnsValid(t, htblColNameValue));

		try {
			if (!(coloumnsValid(t, htblColNameValue))) {
				throw new DBAppException("Invaild coloumns");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
				
		Set<String> types = t.htblColNameType.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));
			
		}
		try {
			t.update(strClusteringKey, htblColNameValue);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		// System.out.println(tuple);
		serializeTable(t);
	}

	public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
		
		if (strTableName == null || !(tables.contains(strTableName))) {
			throw new DBAppException("This table does not exist");
		}
		
		Table t = null;
		t = deserialize(strTableName);
		try {
			if (!(coloumnsValid(t, htblColNameValue))) {
				throw new DBAppException("Invaild coloumns");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			t.delete(htblColNameValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		serializeTable(t);
	}

	private static Table deserialize(String path) {
		Table t = null;
		try {

			FileInputStream fileIn = new FileInputStream(
					"D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\" + path + ".class");

			ObjectInputStream in = new ObjectInputStream(fileIn);
			t = (Table) in.readObject();
			in.close();
			fileIn.close();
			return t;
		} catch (IOException i) {
			i.printStackTrace();
			return t;
		} catch (ClassNotFoundException c) {
			System.out.println("");
			c.printStackTrace();
			return t;
		}
	}

	public void displayTable(String tableName) throws DBAppException {
		if (tableName == null || !(tables.contains(tableName))) {
			throw new DBAppException("This table does not exist");
		}
		Table t = null;
		t = deserialize(tableName);
		System.out.println(t);
	}

	private static void serializeTable(Table t) {
		try {
			FileOutputStream fileOut = new FileOutputStream(
					"D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\" + t.tableName + ".class");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(t);
			out.close();
			fileOut.close();
			// System.out.printf("Serialized data is saved in /tmp/employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws DBAppException
	 * @throws ParseException
	 */
	/**
	 * @param args
	 * @throws IOException
	 * @throws DBAppException
	 * @throws ParseException
	 */
	/**
	 * @param args
	 * @throws IOException
	 * @throws DBAppException
	 * @throws ParseException
	 */
	/*@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static void main(String[] args) throws IOException, DBAppException, ParseException {
		String strTableName = "Student";
		DBApp dbApp = new DBApp();

		Hashtable htblColNameType = new Hashtable();
		htblColNameType.put("id", "java.lang.integer");
		htblColNameType.put("name", "java.lang.string");
		htblColNameType.put("gpa", "java.lang.double");
		dbApp.createTable(strTableName, "id", htblColNameType);
		Hashtable htblColNameValue = new Hashtable();

		int x=0;
		for (int i = 0; i < 40000; i++) {

			htblColNameValue.put("id", new Integer(i));
			htblColNameValue.put("name", new String ("h"));
			htblColNameValue.put("gpa", new Double(0.95));
			dbApp.insertIntoTable(strTableName, htblColNameValue);
			x++;
			System.out.println(x);

		}
		dbApp.displayTable(strTableName);

	}*/
	
	

	@SuppressWarnings({ "rawtypes", "unused" })
	private Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) {
		return null;
	}

	@SuppressWarnings("unused")
	private void createBIndex(String strTableName, String string) {

	}
}
