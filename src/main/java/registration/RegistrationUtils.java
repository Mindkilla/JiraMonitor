package registration;

import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.security.SecureRandom;
import java.sql.SQLException;

import static java.lang.Math.abs;

/**
 * @author Andrey Smirnov
 * @date 14.02.2018
 */
public class RegistrationUtils {
    private static final Logger LOGGER = Logger.getLogger(RegistrationUtils.class);

    private static void registerUser(String login, Integer userId) {
        try {
            SqLiteConnector.connAndRegister(userId, login);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static boolean sendRegCode(String login, Integer id) {
        String email = null;
        try {
            email = LdapSearch.getMailFromAD(login);
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        if (email != null) {
            String code = generateCode();
            try {
                SqLiteConnector.connAndWrite(id, login, code);
            } catch (ClassNotFoundException | SQLException e) {
                LOGGER.error(e.getMessage());
                return false;
            }
            EmailSender.sendEmail(email, code);
            return true;
        }
        return false;
    }

    public static boolean checkRegCode(String code, Integer userId) {
        String login = null;
        try {
            login = SqLiteConnector.connAndRead(userId, code);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (login == null)
            return false;

        registerUser(login, userId);
        return true;
    }

    public static Boolean isRegisteredUser(String userName, Integer userid) {
        String current = isRegisteredUser(userid);
        return current != null && current.equals(userName);
    }

    public static String isRegisteredUser(Integer userId) {
        String current = "";
        try {
            current = SqLiteConnector.connAndCheck(userId);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return current;
    }

    private static String generateCode() {

        SecureRandom random = new SecureRandom();
        long code = abs(random.nextLong() % 10000);
        return String.format("%04d", code);
    }

    private RegistrationUtils() {
    }
}
