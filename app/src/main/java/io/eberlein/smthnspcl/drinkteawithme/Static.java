package io.eberlein.smthnspcl.drinkteawithme;

import android.text.format.DateFormat;

import com.google.android.gms.common.util.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

class Static {
    static final String USERS = "users";
    static final String LAST_ONLINE = "lastOnline";
    static final String CREATED = "created";
    static final String DISPLAY_NAME = "displayName";
    static final String ONLINE = "online";
    static final String FRIENDS = "friends";
    static final String LAST_SESSION = "lastSession";
    static final String DATETIMEFORMAT = "EEE, d MMM yyyy HH:mm";
    static final String SESSIONS = "sessions";
    static final String ONLINE_TIMELINE = "onlineTimeline";
    static final String USERNAME_TIMELINE = "usernameTimeline";
    static final String OFFLINE_TIMELINE = "offlineTimeline";
    static final String TEA_TIME = "teaTime";
    static final String EMAIL = "email";
    static final String CUP_SIZE = "cupSize";
    static final String CUP_SIZES = "cupSizes";

    static String hash(String in) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(in.getBytes());
            return Hex.bytesToStringLowercase(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String getCurrentTimestamp() {
        return DateFormat.format(DATETIMEFORMAT, Calendar.getInstance().getTime()).toString();
    }
}
