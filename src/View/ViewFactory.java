package View;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewFactory {

    public static ViewFactory defaultViewFactory = new ViewFactory();
    private static boolean mainViewInitialized = false;

    public Node resolveIcon(String treeItemValue){
        String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
        ImageView returnIcon;
        try {
            if(lowerCaseTreeItemValue.contains("inbox")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/inbox.png")));
            } else if(lowerCaseTreeItemValue.contains("sent")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/sent.png")));
            } else if(lowerCaseTreeItemValue.contains("spam")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/spam.png")));
            } else if(lowerCaseTreeItemValue.contains("@")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("Images/email.png")));
            } else if(lowerCaseTreeItemValue.contains("important")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/important.png")));
            } else if(lowerCaseTreeItemValue.contains("drafts")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/drafts.png")));
            }else if(lowerCaseTreeItemValue.contains("trash")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/trash.png")));
            }else if(lowerCaseTreeItemValue.contains("starred")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/starred.png")));
            }else if(lowerCaseTreeItemValue.contains("all mail")){
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/allMail.png")));
            }else{
                returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/folder.png")));
            }
        } catch (NullPointerException e) {
            System.out.println("Invalid image location!!!");
            e.printStackTrace();
            returnIcon = new ImageView();
        }

        returnIcon.setFitHeight(16);
        returnIcon.setFitWidth(16);

        return returnIcon;
    }

}




