package com.topup.database.util;

import com.topup.database.model.user.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utility {

    // Checking Regex
    public boolean checkUsernameRegex(User user) {
        String username = user.getUsername();
        Pattern regexUser = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");

        /** Penjelasan Regex
         * Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
         * Username allowed of the dot (.), underscore (_), and hyphen (-).
         * The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
         * The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
         * The number of characters must be between 5 to 20.
         */

        Matcher m = regexUser.matcher(username);

        return m.matches();
    }
    public boolean checkPasswordRegex(User user){
        String password = user.getPassword();
        Pattern regexPass = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");

        Matcher m = regexPass.matcher(password);

        /** Penjelasan Regex
         * Password must contain at least one digit [0-9].
         * Password must contain at least one lowercase Latin character [a-z].
         * Password must contain at least one uppercase Latin character [A-Z].
         * Password must contain at least one special character like ! @ # & ( ).
         * Password must contain a length of at least 8 characters and a maximum of 20 characters.
         */

        return m.matches();
    }
}

