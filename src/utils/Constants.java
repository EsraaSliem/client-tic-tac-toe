package utils;

import java.util.regex.Pattern;

/**
 *
 * @author Amer Shaker
 */
public interface Constants {

    public final static String SERVER_IP_ADDRESS = "10.0.0.247";
    public final static int PORT = 5000;
    // Server Services
    public final static String ACCOUNT_SERVICE = "Account";

    // User Status
    public final static String AVAILABLE = "Available";
    public final static String IS_PLAYING = "is Playing";
       public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z]+[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.com", Pattern.CASE_INSENSITIVE);

}
