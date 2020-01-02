package org.zahid.apps.web.library.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.config.ConfigProperties;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.Organization;
import org.zahid.apps.web.library.mapper.BookMapper;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.payload.response.SearchBookResponse;
import org.zahid.apps.web.library.security.service.UserPrincipal;

import java.util.*;

@Component
public class Miscellaneous {

    private static final Logger LOG = LogManager.getLogger(Miscellaneous.class);
    private static final String SERVLET_START = "/reportservlet?repName=";
    private static ConfigProperties configProperties;
    private static JdbcTemplate jdbcTemplate;
    private static SimpleJdbcCall simpleJdbcCall;
    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static BookMapper bookMapper;

    @Autowired
    public Miscellaneous(
            final ConfigProperties configProperties,
            final JdbcTemplate jdbcTemplate,
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            final BookMapper bookMapper
    ) {
        this.configProperties = configProperties;
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.bookMapper = bookMapper;
    }

    public static Exception getNestedException(Exception rootException) {
        if (rootException.getCause() == null) {
            LOG.log(Level.INFO, "Last Exception: {}", rootException);
            return rootException;
        } else {
            Exception cause = (Exception) rootException.getCause();
            LOG.log(Level.INFO, rootException.getClass().getName() + "Exception Cause: {0}", cause);
            return getNestedException(cause);
        }
    }

    public static String getResourceMessage(String rsrcBundle, String key)
            throws NullPointerException, MissingResourceException, ClassCastException {
        ResourceBundle bundle = ResourceBundle.getBundle(rsrcBundle);
        return bundle.getString(key);
    }

    public static ResourceBundle getResourceBundle(String bundle)
            throws NullPointerException, MissingResourceException {
        return ResourceBundle.getBundle(bundle);
    }

    public static String convertDBError(Exception e) {
        final String[] resourceMessage = {null};
        final boolean[] found = {false};
        String errorMessage = Miscellaneous.getNestedException(e).getMessage();
        Set<ResourceBundle> rbList = new HashSet<>();
        rbList.add(Miscellaneous.getResourceBundle("dbconstraints"));
        rbList.add(Miscellaneous.getResourceBundle("dberrors"));

        rbList.stream().filter(f -> false == found[0]).forEach(rb -> {
//          rbList.forEach(rb -> {
            rb.keySet()
                    .stream()
                    .filter(key -> errorMessage.toUpperCase().contains(key.toUpperCase()))
                    .map(s -> Miscellaneous.getResourceMessage(rb.getBaseBundleName(), s))
                    .forEach(message -> {
                        LOG.log(Level.INFO, "Message: {0}", message);
                        found[0] = true;
                        resourceMessage[0] = message;
                    });
        });

//        outer:
//        for (ResourceBundle rb : rbList) {
// for (String key : rb.keySet()) {
//                if (errorMessage.toUpperCase().contains(key.toUpperCase())) {
//                    String msg = Miscellaneous.getResourceMessage(rb.getBaseBundleName(), key);
//                    resourceMessage[0] = msg;
//                    break outer;
//                }
//            }
//        }

        return resourceMessage[0];
    }

//    public static boolean exists(String operationClass, Long id) throws ClassNotFoundException, NoSuchMethodException {
//        Class aClass = Class.forName(operationClass);
//        Object obj = aClass.cast(new Object());
//        Method method = aClass.getDeclaredMethod("exists");
//        return false;
//    }

//  public static int exists(String table, String column, Long columnValue) {
//    final String dbServer = configProperties.getDb().get("server");
//    final String dbPort = configProperties.getDb().get("port");
//    final String dbService = configProperties.getDb().get("service");
//    final String dbUsername = configProperties.getDb().get("username");
//    final String dbPassword = configProperties.getDb().get("password");
//
//    LOG.debug("Server: {}", dbServer);
//    LOG.debug("port: {}", configProperties.getApp().get("port"));
//    int result = 0;
//    try {
//      String sql = "BEGIN :RESULT := RECORD_EXISTS ( :P_TABLE, :P_COLUMN, :P_COLUMN_VALUE); END;";
////            System.out.println("SF Result: " + sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("PTABLE", table).setParameter("PCOLUMN", column).setParameter("PID", id).getSingleResult());
//      Connection conn = DB.getInstance(dbServer, dbPort, dbService, dbUsername, dbPassword)
//          .getConnection();
//      CallableStatement stmt = conn.prepareCall(sql);
//      stmt.registerOutParameter("RESULT", Types.INTEGER);
//      stmt.setString("P_TABLE", table);
//      stmt.setString("P_COLUMN", column);
//      stmt.setLong("P_COLUMN_VALUE", columnValue);
//      stmt.execute();
//      result = stmt.getInt("RESULT");
//      LOG.info("Result: ", result);
//      conn.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//    /*
////        String sql = "SELECT XXIM_RECORD_EXISTS (" + table + ", " + column + ", " + id + ") FROM DUAL";
////                System.out.println("Query: " + sql);
////        int result = jdbcTemplate.queryForInt(
////                sql, Integer.class);
////        System.out.println("Result: " + result);
//        MapSqlParameterSource in = new MapSqlParameterSource();
//        in.addValue("P_TABLE", table);
//        in.addValue("P_COLUMN", column);
//        in.addValue("P_ID", id);
//        Integer res = countRecordsJdbcCall.executeFunction(Integer.class, in);
//        System.out.println("Result: " + res);
//
////        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withFunctionName("XXIM_RECORD_EXISTS");
////        MapSqlParameterSource in = new MapSqlParameterSource();
////        in.addValue("P_TABLE", table);
////        in.addValue("P_COLUMN", column);
////        in.addValue("P_ID", id);
////        Map<String, Object> out = jdbcCall.execute(in);
////        for (Map.Entry<String, Object> entry : out.entrySet()) {
////            System.out.println(entry.getValue());
////        }
////        ;
////        String sql = "SELECT COUNT(1) FROM " + table + " WHERE " + column + " = " + id;
////        SessionFactory factory =
////                HibernateUtil.getSessionFactory();
////        System.out.println("Result: " + factory.openSession().createSQLQuery(sql).getSingleResult());
////        EntityManager em = factory.createEntityManager();
////        System.out.println("Result: " + em.createNativeQuery(sql)
////                .setParameter(1, table)
////                .setParameter(2, column)
////                .setParameter(3, id)
////                .getSingleResult());*/
//    return result;
//  }

/*  public static Integer exists(String table, String column, Long columnValue) {
    simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withFunctionName("RECORD_EXISTS");
    final SqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_TABLE", table)
        .addValue("P_COLUMN", column)
        .addValue("P_COLUMN_VALUE", columnValue);
    final Integer result = simpleJdbcCall.executeFunction(Integer.class, in);
    LOG.info("Result: {}", result);
    return result;
  }*/

    public static Integer exists(String table, String column, Long columnValue) {
        Integer result = null;
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("P_TABLE", table)
                .addValue("P_COLUMN", column)
                .addValue("P_COLUMN_VALUE", columnValue);
        result = namedParameterJdbcTemplate.queryForObject(
                "SELECT RECORD_EXISTS (:P_TABLE, :P_COLUMN, :P_COLUMN_VALUE) FROM DUAL", namedParameters, Integer.class);
        return result;
    }

    public static List<SearchBookResponse> searchBookByCriteria(final Integer author, final Integer subject, final Integer publisher, final Integer researcher) {
        final String sql = "SELECT B.BOOK_ID\n" +
                "      ,B.BOOK_NAME\n" +
                "      ,B.PUBLICATION_DATE\n" +
                "      ,B.BOOK_CONDITION\n" +
                "      ,B.PURCHASED\n" +
                "      ,A.AUTHOR_ID\n" +
                "      ,A.AUTHOR_NAME\n" +
                "      ,S.SUBJECT_ID\n" +
                "      ,S.SUBJECT_NAME\n" +
                "      ,P.PUBLISHER_ID\n" +
                "      ,P.PUBLISHER_NAME\n" +
                "      ,R.RESEARCHER_ID\n" +
                "      ,R.RESEARCHER_NAME\n" +
//                "      ,F.SHELF_ID\n" +
//                "      ,F.SHELF_NAME\n" +
                "      ,B.REMARKS\n" +
                "  FROM BOOK B\n" +
                "      ,AUTHOR A\n" +
                "      ,SUBJECT S\n" +
                "      ,PUBLISHER P\n" +
                "      ,RESEARCHER R\n" +
                "      ,SHELF F\n" +
                " WHERE     B.AUTHOR_ID = A.AUTHOR_ID(+)\n" +
                "       AND B.SUBJECT_ID = S.SUBJECT_ID(+)\n" +
                "       AND B.PUBLISHER_ID = P.PUBLISHER_ID(+)\n" +
                "       AND B.RESEARCHER_ID = R.RESEARCHER_ID(+)\n" +
                "       AND B.SHELF_ID = F.SHELF_ID(+)\n" +
                "       AND ( :AUTHOR IS NULL OR B.AUTHOR_ID = :AUTHOR)\n" +
                "       AND ( :SUBJECT IS NULL OR B.SUBJECT_ID = :SUBJECT)\n" +
                "       AND ( :PUBLISHER IS NULL OR B.PUBLISHER_ID = :PUBLISHER)\n" +
                "       AND ( :RESEARCHER IS NULL OR B.RESEARCHER_ID = :RESEARCHER)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("AUTHOR", author)
                .addValue("SUBJECT", subject)
                .addValue("PUBLISHER", publisher)
                .addValue("RESEARCHER", researcher);
        final List<SearchBookResponse> result = namedParameterJdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<SearchBookResponse>(SearchBookResponse.class));
        return result;
    }


    public static String getSubjectHierarchy(final Long subjectId) {
        String result = null;
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("P_SUBJECT_ID", subjectId);
        result = namedParameterJdbcTemplate.queryForObject(
                "SELECT GET_SUBJECT_HIERARCHY (:P_SUBJECT_ID) FROM DUAL", namedParameters, String.class);
        return result;
    }

    public static final String callReport(String repName, JSONObject params) {
        String reportURL =
                SERVLET_START + repName + (params != null ? "&params=" + params.toString() : "");
        LOG.info("Report URL: " + reportURL);
        return reportURL;
    }

    public static Optional<Organization> getCurrentOrganization() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Optional.ofNullable(userPrincipal.getOrganization());
    }
}
