import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import Model.EmailMessageBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {
    public static void main(String[] args) {
        EmailAccountFactory accountFactory = new EmailAccountFactory(
                "mohamed.emad627@ci.menofia.edu.eg",
                "MO0420sara",
                "Outlook");
        EmailAccountBean accountBean = accountFactory.createEmailAccount();

        ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
        accountBean.addEmailsToData(data);
    }
}
