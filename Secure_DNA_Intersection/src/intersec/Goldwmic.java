package intersec;
import org.python.util.PythonInterpreter;

import java.io.IOException;

import org.python.core.*;
public class Goldwmic {
	public static int goldwmic_encryption(double falsepos, int expnumbofel, String incl, String insv) throws IOException{
	
	      try {
	          org.python.util.PythonInterpreter python = new org.python.util.PythonInterpreter();
	          python.execfile ( "./lib/goldwasser_micali.py" );
	       } catch ( Exception e ) {
	          System.out.println ( "An error was encountered." );
	       }
	      
	      
		return 5;
	}
}
