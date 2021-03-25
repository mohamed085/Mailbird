package Services;

import Model.UserBean;

public interface UserServices {
    public boolean addNewUSer(UserBean userBean);
    public UserBean searchForUser(UserBean userBean);
}
