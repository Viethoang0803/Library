package com.example.library.controllers.client;

import com.example.library.services.impl.AccountServiceImpl;
import com.example.library.services.IAccountService;
import com.example.library.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EmailController {
    public TextField txtEmail;

    private final IAccountService accountService;

    public EmailController() {
        this.accountService = new AccountServiceImpl();
    }

    public void btnSubmit(ActionEvent actionEvent) {
        System.out.println(txtEmail.getText());
        try {
            accountService.resetPassword(txtEmail.getText());
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thành công", null, "Đặt lại mật khẩu thành công, hãy kiểm tra email của bạn để biết thêm thông tin!");
        } catch (Exception e) {
            AlertUtil.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Lỗi", null, e.getMessage());
        }
    }
}
