package com.example.library.controllers;

import com.example.library.models.Account;
import com.example.library.models.Reader;
import com.example.library.services.impl.AccountServiceImpl;
import com.example.library.services.IAccountService;
import com.example.library.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.UUID;

import static com.example.library.common.Regex.isValid;

public class RegisterController {
    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtAddress;
    @FXML
    private DatePicker dpDob;
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private PasswordField pwMatKhau;
    @FXML
    private PasswordField pwReMatKhau;

    private final IAccountService accountService;

    public RegisterController() {
        accountService = new AccountServiceImpl();
    }

    public void onClickRegister(ActionEvent actionEvent) {
        String username = txtTaiKhoan.getText();
        String password = pwMatKhau.getText();
        String rePassword = pwReMatKhau.getText();

        String fullname = txtFullname.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String address = txtAddress.getText();
        dpDob.getValue();
        String dob = dpDob.getValue().toString();


        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty() ||
                fullname.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || dob.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Tất cả các trường không được bỏ trống!");
            return;
        }

        if (!password.equals(rePassword)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Mật khẩu và mật khẩu nhập lại không trùng khớp!");
            return;
        }

        if (!isValid("EMAIL", email)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Email không đúng định dạng!");
            return;
        }

        if (!isValid("PHONE_NUMBER", phoneNumber)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số điện thoại không đúng định dạng!");
            return;
        }

        Account account = Account.builder()
                .username(username)
                .password(password)
                .role("reader")
                .build();

        Reader reader = Reader.builder()
                .readerName(fullname)
                .readerId("R" + UUID.randomUUID().toString().substring(0, 5).toUpperCase())
                .readerAddress(address)
                .readerDOB(LocalDate.parse(dob))
                .readerEmail(email)
                .readerPhone(phoneNumber)
                .build();
        try {
            if (accountService.registerAccount(account, reader)) {
                AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Đăng kí thành công");
            } else {
                AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Đăng kí thất bại, tài khoản đăng kí đã tồn tại!");
            }
        } catch (Exception e) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, e.getMessage());
        }
    }
}
