package rename.common;



public class Context {

	private static final Context CONTEXT = new Context();


	public static Context sharedContext() {
		return CONTEXT;
	}

	public Context() {
				
	}
		
	
}
