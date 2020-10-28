package SAP_Elghalaba;
import java.awt.Polygon;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

@SuppressWarnings({ "rawtypes", "serial", "unchecked"})
public class Page extends Vector implements Serializable {
	static int maxNumofRows;
	public String pageName;
	public String fatherTable;
	public Page(int n, String s,String t) {
		super(0);
		maxNumofRows = n;
		pageName = s;
		fatherTable = t;
	}

//	public boolean insertIntoPage(Object tuple) {
//
//		if (this.size() == maxNumofRows)
//			return false;
//		else {
//
//			this.addElement(tuple);
//
//			return true;
//		}
//	}

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

	public void insertint(int key, Vector value) {
		int first = 0;
		int last = this.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));
		if (this.size() == 0) {
			this.add(value);
		} else {
			if ((int) value.get(key) < (int) ((Vector) this.get(0)).get(key))
				this.add(0, value);
			else {
				while (first <= last) {
					if ((int) ((Vector) this.get(mid)).get(key) < (int) value.get(key)) {
						first = mid + 1;
					} else if ((int) ((Vector) this.get(mid)).get(key) == (int) value.get(key)) {
						this.add(mid + 1, value);
						break;
					} else {
						last = mid - 1;
					}
					mid = (first + last) / 2;
				}
				if (first > last) {
					this.add(mid + 1, value);
				}
			}
		}
	}

	public void insertdate(int key, Vector value) {
		int first = 0;
		int last = this.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));
		Date datum1 = (Date) value.get(key);

		if (this.size() == 0) {
			this.add(value);
		} else {
			Date datum2 = (Date) ((Vector) this.get(0)).get(key);
			if (datum1.compareTo(datum2) < 0)

				this.add(0, value);
			else {
				while (first <= last) {
					datum1 = (Date) ((Vector) this.get(mid)).get(key);
					datum2 = (Date) value.get(key);
					if (datum1.compareTo(datum2) < 0) {
						first = mid + 1;
					} else if (datum1.compareTo(datum2) == 0) {
						this.add(mid + 1, value);
						break;
					} else {
						last = mid - 1;
					}
					mid = (first + last) / 2;
				}
				if (first > last) {
					this.add(mid + 1, value);
				}
			}
		}
	}

	public void insertstring(int key, Vector value) {
		int first = 0;
		int last = this.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));
		if (this.size() == 0) {
			this.add(value);
		} else {
			if (stringCompare((String) value.get(key), (String) ((Vector) this.get(0)).get(key)) < 0)
	this.add(0, value);
			else {
				while (first <= last) {

					if (stringCompare((String) ((Vector) this.get(mid)).get(key), (String) value.get(key)) < 0) {
						first = mid + 1;
					} else if (stringCompare((String) ((Vector) this.get(mid)).get(key),
							(String) value.get(key)) == 0) {
						this.add(mid +1 , value);
						break;
					} else {
						last = mid - 1;
					}
					mid = (first + last) / 2;
				}
				if (first > last) {
					this.add(mid +1 , value);
				}
			}
		}
	}

	public void insertbool(int key, Vector value) {
		int first = 0;
		int last = this.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));
		if (this.size() == 0) {
			this.add(value);
		} else {
			if (boolcompare((boolean) value.get(key), (boolean) ((Vector) this.get(0)).get(key)) < 0)
	this.add(0, value);
			else {
				while (first <= last) {

					if (boolcompare((boolean) ((Vector) this.get(mid)).get(key), (boolean) value.get(key)) < 0) {
						first = mid + 1;
					} else if (boolcompare((boolean) ((Vector) this.get(mid)).get(key),
							(boolean) value.get(key)) == 0) {
						this.add(mid +1 , value);
						break;
					} else {
						last = mid - 1;
					}
					mid = (first + last) / 2;
				}
				if (first > last) {
					this.add(mid +1 , value);
				}
			}
		}
	}
	private int boolcompare(boolean x, boolean y) {
		if((x==true && y == true)||(x==false && y==false))return 0;
		else if(x== true && y== false)return -1;
		else return 1;
	}

	public void insertpoly(int key,Vector value)  {
		int first = 0;
		int last = this.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));
		Polygon datum1 = (Polygon) value.get(key);
		Polygon1 p3 =new Polygon1(datum1);

		if (this.size() == 0) {
			this.add(value);
		} else {
			Polygon datum2 = (Polygon) ((Vector) this.get(0)).get(key);
			if (p3.compareTo(datum2) < 0)

				this.add(0, value);
			else {
				while (first <= last) {
					datum1= (Polygon) ((Vector) this.get(mid)).get(key);
					Polygon1 p4 =new Polygon1(datum1);
					datum2 = (Polygon) value.get(key);
					if (p4.compareTo(datum2) < 0) {
						first = mid + 1;
					} else if (p4.compareTo(datum2) == 0) {
						this.add(mid + 1, value);
						break;
					} else {
						last = mid - 1;
					}
					mid = (first + last) / 2;
				}
				if (first > last) {
					this.add(mid + 1, value);
				}
			}
		}
	}
	
	public void insertdouble(int key, Vector value) {
		int first = 0;
		int last = this.size() - 1;
		int mid = (first + last) / 2;
		// System.out.println((int)((Vector) this.get(0)).get(key));
		if (this.size() == 0) {
			this.add(value);
		} else {
			if ((double) value.get(key) < (double) ((Vector) this.get(0)).get(key))
				this.add(0, value);
			else {
				while (first <= last) {
					if ((double) ((Vector) this.get(mid)).get(key) < (double) value.get(key)) {
						first = mid + 1;
					} else if ((double) ((Vector) this.get(mid)).get(key) == (double) value.get(key)) {
						this.add(mid + 1, value);
						break;
					} else {
						last = mid - 1;
					}
					mid = (first + last) / 2;
				}
				if (first > last) {
					this.add(mid + 1, value);
				}
			}
		}
	}

	public boolean fullPage() {
		if (this.size() == maxNumofRows)
			return true;
		else
			return false;
	}


	public Vector updateintpage(int key, int x, Vector typespos, Vector values,boolean cluster,String TableName) throws IOException {
		//System.out.println(fatherTable);

		Vector ret = new Vector();
		Vector toSort = new Vector();
		ret.add(true);
		//System.out.println(values);
		//System.out.println(typespos);
		for(int i=0;i<this.size();i++) {
		//	System.out.println(((Vector) this.elementAt(i)).elementAt(x));
			if((int) ((Vector) this.elementAt(i)).elementAt(x)==key) {
				

				for(int j=0;j<typespos.size();j++) {
					//System.out.println(this.elementAt(i));
					((Vector) this.elementAt(i)).set((int) typespos.elementAt(j), values.elementAt(j));}
				if(cluster==true) {
					//System.out.println("ww");
					Vector r =null;
					r =((Vector) this.elementAt(i));
					
					toSort.add(r);
					
				}
				
				
				if(this.size()<=i+1) {
					//	System.out.println("hi");
						ret.setElementAt(false, 0);
					
					}
				else if((int) ((Vector) this.elementAt(i+1)).elementAt(x)!=key) ret.setElementAt(true, 0);;
				
				
			}
		}
		for (int i=0;i<toSort.size();i++) {
			this.remove(toSort.get(i));
		}
		ret.add(toSort);
		return ret;
	}


	

	public Vector updateboolpage(boolean key, int x, Vector typepos, Vector tuple, boolean checkforCluster,
			String tableName) {
		//System.out.println(fatherTable);
		Vector ret = new Vector();
		Vector toSort = new Vector();
		ret.add(true);
		//System.out.println(tuple);
	//	System.out.println(typepos);
		for(int i=0;i<this.size();i++) {
		//	System.out.println(((Vector) this.elementAt(i)).elementAt(x));
			if(((Vector) this.elementAt(i)).elementAt(x).equals(key)) {
				

				for(int j=0;j<typepos.size();j++) {
					//System.out.println(this.elementAt(i));
					((Vector) this.elementAt(i)).set((int) typepos.elementAt(j), tuple.elementAt(j));}
				if(checkforCluster==true) {
					//System.out.println("ww");
					Vector r =null;
					r =((Vector) this.elementAt(i));
			//	System.out.println(r);
					toSort.add(r);
					
				}
				
				
				if(this.size()<=i+1) {
					//	System.out.println("hi");
						ret.setElementAt(false, 0);
					
					}
				else if(!((Vector) this.elementAt(i+1)).elementAt(x).equals(key)) ret.setElementAt(true, 0);;
				
				
			}
		}
		for (int i=0;i<toSort.size();i++) {
			this.remove(toSort.get(i));
		}
		
		
		ret.add(toSort);
		return ret;
		
	}
	

	public Vector updatedoublepage(double key, int x, Vector typepos, Vector tuple, boolean checkforCluster,
			String tableName) {
		

		Vector ret = new Vector();
		Vector toSort = new Vector();
		ret.add(true);
		//System.out.println(values);
		//System.out.println(typespos);
		for(int i=0;i<this.size();i++) {
		//	System.out.println(((Vector) this.elementAt(i)).elementAt(x));
			if((double) ((Vector) this.elementAt(i)).elementAt(x)==key) {
				

				for(int j=0;j<typepos.size();j++) {
					//System.out.println(this.elementAt(i));
					((Vector) this.elementAt(i)).set((int) typepos.elementAt(j), tuple.elementAt(j));}
				if(checkforCluster==true) {
					//System.out.println("ww");
					Vector r =null;
					r =((Vector) this.elementAt(i));
					
					toSort.add(r);
					
				}
				
				
				if(this.size()<=i+1) {
					//	System.out.println("hi");
						ret.setElementAt(false, 0);
					
					}
				else if((double) ((Vector) this.elementAt(i+1)).elementAt(x)!=key) ret.setElementAt(true, 0);;
				
				
			}
		}
		for (int i=0;i<toSort.size();i++) {
			this.remove(toSort.get(i));
		}
		ret.add(toSort);
		return ret;
	}

	public Vector updateStringpage(String key, int x, Vector typepos, Vector tuple, boolean checkforCluster,
			String tableName) {
		Vector ret = new Vector();
		Vector toSort = new Vector();
		ret.add(true);
		//System.out.println(values);
		//System.out.println(typespos);
		for(int i=0;i<this.size();i++) {
		//	System.out.println(((Vector) this.elementAt(i)).elementAt(x));
			if(((Vector) this.elementAt(i)).elementAt(x).equals(key)) {
				

				for(int j=0;j<typepos.size();j++) {
					//System.out.println(this.elementAt(i));
					((Vector) this.elementAt(i)).set((int) typepos.elementAt(j), tuple.elementAt(j));}
				if(checkforCluster==true) {
					//System.out.println("ww");
					Vector r =null;
					r =((Vector) this.elementAt(i));
					
					toSort.add(r);
					
				}
				
				
				if(this.size()<=i+1) {
					//	System.out.println("hi");
						ret.setElementAt(false, 0);
					
					}
				else if(!((Vector) this.elementAt(i+1)).elementAt(x).equals(key)) ret.setElementAt(true, 0);;
				
				
			}
		}
		for (int i=0;i<toSort.size();i++) {
			this.remove(toSort.get(i));
		}
		ret.add(toSort);
		return ret;
		
	}

	public Vector updatedatepage(Date key, int x, Vector typepos, Vector tuple, boolean checkforCluster,
			String tableName) {
		Vector ret = new Vector();
		Vector toSort = new Vector();
		ret.add(true);
		//System.out.println(values);
		//System.out.println(typespos);
		for(int i=0;i<this.size();i++) {
		//	System.out.println(((Vector) this.elementAt(i)).elementAt(x));
			if(((Vector) this.elementAt(i)).elementAt(x).equals(key)) {
				

				for(int j=0;j<typepos.size();j++) {
					//System.out.println(this.elementAt(i));
					((Vector) this.elementAt(i)).set((int) typepos.elementAt(j), tuple.elementAt(j));}
				if(checkforCluster==true) {
					//System.out.println("ww");
					Vector r =null;
					r =((Vector) this.elementAt(i));
					
					toSort.add(r);
					
				}
				
				
				if(this.size()<=i+1) {
					//	System.out.println("hi");
						ret.setElementAt(false, 0);
					
					}
				else if(!((Vector) this.elementAt(i+1)).elementAt(x).equals(key)) ret.setElementAt(true, 0);;
				
				
			}
		}
		for (int i=0;i<toSort.size();i++) {
			this.remove(toSort.get(i));
		}
		ret.add(toSort);
		return ret;
		
	}

	public void deleteinPage(Vector typepos, Vector tuple, int polpol) {
		Vector tbr = new Vector();
		for(int i=0;i<this.size();i++) {
			if(checkforValues(typepos,tuple,(Vector) this.elementAt(i),polpol)==true)tbr.add(this.elementAt(i));
		}
		for(int i=0;i<tbr.size();i++) {
			this.remove(tbr.get(i));
		}
		
	}

	private boolean checkforValues(Vector typepos, Vector tuple, Vector Orignaltuple,int polpos) {
		int check = 0;
		for(int i=0;i<tuple.size();i++) {
				if(Orignaltuple.indexOf(Orignaltuple.elementAt((int) typepos.elementAt(i)))==polpos) {
					Polygon1 p = new Polygon1((Polygon) Orignaltuple.elementAt((int) typepos.elementAt(i)));
					if(p.compareTo(tuple.elementAt(i))==0)check++;
				}
				else {
				if(Orignaltuple.elementAt((int) typepos.elementAt(i)).equals(tuple.elementAt(i)))check++;
			}
		}
		//System.out.println(check);
		if(check==tuple.size())return true;
		return false;
	}


	public Vector updatepolypage(Polygon key, int x, Vector typepos, Vector tuple, boolean checkforCluster,
			String tableName) {
		Vector ret = new Vector();
		Vector toSort = new Vector();
		Polygon1 p =new Polygon1(key);
		int z;
		ret.add(true);
		//System.out.println(values);
		//System.out.println(typespos);
		for(int i=0;i<this.size();i++) {
		//	System.out.println(((Vector) this.elementAt(i)).elementAt(x));
			z=p.compareTo(((Vector) this.elementAt(i)).elementAt(x));
			if(z==0) {
				

				for(int j=0;j<typepos.size();j++) {
					//System.out.println(this.elementAt(i));
					((Vector) this.elementAt(i)).set((int) typepos.elementAt(j), tuple.elementAt(j));}
				if(checkforCluster==true) {
					//System.out.println("ww");
					Vector r =null;
					r =((Vector) this.elementAt(i));
					
					toSort.add(r);
					
				}
				
				
				if(this.size()<=i+1) {
					//	System.out.println("hi");
						ret.setElementAt(false, 0);
					
					}
				else {z=p.compareTo(((Vector) this.elementAt(i+1)).elementAt(x));
					if(z!=0) ret.setElementAt(true, 0);
				}
				
				
			}
		}
		for (int i=0;i<toSort.size();i++) {
			this.remove(toSort.get(i));
		}
		ret.add(toSort);
		return ret;
	}
	
}
