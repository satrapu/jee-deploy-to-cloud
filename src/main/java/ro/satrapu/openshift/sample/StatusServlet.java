package ro.satrapu.openshift.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author satrapu
 */
@WebServlet(name = "StatusServlet", urlPatterns = {"/status"})
public class StatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Processes HTTP requests.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");

	try (PrintWriter out = response.getWriter()) {
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>OpenShift Sample Status</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>OpenShift Sample Status</h1>");

	    Map<String, String> statusInfo = getStatusInfo(request);
	    writeStatus(statusInfo, out);

	    out.println("</body>");
	    out.println("</html>");
	}
    }

    /**
     *
     * @param request
     * @return
     */
    private static Map<String, String> getStatusInfo(HttpServletRequest request) {
	Map<String, String> result = new LinkedHashMap<>();
	result.put("Current date", DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL).format(new Date()));
	result.put("Host name", request.getRemoteHost());
	result.put("Host IP", request.getRemoteAddr());
	result.put("Port", "" + request.getLocalPort());
	result.put("Request context path", request.getContextPath());
	result.put("Request method", request.getMethod());

	return result;
    }

    /**
     *
     * @param statusInfo
     * @param response
     */
    private static void writeStatus(Map<String, String> statusInfo, PrintWriter writer) {
	writer.println("<ul>");

	for (String key : statusInfo.keySet()) {
	    String value = statusInfo.get(key);

	    writer.println("<li>");
	    writer.println(MessageFormat.format("{0}: {1}", key, value));
	    writer.println("</li>");
	}

	writer.println("</ul>");
    }
}
