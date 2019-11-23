package org.zahid.apps.web.pos.entity.idgenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.zahid.apps.web.pos.entity.InvoiceMain;

import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class InvoiceIdGenerator implements IdentifierGenerator {

    private static final Logger LOG = LogManager.getLogger(InvoiceIdGenerator.class);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (object instanceof InvoiceMain) {
            final DateFormat sdf = new SimpleDateFormat("yyMMdd");
            final InvoiceMain invoiceMain = (InvoiceMain) object;
            final String invDate = sdf.format(invoiceMain.getInvDate());
            final String invType = invoiceMain.getInvType();
            LOG.info("Inv. Type: {}, Inv. Date: {}", invType, invDate);
            final Long value = generateID(invType, invDate, session.connection());
            return value;
        }
        return null;
    }

    private Long generateID(final String invType, final String invDate, Connection connection) {

        try {
            CallableStatement stmt = connection.prepareCall("{? = call XXIM_GENERATE_INV_NUM(?, ?)}");
            stmt.registerOutParameter(1, Types.BIGINT);
            stmt.setString(2, invType);
            stmt.setString(3, invDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
