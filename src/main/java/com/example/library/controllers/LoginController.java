package com.example.library.controllers;

import com.example.library.App;
import com.example.library.models.Account;
import com.example.library.services.impl.AccountServiceImpl;
import com.example.library.services.IAccountService;
import com.example.library.utils.AlertUtil;
import com.example.library.utils.UserContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private ComboBox<String> cbRole;
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private PasswordField pwMatKhau;

    private final IAccountService accountService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.getItems().addAll("Librarian", "Reader");
        cbRole.setValue("Reader");
    }

    public LoginController() {
        this.accountService = new AccountServiceImpl();
    }

    public void onClickLogin(ActionEvent actionEvent) throws IOException {
        String username = txtTaiKhoan.getText();
        String password = pwMatKhau.getText();
        String role = cbRole.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Tài khoản hoặc mật khẩu không được để trống!");
            return;
        }

        Account account = Account.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
        if (accountService.checkAccount(account)) {

            if (accountService.isBlocked(username)) {
                AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Tài khoản của bạn đã bị khóa, hãy liên hệ với thủ thư để biết thêm thông tin!");
                return;
            }

            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Đăng nhập thành công!");

            UserContext.getInstance().setRole(role);
            UserContext.getInstance().setUsername(username);

            App.setRoot("DashboardFrm");


        } else {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "tài khoản hoặc mật khẩu sai!");
        }
    }

    public void onClickRegister(ActionEvent actionEvent) throws IOException {
        App.setRootPop("RegisterFrm", "Register", false);
    }


    public void onClickResetPassword(ActionEvent actionEvent) throws IOException {
        App.setRootPop("EmailFrm", "Đặt lại mật khẩu", false);
    }
}
