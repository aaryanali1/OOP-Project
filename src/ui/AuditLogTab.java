package ui;

import fileio.AuditLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class AuditLogTab {
    @FXML private TextArea auditArea;
    @FXML private Button refresh;

    private AuditLog auditLog = new AuditLog();

    @FXML
    void handleRefresh(ActionEvent event) {
        refreshLogs();
    }

    public void refreshLogs() {
        auditArea.clear();
        for (String log : auditLog.loadAllLog()) {
            auditArea.appendText(log + "\n");
        }
    }

    void onTabSelected() {
        refreshLogs();
    }

    public void initialize() {
        auditArea.setEditable(false);
        refreshLogs();
    }
}
