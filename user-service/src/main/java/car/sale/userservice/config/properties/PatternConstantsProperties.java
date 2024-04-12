//package car.sale.userservice.config.properties;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.util.regex.Pattern;
//
//@Getter
//@Setter
//@Component
//@ConfigurationProperties(prefix = "app.patterns")
//public class PatternConstantsProperties {
//
//    private String emailPattern;
//    private String namePattern;
//    private String phonePattern;
//    private String groupedPhoneNumbersPattern;
//    private String passwordPattern;
//
//    public Pattern getEmailPattern() {
//        return Pattern.compile(emailPattern);
//    }
//
//    public Pattern getNamePattern() {
//        return Pattern.compile(namePattern);
//    }
//
//    public Pattern getPhonePattern() {
//        return Pattern.compile(phonePattern);
//    }
//
//    public Pattern getGroupedPhoneNumbersPattern() {
//        return Pattern.compile(groupedPhoneNumbersPattern);
//    }
//
//    public Pattern getPasswordPattern() {
//        return Pattern.compile(passwordPattern);
//    }
//}
