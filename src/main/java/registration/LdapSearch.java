package registration;

import org.apache.log4j.Logger;
import org.ini4j.Ini;
import org.ini4j.Profile;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author Andrey Smirnov
 * @date 13.02.2018
 */
class LdapSearch {

    private static final Logger LOGGER = Logger.getLogger(LdapSearch.class);

    static String getMailFromAD(String login) throws NamingException {
        Ini ini = new Ini();
        try {
            ini.load(LdapSearch.class.getResourceAsStream("/config.ini"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        Profile.Section section = ini.get("ldap");
        String email = null;
        //InitialDirContext constructor applies only Hashtable :(
        Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
        ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapEnv.put(Context.PROVIDER_URL, section.get("providerUrl"));
        ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        ldapEnv.put(Context.SECURITY_PRINCIPAL, section.get("user"));
        ldapEnv.put(Context.SECURITY_CREDENTIALS, section.get("pass"));
        InitialDirContext ldapContext = new InitialDirContext(ldapEnv);
        // Create the search controls
        SearchControls searchCtls = new SearchControls();
        // Specify the attributes to return
        String[] returnedAtts = {"samAccountName", "mail"};
        searchCtls.setReturningAttributes(returnedAtts);
        // Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // specify the LDAP search filter
        String searchFilter = "(&(objectCategory=user)(objectClass=person)(samAccountName=" + login + "))";
        // Specify the Base for the search
        String searchBase = section.get("searchBase");
        // Search for objects using the filter
        NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
        // Loop through the search results
        while (answer.hasMoreElements()) {
            SearchResult sr = answer.next();
            Attributes attrs = sr.getAttributes();
            email = attrs.get("mail").toString().split("mail: ")[1];
        }
        return email;
    }

    private LdapSearch() {
    }
}
