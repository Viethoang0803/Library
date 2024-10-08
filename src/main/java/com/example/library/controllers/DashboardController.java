package com.example.library.controllers;

import com.example.library.App;
import com.example.library.services.impl.BorrowServiceImpl;
import com.example.library.services.IBorrowService;
import com.example.library.utils.AlertUtil;
import com.example.library.utils.UserContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private ListView<String> lstMenu;
    @FXML
    private AnchorPane pane;

    private final IBorrowService borrowService;

    public DashboardController() {
        this.borrowService = new BorrowServiceImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DashboardController");

//        lstMenu.getItems().addAll("Quản lý sách", "Quản lý độc giả", "Quản lý mượn trả", "Thống kê");
//        lstMenu.getSelectionModel().selectFirst();
//        loadPane(lstMenu.getSelectionModel().getSelectedItem());
        initMenuByRole(UserContext.getInstance().getRole());

        if (borrowService.isReaderLate(UserContext.getInstance().getReaderId())) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Cảnh báo", null, "Bạn có sách quá hạn chưa trả, hãy sắp xếp để trả sách sớm nhất có thể");
        }

    }

    public void initMenuByRole(String role) {
        if (role.equalsIgnoreCase("reader")) {
            lstMenu.getItems().addAll("Thông tin chung", "Sách có thể mượn", "Lịch sử mượn", "Yêu cầu mượn");
        }

        if (role.equalsIgnoreCase("librarian")) {
        lstMenu.getItems().addAll("Thông tin chung", "Quản lý sách", "Quản lý người đọc", "Các yêu cầu mượn", "Trả sách", "Thống kê");
        }

        lstMenu.getSelectionModel().selectFirst();
        loadPane(lstMenu.getSelectionModel().getSelectedItem());
    }

    public void onSelected(MouseEvent mouseEvent) {
        String selectedItem = lstMenu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            loadPane(selectedItem);
        }
    }

    private void loadPane(String selected) {
        String frm = "";

        switch (selected) {
            case "Quản lý sách":
                frm = "/com/example/library/BookManagementFrm.fxml";
                break;
            case "Quản lý người đọc":
                frm = "/com/example/library/ReaderManagementFrm.fxml";
                break;
            case "Borrow management":
                frm = "/com/example/library/BorrowManagementFrm.fxml";
                break;
            case "Thống kê":
                frm = "/com/example/library/StaticFrm.fxml";
                break;

            case "Các yêu cầu mượn":
                frm = "/com/example/library/RequestFrm.fxml";
                break;

            case "Trả sách":
                frm = "/com/example/library/ReturnFrm.fxml";
                break;

            // client
            case "Sách có thể mượn":
                frm = "/com/example/library/BookManagementFrm.fxml";
                break;
            case "Thông tin chung":
                frm = "/com/example/library/InformationFrm.fxml";
                break;
            case "Lịch sử mượn":
                frm = "/com/example/library/BorrowHistoryFrm.fxml";
                BorrowHistoryController.setReaderId(UserContext.getInstance().getReaderId());
                break;
            case "Yêu cầu mượn":
                frm = "/com/example/library/RequestFrm.fxml";
                break;
            default:
                break;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(frm));
            pane.getChildren().clear();
            AnchorPane newPane = loader.load();
            pane.getChildren().add(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void onClickLogout(ActionEvent actionEvent) throws IOException {
        UserContext.getInstance().clearContext();

        App.setRoot("LoginFrm");
    }

    public void onClickChangePassword(ActionEvent actionEvent) throws IOException {
        App.setRootPop("ChangePassFrm", "Đổi mật khẩu", false);
    }
}
