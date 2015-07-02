package com.adobe.aod.monitoring.api.osgi;

/**
 *
 */
public class OSGiUtil {

    public static String validOSGiEventTopic(String topic) {
        char[] chars = topic.toCharArray();
        int length = chars.length;
        String validTopic = "";
        for (int i = 0; i < length; i++) {
            char ch = chars[i];
            if (ch == '/') {
                // Can't start or end with a '/' but anywhere else is okay
                if (i == 0 || (i == length - 1)) {
                    continue;
                }
                // Can't have "//" as that implies empty token
                if (chars[i - 1] == '/') {
                    continue;
                }
                validTopic += ch;
            }
            if (('A' <= ch) && (ch <= 'Z')) {
                validTopic += ch;
            }
            if (('a' <= ch) && (ch <= 'z')) {
                validTopic += ch;
            }
            if (('0' <= ch) && (ch <= '9')) {
                validTopic += ch;
            }
            if ((ch == '_') || (ch == '-')) {
                validTopic += ch;
            }
        }

        return validTopic;
    }
}
