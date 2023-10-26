package vista;

import accesoDatos.Repositorio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static oracle.sql.NUMBER.e;

public class loginController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContraseña;
    @FXML
    private Button btnIniciarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        try {
            if (txtUsuario.getText().isEmpty() || txtContraseña.getText().isEmpty()) {
                throw new IllegalArgumentException();
            }
            String usuario = txtUsuario.getText();
            String contraseña = txtContraseña.getText();
            Repositorio repo = new Repositorio();
            if (repo.login(usuario, contraseña)) {
                confirmacion();
                cambioEscenaMenu();
            } else {
                anulacion();
            }
        } catch (IllegalArgumentException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al iniciar sesión");
            alerta.setContentText("Debe llenar todos los datos");
            alerta.showAndWait();
        }

    }

    public void confirmacion() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("Inicio de sesión exitoso!");
        alerta.showAndWait();
    }

    public void anulacion() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error al iniciar sesión");
        alerta.setContentText("Usuario y/o contraseña incorrectos");
        alerta.showAndWait();

    }

    public void cambioEscenaMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/menuPrincipal.fxml"));
            Parent root = loader.load();
            MenuPrincipalController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            Stage escena = (Stage) this.btnIniciarSesion.getScene().getWindow();
            escena.close();

        } catch (Exception e) {
            System.err.println("Error al Cargar la pantalla");
        }
    }

    public static void cerrarVentana(ActionEvent e) {
        Node source = (Node) e.getSource();     //Me devuelve el elemento al que hice click
        Stage stage = (Stage) source.getScene().getWindow();    //Me devuelve la ventana donde se encuentra el elemento
        stage.close();                          //Me cierra la ventana
    }
}
