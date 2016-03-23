/**
 * 
 */
package jarvis.demo.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

/**
 * @author Jarvis
 *
 */
@WebService
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public class HelloWorld {

	@WebMethod
	@WebResult
	public String sayHelloWord(@WebParam String name) {
		return "My name is " + name;
	}

}
