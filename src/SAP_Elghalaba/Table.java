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
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

@SuppressWarnings("serial")
public class Table implements Serializable {
	String tableName;
	@SuppressWarnings("rawtypes")
	Vector pages;
	int pagesize;
	String strClusteringKeyColumn;
	Hashtable<String, String> htblColNameType;
	@SuppressWarnings("rawtypes")
	Vector lastElementofPages;
	int pageid;

	@SuppressWarnings("rawtypes")
	public Table(String n, int i, String strClusteringKeyColumn, Hashtable<String, String> htblColNameType)
			throws IOException {
		tableName = n;
		pages = new Vector(0);
		pagesize = i;
		this.strClusteringKeyColumn = strClusteringKeyColumn;
		this.htblColNameType = htblColNameType;
		putInCsv();
		lastElementofPages = new Vector(10);
		pageid = 0;
	}

	public void putInCsv() throws IOException {
		FileWriter csvWriter = new FileWriter("D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\metadata.csv",
				true);
		Set<String> set = htblColNameType.keySet();

		for (String i : set) {
			csvWriter.append(tableName);
			csvWriter.append(",");
			csvWriter.append(i);
			csvWriter.append(",");
			csvWriter.append(htblColNameType.get(i));
			csvWriter.append(",");
			if (i.equalsIgnoreCase(strClusteringKeyColumn))
				csvWriter.append("True");
			else
				csvWriter.append("False");
			csvWriter.append(",");
			csvWriter.append("False");
			csvWriter.append("\n");
		}
		csvWriter.flush();
		csvWriter.close();
	}

	@SuppressWarnings("unchecked")
	private Page addNewPage() {
		Page neu = new Page(pagesize, tableName + pageid, this.tableName);
		pages.add(neu.pageName);
		pageid++;
		return neu;
	}

	private static void serializePage(Page t) {
		try {
			FileOutputStream fileOut = new FileOutputStream(
					"D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\" + t.pageName + ".class");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(t);
			out.close();
			fileOut.close();
			// System.out.printf("Serialized data is saved in /tmp/employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private static Page deserialize(String path) {
		Page p = null;
		try {
			// System.out.println(path);
			FileInputStream fileIn = new FileInputStream(
					"D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\" + path + ".class");

			ObjectInputStream in = new ObjectInputStream(fileIn);
			p = (Page) in.readObject();
			in.close();
			fileIn.close();
			return p;
		} catch (IOException i) {
			i.printStackTrace();
			return p;
		} catch (ClassNotFoundException c) {
			System.out.println("");
			c.printStackTrace();
			return p;
		}
	}

	public String toString() {
		String t = "";
		for (int i = 0; i < pages.size(); i++) {
			Page p = null;
			p = deserialize((String) pages.elementAt(i));
			t += p + "\n";
			serializePage(p);
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateLastElementofPage(Page p) {
		Vector t = new Vector();
		// int q = Integer.parseInt("" + p.pageName.charAt(p.pageName.length() - 1));
		boolean flag = false;
		Vector r = null;
		Vector s = new Vector();
		for (int i = 0; i < lastElementofPages.size(); i++) {
			s.clear();
			t = (Vector) lastElementofPages.get(i);

			if (t.get(0).equals(p.pageName)) {

				if ((p.fullPage() == false) && (i + 1) < lastElementofPages.size()) {
					r = (Vector) lastElementofPages.get(i + 1);
					r = deserialize((String) r.get(0));
					s.add(t.get(0));
					s.add(r.firstElement());
					serializePage((Page) r);
				} else {
					s.add(t.get(0));

					s.add(p.lastElement());
				}
				flag = true;

				lastElementofPages.set(i, s);

				return;
			}
		}
		s.clear();
		if (flag == false) {

			s.clear();
			s.add(p.pageName);
			s.add(p.lastElement());

			lastElementofPages.add(s);

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void insert(Object tupleOriginal) throws IOException, DBAppException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		BufferedReader br = new BufferedReader(
				new FileReader("D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\metadata.csv"));
		Vector tuple = (Vector) tupleOriginal;
		tuple.add(dtf.format(now));
		String line = br.readLine();
		String type = "";
		while (line != null) {
			String[] info = line.split(",");
			String id = info[1];
			if (info[0].equals(this.tableName)) {
				if (info[3].equalsIgnoreCase("true")) {
					type = info[2];
					break;
				}
			}

			line = br.readLine();
		}
		br.close();
		type = type.toLowerCase();

		int x = findColumnofClusteringKey();
		// System.out.println(((Vector) tuple).get(x));
		if (((Vector) tuple).get(x) == null) {
			throw new DBAppException("Can't insert null clustering key! please try agian");
		}

		switch (type) {
		case "java.lang.double":
			insertdouble(tuple);
			break;
		case "java.lang.string":
			insertString(tuple);
			break;
		case "java.lang.integer":
			intinsert(tuple);
			break;
		case "java.lang.boolean":
			boolinsert(tuple);
			break;
		case "java.util.date":
			dateinsert(tuple);
			break;
		case "java.awt.polygon":
			polyinsert(tuple);
			break;
		default:
			throw new DBAppException("Invalid data type. Please try again");
		}

	}

	@SuppressWarnings("rawtypes")
	private void polyinsert(Object tuple) throws DBAppException {
		Page p = null;
		int x = findColumnofClusteringKey();
		if (pages.size() == 0) {
			p = addNewPage();
			p.insertpoly(x, (Vector) tuple);
			;
			updateLastElementofPage(p);
			serializePage(p);
		} else {
			Polygon datum11 = (Polygon) ((Vector) ((Vector) lastElementofPages.lastElement()).get(1)).get(x);
			Polygon1 p1 = new Polygon1(datum11);
			Polygon datum22 = (Polygon) ((Vector) tuple).get(x);
			// System.out.println(datum11);
			// System.out.println(datum22);
			if (p1.compareTo(datum22) < 0)

			{
				// System.out.println(tuple);
				p = deserialize((String) ((Vector) lastElementofPages.lastElement()).get(0));
				if (p.fullPage()) {

					p = addNewPage();
					p.insertpoly(x, (Vector) tuple);
					;

					updateLastElementofPage(p);
					serializePage(p);
				} else {
					p.insertpoly(x, (Vector) tuple);
					updateLastElementofPage(p);
					serializePage(p);
				}
			} else {
				String page = getRightPage((Polygon) ((Vector) tuple).get(x), x);
				// System.out.println(page+"ff");
				for (int i = 0; i < pages.size(); i++) {
					// System.out.println(page);
					p = deserialize(page);
					// System.out.println(p.fullPage());
					if (p.fullPage() == false) {
						p.insertpoly(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						// System.out.println("hi");
						break;
					} else {
						Vector shifted = (Vector) p.lastElement();
						// System.out.println(shifted);
						p.remove(p.size() - 1);
						// System.out.println(p);
						p.insertpoly(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						int index = pages.indexOf(page);
						if (pages.size() - 1 == index) {
							p = addNewPage();
							p.insertpoly(x, shifted);
							updateLastElementofPage(p);
							serializePage(p);
							break;
						} else {
							page = (String) pages.get(index + 1);
							tuple = new Vector();
							tuple = shifted;
						}
					}
				}

			}
		}
	}

	@SuppressWarnings("rawtypes")
	private String getRightPage(Polygon polygon, int x) {
		Polygon datum1 = (Polygon) ((Vector) ((Vector) lastElementofPages.get(0)).get(1)).get(x);
		Polygon1 p2 =new Polygon1(datum1);
		
			int first = 0;
		int last = pages.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));
		
		if(p2.compareTo(polygon)>=0)
				return ((String) ((Vector) lastElementofPages.get(0)).get(0));
			else {
				while (first <= last) {
					Polygon datum2 = (Polygon) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x);
					Polygon1 p3 =new Polygon1(datum2);
					if(p3.compareTo(polygon)<0) {
						
						first = mid + 1;
					}/* else if(p3.compareTo(polygon)==0) {			
						return  ((String) ((Vector) lastElementofPages.get(mid)).get(0));

					}*/ else {
						last = mid - 1;
					}
					mid = (first + last) / 2;
				}
				if (first > last) {
					if((mid+1)<lastElementofPages.size())
					return  ((String) ((Vector) lastElementofPages.get(mid+1)).get(0));
				}
			}
			return "";
	}

	@SuppressWarnings("rawtypes")
	private void dateinsert(Object tuple) throws DBAppException {
		Page p = null;
		int x = findColumnofClusteringKey();
		if (pages.size() == 0) {
			p = addNewPage();
			p.insertdate(x, (Vector) tuple);
			;
			updateLastElementofPage(p);
			serializePage(p);
		} else {
			Date datum11 = (Date) ((Vector) ((Vector) lastElementofPages.lastElement()).get(1)).get(x);
			Date datum22 = (Date) ((Vector) tuple).get(x);
			// System.out.println(datum11);
			// System.out.println(datum22);
			if (datum11.compareTo(datum22) < 0)

			{
				// System.out.println(tuple);
				p = deserialize((String) ((Vector) lastElementofPages.lastElement()).get(0));
				if (p.fullPage()) {

					p = addNewPage();
					p.insertdate(x, (Vector) tuple);
					;

					updateLastElementofPage(p);
					serializePage(p);
				} else {
					p.insertdate(x, (Vector) tuple);
					updateLastElementofPage(p);
					serializePage(p);
				}
			} else {
				String page = getRightPage((Date) ((Vector) tuple).get(x), x);
				// System.out.println(page+"ff");
				for (int i = 0; i < pages.size(); i++) {
					// System.out.println(page);
					p = deserialize(page);
					// System.out.println(p.fullPage());
					if (p.fullPage() == false) {
						p.insertdate(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						// System.out.println("hi");
						break;
					} else {
						Vector shifted = (Vector) p.lastElement();
						// System.out.println(shifted);
						p.remove(p.size() - 1);
						// System.out.println(p);
						p.insertdate(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						int index = pages.indexOf(page);
						if (pages.size() - 1 == index) {
							p = addNewPage();
							p.insertdate(x, shifted);
							updateLastElementofPage(p);
							serializePage(p);
							break;
						} else {
							page = (String) pages.get(index + 1);
							tuple = new Vector();
							tuple = shifted;
						}
					}
				}

			}
		}
		// System.out.println(this);

	}

	@SuppressWarnings("rawtypes")
	private String getRightPage(Date date, int x) {
		Date datum1 = (Date) ((Vector) ((Vector) lastElementofPages.get(0)).get(1)).get(x);
		
		
			int first = 0;
			int last = pages.size() - 1;
			int mid = (first + last) / 2;
			// System.out.println((int)((Vector) this.get(0)).get(key));
			
				if (datum1.compareTo(date)>=0)
					return ((String) ((Vector) lastElementofPages.get(0)).get(0));
				else {
					while (first <= last) {
						datum1 = (Date) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x);
						if (datum1.compareTo(date)<0) {
							
							first = mid + 1;
						}/* else if (datum1.compareTo(date)==0) {			
							return  ((String) ((Vector) lastElementofPages.get(mid)).get(0));

						}*/ else {
							last = mid - 1;
						}
						mid = (first + last) / 2;
					}
					if (first > last) {
						if((mid+1)<lastElementofPages.size())
						return  ((String) ((Vector) lastElementofPages.get(mid+1)).get(0));
					}
				}
				return "";
		}

	@SuppressWarnings("rawtypes")
	private void boolinsert(Object tuple) throws DBAppException {
		Page p = null;
		int x = findColumnofClusteringKey();
		if (pages.size() == 0) {
			p = addNewPage();
			p.insertbool(x, (Vector) tuple);
			;
			updateLastElementofPage(p);
			serializePage(p);
		} else {

			// System.out.println((int) ((Vector)((Vector)
			// lastElementofPages.lastElement()).get(1)).get(x));
			// System.out.println((int) ((Vector) tuple).get(x));
			if (boolcompare((boolean) ((Vector) ((Vector) lastElementofPages.lastElement()).get(1)).get(x),
					(boolean) ((Vector) tuple).get(x)) < 0) {
				// System.out.println(tuple);
				p = deserialize((String) ((Vector) lastElementofPages.lastElement()).get(0));
				if (p.fullPage()) {

					p = addNewPage();
					p.insertbool(x, (Vector) tuple);
					;

					updateLastElementofPage(p);
					serializePage(p);
				} else {
					p.insertbool(x, (Vector) tuple);
					updateLastElementofPage(p);
					serializePage(p);
				}
			} else {
				String page = getRightPage((boolean) ((Vector) tuple).get(x), x);
				// System.out.println(page+"ff");
				for (int i = 0; i < pages.size(); i++) {
					// System.out.println(page);
					p = deserialize(page);
					// System.out.println(p.fullPage());
					if (p.fullPage() == false) {
						p.insertbool(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						// System.out.println("hi");
						break;
					} else {
						Vector shifted = (Vector) p.lastElement();
						// System.out.println(shifted);
						p.remove(p.size() - 1);
						// System.out.println(p);
						p.insertbool(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						int index = pages.indexOf(page);
						if (pages.size() - 1 == index) {
							p = addNewPage();
							p.insertbool(x, shifted);
							updateLastElementofPage(p);
							serializePage(p);
							break;
						} else {
							page = (String) pages.get(index + 1);
							tuple = new Vector();
							tuple = shifted;
						}
					}
				}

			}
		}

	}

	@SuppressWarnings("rawtypes")
	private String getRightPage(boolean y, int x) {

		int first = 0;
		int last = pages.size() - 1;
		int mid = (first + last) / 2;
// System.out.println((int)((Vector) this.get(0)).get(key));

		if (boolcompare((boolean) ((Vector) ((Vector) lastElementofPages.get(0)).get(1)).get(x), y) >= 0)
			return ((String) ((Vector) lastElementofPages.get(0)).get(0));
		else {
			while (first <= last) {
				if (boolcompare((boolean) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x), y) < 0) {
					first = mid + 1;
				} /*else if (boolcompare((boolean) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x),
						y) == 0) {
					return ((String) ((Vector) lastElementofPages.get(mid)).get(0));

				}*/ else {
					last = mid - 1;
				}
				mid = (first + last) / 2;
			}
			if (first > last) {
				if((mid+1)<lastElementofPages.size())
					return ((String) ((Vector) lastElementofPages.get(mid + 1)).get(0));
			}
		}
		return "";
	}

	public static int stringCompare(String str1, String str2) {

		int l1 = str1.length();
		int l2 = str2.length();
		int lmin = Math.min(l1, l2);

		for (int i = 0; i < lmin; i++) {
			int str1_ch = (int) str1.charAt(i);
			int str2_ch = (int) str2.charAt(i);

			if (str1_ch != str2_ch) {
				return str1_ch - str2_ch;
			}
		}

		// Edge case for strings like
		// String 1="Geeks" and String 2="Geeksforgeeks"
		if (l1 != l2) {
			return l1 - l2;
		}

		// If none of the above conditions is true,
		// it implies both the strings are equal
		else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	private void insertString(Object tuple) throws DBAppException {
		Page p = null;
		int x = findColumnofClusteringKey();
		if (pages.size() == 0) {
			p = addNewPage();
			p.insertstring(x, (Vector) tuple);
			;
			updateLastElementofPage(p);
			serializePage(p);
		} else {

			// System.out.println((int) ((Vector)((Vector)
			// lastElementofPages.lastElement()).get(1)).get(x));
			// System.out.println((int) ((Vector) tuple).get(x));
			if (stringCompare((String) ((Vector) ((Vector) lastElementofPages.lastElement()).get(1)).get(x),
					(String) ((Vector) tuple).get(x)) < 0) {
				// System.out.println(tuple);
				p = deserialize((String) ((Vector) lastElementofPages.lastElement()).get(0));
				if (p.fullPage()) {

					p = addNewPage();
					p.insertstring(x, (Vector) tuple);
					;

					updateLastElementofPage(p);
					serializePage(p);
				} else {
					p.insertstring(x, (Vector) tuple);
					updateLastElementofPage(p);
					serializePage(p);
				}
			} else {
				String page = getRightPage((String) ((Vector) tuple).get(x), x);
				// System.out.println(page+"ff");
				for (int i = 0; i < pages.size(); i++) {
					// System.out.println(page);
					p = deserialize(page);
					// System.out.println(p.fullPage());
					if (p.fullPage() == false) {
						p.insertstring(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						// System.out.println("hi");
						break;
					} else {
						Vector shifted = (Vector) p.lastElement();
						// System.out.println(shifted);
						p.remove(p.size() - 1);
						// System.out.println(p);
						p.insertstring(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						int index = pages.indexOf(page);
						if (pages.size() - 1 == index) {
							p = addNewPage();
							p.insertstring(x, shifted);
							updateLastElementofPage(p);
							serializePage(p);
							break;
						} else {
							page = (String) pages.get(index + 1);
							tuple = new Vector();
							tuple = shifted;
						}
					}
				}

			}
		}

	}

	@SuppressWarnings("rawtypes")
	private String getRightPage(String y, int x) {
		int first = 0;
		int last = pages.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));

		if (stringCompare((String) ((Vector) ((Vector) lastElementofPages.get(0)).get(1)).get(x), y) >= 0)
			return ((String) ((Vector) lastElementofPages.get(0)).get(0));
		else {
			while (first <= last) {
				if (stringCompare((String) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x), y) < 0) {
					first = mid + 1;
				}/* else if (stringCompare((String) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x),
						y) == 0) {
					return ((String) ((Vector) lastElementofPages.get(mid)).get(0));

				}*/ else {
					last = mid - 1;
				}
				mid = (first + last) / 2;
			}
			if (first > last) {
				if((mid+1)<lastElementofPages.size())
					return ((String) ((Vector) lastElementofPages.get(mid + 1)).get(0));
			}
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	private void insertdouble(Object tuple) throws DBAppException {
		Page p = null;
		int x = findColumnofClusteringKey();
		if (pages.size() == 0) {
			p = addNewPage();
			p.insertdouble(x, (Vector) tuple);
			;
			updateLastElementofPage(p);
			serializePage(p);
		} else {

			// System.out.println((int) ((Vector)((Vector)
			// lastElementofPages.lastElement()).get(1)).get(x));
			// System.out.println((int) ((Vector) tuple).get(x));
			if ((double) ((Vector) ((Vector) lastElementofPages.lastElement()).get(1))
					.get(x) < (double) ((Vector) tuple).get(x)) {
				// System.out.println(tuple);
				p = deserialize((String) ((Vector) lastElementofPages.lastElement()).get(0));
				if (p.fullPage()) {

					p = addNewPage();
					p.insertdouble(x, (Vector) tuple);
					;

					updateLastElementofPage(p);
					serializePage(p);
				} else {
					p.insertdouble(x, (Vector) tuple);
					updateLastElementofPage(p);
					serializePage(p);
				}
			} else {
				String page = getRightPage((double) ((Vector) tuple).get(x), x);
				// System.out.println(page+"ff");
				for (int i = 0; i < pages.size(); i++) {
					// System.out.println(page);
					p = deserialize(page);
					// System.out.println(p.fullPage());
					if (p.fullPage() == false) {
						p.insertdouble(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						// System.out.println("hi");
						break;
					} else {
						Vector shifted = (Vector) p.lastElement();
						// System.out.println(shifted);
						p.remove(p.size() - 1);
						// System.out.println(p);
						p.insertdouble(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						int index = pages.indexOf(page);
						if (pages.size() - 1 == index) {
							p = addNewPage();
							p.insertdouble(x, shifted);
							updateLastElementofPage(p);
							serializePage(p);
							break;
						} else {
							page = (String) pages.get(index + 1);
							tuple = new Vector();
							tuple = shifted;
						}
					}
				}

			}
		}
	}

	@SuppressWarnings("rawtypes")
	private String getRightPage(double y, int x) {

		int first = 0;
		int last = pages.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));

		if ((double) ((Vector) ((Vector) lastElementofPages.get(0)).get(1)).get(x) >= y)
			return ((String) ((Vector) lastElementofPages.get(0)).get(0));
		else {
			while (first <= last) {
				if ((double) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x) < y) {
					first = mid + 1;
				}/* else if ((double) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x) == y) {
					return ((String) ((Vector) lastElementofPages.get(mid)).get(0));

				}*/ else {
					last = mid - 1;
				}
				mid = (first + last) / 2;
			}
			if (first > last) {
				if((mid+1)<lastElementofPages.size())
					return ((String) ((Vector) lastElementofPages.get(mid + 1)).get(0));
			}
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	public void intinsert(Object tuple) throws DBAppException {
		Page p = null;
		int x = findColumnofClusteringKey();
		if (pages.size() == 0) {
			p = addNewPage();
			p.insertint(x, (Vector) tuple);
			;
			updateLastElementofPage(p);
			serializePage(p);
		} else {

			// System.out.println((int) ((Vector)((Vector)
			// lastElementofPages.lastElement()).get(1)).get(x));
			// System.out.println((int) ((Vector) tuple).get(x));
			if ((int) ((Vector) ((Vector) lastElementofPages.lastElement()).get(1)).get(x) < (int) ((Vector) tuple)
					.get(x)) {
				// System.out.println(tuple);
				p = deserialize((String) ((Vector) lastElementofPages.lastElement()).get(0));
				if (p.fullPage()) {

					p = addNewPage();
					p.insertint(x, (Vector) tuple);
					;

					updateLastElementofPage(p);
					serializePage(p);
				} else {
					p.insertint(x, (Vector) tuple);
					updateLastElementofPage(p);
					serializePage(p);
				}
			} else {
				String page = getRightPage((int) ((Vector) tuple).get(x), x);

				for (int i = 0; i < pages.size(); i++) {
					// System.out.println(page);
					p = deserialize(page);
					// System.out.println(p.fullPage());
					if (p.fullPage() == false) {
						p.insertint(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						// System.out.println("hi");
						break;
					} else {
						Vector shifted = (Vector) p.lastElement();
						// System.out.println(shifted);
						p.remove(p.size() - 1);
						// System.out.println(p);
						p.insertint(x, (Vector) tuple);
						updateLastElementofPage(p);
						serializePage(p);
						int index = pages.indexOf(page);
						if (pages.size() - 1 == index) {
							p = addNewPage();
							p.insertint(x, shifted);
							updateLastElementofPage(p);
							serializePage(p);
							break;
						} else {
							page = (String) pages.get(index + 1);
							tuple = new Vector();
							tuple = shifted;
						}
					}
				}

			}
		}
		// System.out.println(this);
	}

	@SuppressWarnings("rawtypes")
	public String getRightPage(int y, int x) {
		int first = 0;
		int last = pages.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));

		if ((int) ((Vector) ((Vector) lastElementofPages.get(0)).get(1)).get(x) >= y)
			return ((String) ((Vector) lastElementofPages.get(0)).get(0));
		else {
			while (first <= last) {
				if ((int) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x) < y) {
					first = mid + 1;
				} /*else if ((int) ((Vector) ((Vector) lastElementofPages.get(mid)).get(1)).get(x) == y) {
					return ((String) ((Vector) lastElementofPages.get(mid)).get(0));

				}*/ else {
					last = mid - 1;
				}
				mid = (first + last) / 2;
			}
			if (first > last) {
				if((mid+1)<lastElementofPages.size())
					return ((String) ((Vector) lastElementofPages.get(mid + 1)).get(0));
			}
		}
		return "";
	}
	

	public int findColumnofClusteringKey() throws DBAppException {
		Set<String> types = this.htblColNameType.keySet();
		int c = 0;
		for (String i : types) {

			if (strClusteringKeyColumn.equals(i)) {
				return c;

			}
			c++;
		}
		throw new DBAppException("Invalid clustering key");
	}

	public static int boolcompare(boolean x, boolean y) {
		if ((x == true && y == true) || (x == false && y == false))
			return 0;
		else if (x == true && y == false)
			return -1;
		else
			return 1;
	}

	@SuppressWarnings("unused")
	public void update(String strClusteringKey, Hashtable<String, Object> htblColNameValue)
			throws IOException, ParseException, DBAppException {
		BufferedReader br = new BufferedReader(
				new FileReader("D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\metadata.csv"));
		String line = br.readLine();
		String type = "";
		while (line != null) {
			String[] info = line.split(",");
			String id = info[1];
			if (info[0].equals(this.tableName)) {
				if (info[3].equalsIgnoreCase("true")) {
					type = info[2];
					break;
				}
			}

			line = br.readLine();
		}
		br.close();
		type = type.toLowerCase();
		// System.out.println(type);
		switch (type) {
		case "java.lang.double":
			updatedouble(strClusteringKey, htblColNameValue);
			break;
		case "java.lang.string":
			updateString(strClusteringKey, htblColNameValue);
			break;
		case "java.lang.integer":
			intupdate(strClusteringKey, htblColNameValue);
			break;
		case "java.lang.boolean":
			boolupdate(strClusteringKey, htblColNameValue);
			break;
		case "java.util.date":
			dateupdate(strClusteringKey, htblColNameValue);
			break;
		case "java.awt.polygon":
			polyupdate(strClusteringKey, htblColNameValue);
			break;
		default:
			throw new DBAppException("Invalid data type. Please try again");
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void polyupdate(String strClusteringKey, Hashtable<String, Object> htblColNameValue)
			throws IOException, DBAppException {

		boolean check = false;
		Vector tosort = new Vector();
		StringTokenizer st1 = new StringTokenizer(strClusteringKey, ",");
		Polygon key = new Polygon();

		Vector xx = new Vector();
		Vector y = new Vector();
		String z = "";
		while (st1.hasMoreTokens()) {
			z = st1.nextToken();
			z = z.substring(1);
			xx.add(Integer.parseInt(z));
			z = st1.nextToken();
			z = z.substring(0, z.length() - 1);
			y.add(Integer.parseInt(z));
		}
		// System.out.println(xx);
		// System.out.println(y);
		for (int i = 0; i < xx.size(); i++) {
			key.addPoint((int) xx.elementAt(i), (int) y.elementAt(i));
		}

		// System.out.println(key);
		int x = findColumnofClusteringKey();
		String page = getRightPage(key, x);
		// System.out.println(page+"dd");
		// System.out.println(pages.get(pages.indexOf(page)+1));
		Page p = null;
		Vector typepos = new Vector();

		Set<String> type = htblColNameValue.keySet();
		for (String i : type) {
			typepos.add(columnofKeys(i));
		}

		Set<String> types = htblColNameValue.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

		}
		int index = pages.indexOf(page);

		// System.out.println(page+"ggg");
		// System.out.println(this);
		if (page.length() == 0)
			return;
		// System.out.println(page+"dh");
		p = deserialize(page);

		while (check == false) {

			// System.out.println(page);
			// System.out.println(lastElementofPages);
			Vector s = (Vector) p.updatepolypage(key, x, typepos, tuple, checkforCluster(x, typepos), tableName);

			// System.out.println(lastElementofPages);
			if (p.size() == 0) {
				deletepage(p);
				index--;
				// System.out.println(index);
			} else {
				updateLastElementofPage(p);
				serializePage(p);
			}
			// System.out.println(s);
			check = (boolean) s.get(0);
			for (int i = 0; i < ((Vector) s.get(1)).size(); i++) {
				tosort.add(((Vector) s.get(1)).get(i));
			}
			if (check == false) {
				// System.out.println(index);
				if (pages.size() > (index + 1)) {

					// System.out.println(pages);
					p = deserialize((String) pages.get(index + 1));
					index++;
					// System.out.println(p.pageName);
				} else
					break;
			} else {// System.out.println(this);
				break;
			}

		}
		// System.out.println(this);

		// System.out.println(tosort);

		for (int i = 0; i < tosort.size(); i++) {
			// System.out.println(this);
			// System.out.println(tosort.get(i)+"l");
			Vector t = (Vector) tosort.get(i);
			t.remove(t.size() - 1);
			insert(t);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void dateupdate(String strClusteringKey, Hashtable<String, Object> htblColNameValue)
			throws IOException, ParseException, DBAppException {

		boolean check = false;
		Vector tosort = new Vector();
		Date key = new SimpleDateFormat("yyyy/MM/dd").parse(strClusteringKey);

		// System.out.println(key);
		int x = findColumnofClusteringKey();
		String page = getRightPage(key, x);
		// System.out.println(page+"dd");
		// System.out.println(pages.get(pages.indexOf(page)+1));
		Page p = null;
		Vector typepos = new Vector();

		Set<String> type = htblColNameValue.keySet();
		for (String i : type) {
			typepos.add(columnofKeys(i));
		}

		Set<String> types = htblColNameValue.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

		}
		int index = pages.indexOf(page);

		// System.out.println(page+"ggg");
		// System.out.println(this);
		if (page.length() == 0)
			return;
		p = deserialize(page);

		while (check == false) {

			// System.out.println(page);
			// System.out.println(lastElementofPages);
			Vector s = (Vector) p.updatedatepage(key, x, typepos, tuple, checkforCluster(x, typepos), tableName);

			// System.out.println(lastElementofPages);
			if (p.size() == 0) {
				deletepage(p);
				index--;
				// System.out.println(index);
			} else {
				updateLastElementofPage(p);
				serializePage(p);
			}
			// System.out.println(s);
			check = (boolean) s.get(0);
			for (int i = 0; i < ((Vector) s.get(1)).size(); i++) {
				tosort.add(((Vector) s.get(1)).get(i));
			}
			if (check == false) {
				// System.out.println(index);
				if (pages.size() > (index + 1)) {

					// System.out.println(pages);
					p = deserialize((String) pages.get(index + 1));
					index++;
					// System.out.println(p.pageName);
				} else
					break;
			} else {// System.out.println(this);
				break;
			}

		}
		// System.out.println(this);

		// System.out.println(tosort);

		for (int i = 0; i < tosort.size(); i++) {
			// System.out.println(this);
			// System.out.println(tosort.get(i)+"l");
			Vector t = (Vector) tosort.get(i);
			t.remove(t.size() - 1);
			insert(t);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void boolupdate(String strClusteringKe, Hashtable<String, Object> htblColNameValue)
			throws IOException, DBAppException {
		boolean check = false;
		Vector tosort = new Vector();
		String clus = strClusteringKe.toLowerCase();
		boolean key;
		if (clus.contentEquals("true"))
			key = true;
		else
			key = false;
		// System.out.println(key);
		int x = findColumnofClusteringKey();
		String page = getRightPage(key, x);
		// System.out.println(page+"dd");
		// System.out.println(pages.get(pages.indexOf(page)+1));
		Page p = null;
		Vector typepos = new Vector();

		Set<String> type = htblColNameValue.keySet();
		for (String i : type) {
			typepos.add(columnofKeys(i));
		}

		Set<String> types = htblColNameValue.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

		}
		int index = pages.indexOf(page);

		// System.out.println(page+"ggg");
		if (page.length() == 0)
			return;
		// System.out.println(this);
		p = deserialize(page);

		while (check == false) {

			// System.out.println(page);
			// System.out.println(lastElementofPages);
			Vector s = (Vector) p.updateboolpage(key, x, typepos, tuple, checkforCluster(x, typepos), tableName);

			// System.out.println(lastElementofPages);
			if (p.size() == 0) {
				deletepage(p);
				index--;
				// System.out.println(index);
			} else {
				updateLastElementofPage(p);
				serializePage(p);
			}
			// System.out.println(s);
			check = (boolean) s.get(0);
			for (int i = 0; i < ((Vector) s.get(1)).size(); i++) {
				tosort.add(((Vector) s.get(1)).get(i));
			}
			if (check == false) {
				// System.out.println(index);
				if (pages.size() > (index + 1)) {

					// System.out.println(pages);
					p = deserialize((String) pages.get(index + 1));
					index++;
					// System.out.println(p.pageName);
				} else
					break;
			} else {// System.out.println(this);
				break;
			}

		}
		// System.out.println(this);

		// System.out.println(tosort);

		for (int i = 0; i < tosort.size(); i++) {
			// System.out.println(this);
			// System.out.println(tosort.get(i)+"l");
			Vector t = (Vector) tosort.get(i);
			t.remove(t.size() - 1);
			insert(t);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void intupdate(String strClusteringKey, Hashtable<String, Object> htblColNameValue)
			throws IOException, DBAppException {

		boolean check = false;
		Vector tosort = new Vector();
		int key = Integer.parseInt(strClusteringKey);
		// System.out.println(key);
		int x = findColumnofClusteringKey();
		String page = getRightPage(key, x);
		// System.out.println(page);
		// System.out.println(pages.get(pages.indexOf(page)+1));
		Page p = null;
		Vector typepos = new Vector();

		Set<String> type = htblColNameValue.keySet();
		for (String i : type) {
			typepos.add(columnofKeys(i));
		}

		Set<String> types = htblColNameValue.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

		}
		int index = pages.indexOf(page);
		if (page.length() == 0)
			return;
		// System.out.println(page+"ggg");
		p = deserialize(page);

		while (check == false) {

			// System.out.println(page);
			Vector s = (Vector) p.updateintpage(key, x, typepos, tuple, checkforCluster(x, typepos), tableName);
			// updateLastElementofPage(p);
			if (p.size() == 0) {
				deletepage(p);
				index--;
				// System.out.println(index);
				// updateLastElementofPage(p);
			} else {
				serializePage(p);
				updateLastElementofPage(p);
			}
			// System.out.println(s);
			check = (boolean) s.get(0);
			for (int i = 0; i < ((Vector) s.get(1)).size(); i++) {
				tosort.add(((Vector) s.get(1)).get(i));
			}
			if (check == false) {
				// System.out.println(index);
				if (pages.size() > (index + 1)) {

					// System.out.println(pages);
					p = deserialize((String) pages.get(index + 1));
					index++;
					// System.out.println(p.pageName);
				} else
					break;
			} else {// System.out.println(this);
				break;
			}

		}
		// System.out.println(this);

		// System.out.println(tosort);

		for (int i = 0; i < tosort.size(); i++) {
			// System.out.println(this);
			// System.out.println(tosort.get(i)+"l");
			Vector t = (Vector) tosort.get(i);
			t.remove(t.size() - 1);
			insert(t);
		}

	}

	@SuppressWarnings("rawtypes")
	private void deletepage(Page p) {
		String s = p.pageName;
		pages.remove(s);
		File f = new File(p.pageName + ".class");
		int x = 0;
		for (int i = 0; i < lastElementofPages.size(); i++) {
			if (((Vector) lastElementofPages.elementAt(i)).elementAt(0).equals(p.pageName))
				x = i;
		}
		lastElementofPages.remove(x);
		f.delete();
		// System.out.println(lastElementofPages);
	}

	@SuppressWarnings("rawtypes")
	private boolean checkforCluster(int x, Vector typespos) {
		if (typespos.contains(x))
			return true;
		return false;
	}

	private int columnofKeys(String type) {
		Set<String> types = this.htblColNameType.keySet();
//		System.out.println(types);

		int c = 0;
		for (String i : types) {

			if (type.equals(i))
				return c;
			// System.out.println("hi");

			c++;
		}

		return c;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateString(String strClusteringKey, Hashtable<String, Object> htblColNameValue)
			throws IOException, DBAppException {

		boolean check = false;
		Vector tosort = new Vector();
		String key = strClusteringKey;
		// System.out.println(key);
		int x = findColumnofClusteringKey();
		String page = getRightPage(key, x);
		// System.out.println(page+"dd");
		// System.out.println(pages.get(pages.indexOf(page)+1));
		Page p = null;
		Vector typepos = new Vector();

		Set<String> type = htblColNameValue.keySet();
		for (String i : type) {
			typepos.add(columnofKeys(i));
		}

		Set<String> types = htblColNameValue.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

		}
		int index = pages.indexOf(page);

		// System.out.println(page+"ggg");
		// System.out.println(this);
		if (page.length() == 0)
			return;
		p = deserialize(page);

		while (check == false) {

			// System.out.println(page);
			// System.out.println(lastElementofPages);
			Vector s = (Vector) p.updateStringpage(key, x, typepos, tuple, checkforCluster(x, typepos), tableName);

			// System.out.println(lastElementofPages);
			if (p.size() == 0) {
				deletepage(p);
				index--;
				// System.out.println(index);
			} else {
				updateLastElementofPage(p);
				serializePage(p);
			}
			// System.out.println(s);
			check = (boolean) s.get(0);
			for (int i = 0; i < ((Vector) s.get(1)).size(); i++) {
				tosort.add(((Vector) s.get(1)).get(i));
			}
			if (check == false) {
				// System.out.println(index);
				if (pages.size() > (index + 1)) {

					// System.out.println(pages);
					p = deserialize((String) pages.get(index + 1));
					index++;
					// System.out.println(p.pageName);
				} else
					break;
			} else {// System.out.println(this);
				break;
			}

		}
		// System.out.println(this);

		// System.out.println(tosort);

		for (int i = 0; i < tosort.size(); i++) {
			// System.out.println(this);
			// System.out.println(tosort.get(i)+"l");
			Vector t = (Vector) tosort.get(i);
			t.remove(t.size() - 1);
			insert(t);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updatedouble(String strClusteringKey, Hashtable<String, Object> htblColNameValue)
			throws IOException, DBAppException {
		boolean check = false;
		Vector tosort = new Vector();
		double key = Double.parseDouble(strClusteringKey);
		// System.out.println(key);
		int x = findColumnofClusteringKey();
		String page = getRightPage(key, x);
		// System.out.println(page);
		// System.out.println(pages.get(pages.indexOf(page)+1));
		Page p = null;
		Vector typepos = new Vector();

		Set<String> type = htblColNameValue.keySet();
		for (String i : type) {
			typepos.add(columnofKeys(i));
		}

		Set<String> types = htblColNameValue.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

		}
		int index = pages.indexOf(page);

		// System.out.println(page+"ggg");
		if (page.length() == 0)
			return;
		p = deserialize(page);

		while (check == false) {

			// System.out.println(page);
			Vector s = (Vector) p.updatedoublepage(key, x, typepos, tuple, checkforCluster(x, typepos), tableName);
			// updateLastElementofPage(p);
			if (p.size() == 0) {
				deletepage(p);
				index--;
				// System.out.println(index);
				// updateLastElementofPage(p);
			} else {
				serializePage(p);
				updateLastElementofPage(p);
			}
			// System.out.println(s);
			check = (boolean) s.get(0);
			for (int i = 0; i < ((Vector) s.get(1)).size(); i++) {
				tosort.add(((Vector) s.get(1)).get(i));
			}
			if (check == false) {
				// System.out.println(index);
				if (pages.size() > (index + 1)) {

					// System.out.println(pages);
					p = deserialize((String) pages.get(index + 1));
					index++;
					// System.out.println(p.pageName);
				} else
					break;
			} else {// System.out.println(this);
				break;
			}

		}
		// System.out.println(this);

		// System.out.println(tosort);

		for (int i = 0; i < tosort.size(); i++) {
			// System.out.println(this);
			// System.out.println(tosort.get(i)+"l");
			Vector t = (Vector) tosort.get(i);
			t.remove(t.size() - 1);
			insert(t);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void delete(Hashtable<String, Object> htblColNameValue) throws IOException {
		Vector typepos = new Vector();
		int polpol = -1;
		Set<String> type = htblColNameValue.keySet();
		for (String i : type) {
			if (checkforpoly(i)) {
				polpol = columnofKeys(i);
				// System.out.println(polpol);
			}
			typepos.add(columnofKeys(i));
		}

		Set<String> types = htblColNameValue.keySet();
		Vector tuple = new Vector(0);
		for (String i : types) {
			tuple.add(htblColNameValue.get(i));

		}
		// System.out.println(typepos);
		// System.out.println(tuple);
		Vector tobedeleted = new Vector();
		Page p = null;
		for (int i = 0; i < pages.size(); i++) {
			p = deserialize((String) pages.get(i));
			p.deleteinPage(typepos, tuple, polpol);
			if (p.size() == 0) {
				tobedeleted.add(p);

			} else {
				updateLastElementofPage(p);
				serializePage(p);
			}
		}
		for (int i = 0; i < tobedeleted.size(); i++) {
			deletepage((Page) tobedeleted.elementAt(i));
		}
	}

	@SuppressWarnings({ "resource", "unused" })
	public boolean checkforpoly(String type) throws IOException {
		Set<String> types = this.htblColNameType.keySet();
//		System.out.println(types);
		String poly = "";
		for (String i : types) {

			if (type.equals(i)) {
				poly += i;
			}

		}
		if (poly.length() > 0) {
			BufferedReader br = new BufferedReader(
					new FileReader("D:\\uni projects\\DB2 projects 3\\SAP_Elghalaba\\data\\metadata.csv"));
			String line = br.readLine();
			while (line != null) {
				String[] info = line.split(",");
				String id = info[1];
				if (info[0].equals(this.tableName)) {
					if (info[1].equalsIgnoreCase(poly)) {
						if (info[2].equalsIgnoreCase("java.awt.polygon")) {
							return true;

						}

					}
				}

				line = br.readLine();
			}
			br.close();

		}
		return false;
	}
}
