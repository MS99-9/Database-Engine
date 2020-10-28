package SAP_Elghalaba;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.StringTokenizer;
import java.util.Vector;

@SuppressWarnings({ "serial", "rawtypes", "unused"})
public class Polygon1 extends Polygon implements Comparable{
	Polygon p;

	
	public Polygon1(Polygon p) {
		this.p=p;

	}

//	public int compareTo(Object o) {
//		Polygon s = (Polygon) o;
//	//	System.out.println(s.getBounds());
//		Dimension dim1 = s.getBounds( ).getSize( );
//		int nThisArea1 = dim1.width * dim1.height;
//		Dimension dim = p.getBounds( ).getSize( );
//		int nThisArea = dim.width * dim.height;
//	//	System.out.println(nThisArea);
//	//	System.out.println(nThisArea1+"fgf");
//		if(nThisArea>nThisArea1)return 1;
//		else if(nThisArea==nThisArea1)return 0;
//		else return -1;
//	}
	public int compareTo(Object o) {
		Polygon s = (Polygon) o;
		int[]x1=s.xpoints;
		int[]y1=s.ypoints;
		int[]x2=p.xpoints;
		int[]y2=p.ypoints;
		
		Point2D[] list1= new Point2D[s.npoints];
		Point2D[] list2= new Point2D[p.npoints];

		for(int i=0;i<s.npoints;i++) {
			list1[i]=new Point2D.Double(x1[i],y1[i]);
		}
		for(int i=0;i<p.npoints;i++) {
			list2[i]=new Point2D.Double(x2[i],y2[i]);
		}
		
		double area1=areaCalculator(list1);
		double area2=areaCalculator(list2);

		if(area2>area1)return 1;
		else if(area2==area1)return 0;
		else return -1;
	}
	public static double areaCalculator(Point2D[] vertices)
	  {
	    double sum = 0;
	    
	    for (int i = 0; i < vertices.length ; i++)
	    {
	      if (i == 0)
	    	  sum += vertices[i].getX() * (vertices[i + 1].getY() - vertices[vertices.length - 1].getY());
	      else if (i == vertices.length - 1)
	    	  sum += vertices[i].getX() * (vertices[0].getY()  - vertices[i - 1].getY());
	      else
	    	  sum += vertices[i].getX() * (vertices[i + 1].getY() - vertices[i - 1].getY());
	    }

	    double area = 0.5 * Math.abs(sum);
	    return area;
	  }
	
	/*public static void main(String[]args) {
		 StringTokenizer st1 = 
	             new StringTokenizer("(130,110),(100,150),(10,190)", ",");
		Polygon p = new Polygon();
		
		 Vector x = new Vector();
		 Vector y = new Vector();
		String z = "";
		while (st1.hasMoreTokens()) {
			z=st1.nextToken();
			z= z.substring(1);
			x.add(Integer.parseInt(z));
			z=st1.nextToken();
			z= z.substring(0, z.length()-1);
			y.add(Integer.parseInt(z));}
		System.out.println(x);
		System.out.println(y);
		for(int i=0;i<x.size();i++) {
			p.addPoint((int)x.elementAt(i), (int)y.elementAt(i));
		}
		p.addPoint(11, 40);
		Dimension dim1 = p.getBounds( ).getSize( );
		Polygon r = new Polygon();
		r.addPoint(130, 110);
		r.addPoint(100, 150);
		r.addPoint(10, 190);
		int nThisArea1 = dim1.width * dim1.height;
		double dd = 44;
		double rr = 44;
		
		//note : the shape real area = 1200 but with this code it prints 18000!
		System.out.println(nThisArea1);
		
		
		Point2D[] list;
		list= new Point2D[3];
		list[0]=new Point2D.Double(130,110);
		list[1]=new Point2D.Double(100,150);;
		list[2]=new Point2D.Double(10,190);;
		
		System.out.println(areaCalculator(list));
		
		Polygon s = new Polygon();
		s.addPoint(50,100);
		s.addPoint(10,70);
		s.addPoint(50,50);
		
		int[]x1=s.xpoints;
		int[]y1=s.ypoints;

		Point2D[] list1= new Point2D[s.npoints];

		for(int i=0;i<s.npoints;i++) {
			list1[i]=new Point2D.Double(x1[i],y1[i]);
		}


		double area1=areaCalculator(list1);
		System.out.println(area1);
	}*/
}
