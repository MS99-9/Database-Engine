package SAP_Elghalaba;

@SuppressWarnings("serial")
public class DBAppException extends Exception {
	
	public DBAppException() {
		super();
	}
	
	public DBAppException(String message) {
		super(message);
		//message="";
	}

}