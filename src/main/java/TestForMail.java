import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class TestForMail {
    public static void main( String[] args ) {
        String host="smtp.gmail.com";
        final String user="andreasnarep2@gmail.com";//change accordingly
        final String password="ifslypzmdwrpoktx";//change accordingly

        String to="andreasnarep2@gmail.com";//change accordingly

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient( Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("PING MESSAGE");
            message.setText("This is simple program of sending email using JavaMail API");

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch ( MessagingException e) {e.printStackTrace();}
    }
}
