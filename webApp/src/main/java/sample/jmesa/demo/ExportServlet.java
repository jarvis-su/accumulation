package sample.jmesa.demo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;

@SuppressWarnings("deprecation")
public class ExportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		TableFacade tableFacade = TableFacadeFactory.createTableFacade("presidents", request);
		tableFacade.setColumnProperties("id","name.firstName", "name.lastName", "term","career", "born");       
        tableFacade.setItems(new PresidentDao().getPresidents());
        tableFacade.setExportTypes(response, ExportType.CSV, ExportType.EXCEL, ExportType.PDF);
        //直接展现方式
        String html = tableFacade.render();			
		request.setAttribute("presidents", html);
		String tag = request.getParameter("tag");
		String url = "/index.jsp";
		if("2".equals(tag)){
			url = "/index-el.jsp";
		}		
		
		try {   
			RequestDispatcher rd = request.getRequestDispatcher(url);   			
		    rd.forward(request, response);   
		    return;   
		}catch(Exception e){
			/**
			 * 不加上异常处理在控制台报如下错误:
			 *  严重: Servlet.service() for servlet ExportServlet threw exception
				java.lang.IllegalStateException: Cannot forward after response has been committed
					at org.apache.catalina.core.ApplicationDispatcher.doForward(ApplicationDispatcher.java:312)
					at org.apache.catalina.core.ApplicationDispatcher.forward(ApplicationDispatcher.java:302)
					at demo.ExportServlet.doPost(ExportServlet.java:55)
					at demo.ExportServlet.doGet(ExportServlet.java:36)
					at javax.servlet.http.HttpServlet.service(HttpServlet.java:617)
					at javax.servlet.http.HttpServlet.service(HttpServlet.java:717)
					at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:290)
					at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)
					at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:233)
					at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:191)
					at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:128)
					at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)
					at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:109)
					at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:293)
					at org.apache.coyote.http11.Http11Processor.process(Http11Processor.java:849)
					at org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler.process(Http11Protocol.java:583)
					at org.apache.tomcat.util.net.JIoEndpoint$Worker.run(JIoEndpoint.java:454)
					at java.lang.Thread.run(Thread.java:619)
			 * */
		}  
		
	}
}
