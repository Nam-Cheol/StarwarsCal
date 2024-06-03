package starwarsCalender;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TrayHandler {

    private JFrame frame;
    private SystemTray tray;
    private TrayIcon icon;

    public TrayHandler(JFrame frame) {
        this.frame = frame;
        initTray();
    }

    private void initTray() {
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            Image trayImage = new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage();
            icon = new TrayIcon(trayImage, "Starwars Calendar", createTrayPopupMenu());
            icon.setImageAutoSize(true);
        } else {
            JOptionPane.showMessageDialog(frame, "트레이를 최소화 할 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private PopupMenu createTrayPopupMenu() {
        PopupMenu popup = new PopupMenu();

        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                frame.setExtendedState(JFrame.NORMAL);
                tray.remove(icon);
            }
        });

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        popup.add(openItem);
        popup.addSeparator();
        popup.add(exitItem);

        return popup;
    }

    public void minimizeToTray() {
        if (tray != null && icon != null) {
            try {
                tray.add(icon);
                frame.setVisible(false);
                JOptionPane.showMessageDialog(null, "트레이 아이콘으로 최소화 되었습니다.");
            } catch (AWTException e) {
                System.err.println("Unable to add to tray.");
            }
        }
    }
}
