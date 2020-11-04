package ro.ubb.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {


    public boolean validatePassword(String password){
        //Minimum eight characters, at least one lower case letter and one uppercase letter,
        // one number and one special character
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(password);
        return m.find();
    }
}
