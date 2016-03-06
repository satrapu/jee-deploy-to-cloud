package ro.satrapu.jeedeploytocloud;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author satrapu
 */
@WebServlet(name = "StatusServlet", urlPatterns = {"/status"})
public class StatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Resource(name = "java:jboss/datasources/PostgreSQLDS")
    private DataSource dataSource;

    /**
     * Processes HTTP requests.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Status</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Status</h1>");

            Map<String, Object> statusInfo = getStatusInfo(request);
            writeStatus(statusInfo, out);

            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * @param request
     * @return
     */
    private Map<String, Object> getStatusInfo(HttpServletRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Server IP", request.getLocalAddr());
        result.put("Server name", request.getLocalName());
        result.put("Server port", request.getLocalPort());
        result.put("Java version", System.getProperty("java.version"));

        try {
            DatabaseMetaData databaseMetaData = dataSource.getConnection().getMetaData();
            result.put("JDBC driver name", databaseMetaData.getDriverName());
            result.put("JDBC driver version", databaseMetaData.getDriverVersion());
            result.put("Database product name", databaseMetaData.getDatabaseProductName());
            result.put("Database product version", databaseMetaData.getDatabaseProductVersion());
        } catch (SQLException e) {
            e.printStackTrace();
            result.put("Data source", "Unable to get data source info");
        }

        result.put("Client IP", request.getRemoteAddr());
        result.put("Client name", request.getRemoteHost());
        result.put("Request URI", request.getRequestURI());
        result.put("Request method", request.getMethod());
        result.put("Current date", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));

        return result;
    }

    /**
     * @param statusInfo
     * @param writer
     */
    private static void writeStatus(Map<String, Object> statusInfo, PrintWriter writer) {
        writer.println("<ul>");

        for (String key : statusInfo.keySet()) {
            Object value = statusInfo.get(key);

            writer.println("<li>");
            writer.println(MessageFormat.format("{0}: {1}", key, value));
            writer.println("</li>");
        }

        writer.println("</ul>");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException(new UnsupportedOperationException("POST verb not supported"));
    }
}
