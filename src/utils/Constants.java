package utils;

import java.util.regex.Pattern;

public interface Constants {

    public final static String SERVER_IP_ADDRESS = "192.168.1.9";


    public final static int PORT = 5000;
    // Server Services
    public final static String ACCOUNT_SERVICE = "Account";
    // User Status
    public final static String AVAILABLE = "Available";
    public final static String IS_PLAYING = "is Playing";
    public final static String playNowText = "play in single mode ";
    public static final Pattern VALID_Name_REGEX = Pattern.compile("^[A-Z]+", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z]+[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.com", Pattern.CASE_INSENSITIVE);
}
