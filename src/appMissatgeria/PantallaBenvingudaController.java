package appMissatgeria;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PantallaBenvingudaController implements Initializable {

    @FXML private ImageView ivLogo;
    @FXML private Button btStart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("logo.jpg");
        ivLogo.setImage(image);
    }

    @FXML
    protected void start(MouseEvent event){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("pantalla_principal.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("APP MISSATGERIA FJE EL CLOT - DESUMVILA");
            window.setScene(scene);
            window.show();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
