import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class AppLaunchDocumentationGUI extends JFrame {

 
    private static final Color COLOR_OLIVE = Color.decode("#606C38");
    private static final Color COLOR_CORNSILK = Color.decode("#FEFAE0");
    private static final Color COLOR_IVORY = Color.decode("#FFEFCD");

    static class DeveloperData {
        String name;
        int age;
        String email;
        List<String> tasks;
        String photoName;

        public DeveloperData(String name, int age, String email, List<String> tasks, String photoName) {
            this.name = name;
            this.age = age;
            this.email = email;
            this.tasks = tasks;
            this.photoName = photoName;
        }
    }

    public AppLaunchDocumentationGUI() {
        setTitle("Team DEVELOPERS - App Launch Documentation");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(COLOR_CORNSILK);
        mainContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("TEAM DEVELOPERS: APPLICATION LAUNCH PROFILES");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(COLOR_OLIVE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContainer.add(titleLabel);

        JLabel subtitleLabel = new JLabel("DEVELOPER DOCUMENTATION");
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        subtitleLabel.setForeground(COLOR_OLIVE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContainer.add(subtitleLabel);
        
        mainContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBackground(COLOR_CORNSILK);

        List<DeveloperData> team = getTeamMembersData();

        for (DeveloperData member : team) {
            cardsPanel.add(createProfileCard(member));
            cardsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBackground(COLOR_CORNSILK);
        scrollPane.getViewport().setBackground(COLOR_CORNSILK);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_OLIVE, 1));
        
        mainContainer.add(scrollPane);
        add(mainContainer);
    }

    private JPanel createProfileCard(DeveloperData member) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(COLOR_IVORY);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_OLIVE, 1),
                new EmptyBorder(12, 12, 12, 12)
        ));

        // Photo Panel (Names underneath have been completely removed)
        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setBackground(COLOR_IVORY);
        
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(110, 130));
        imgLabel.setMinimumSize(new Dimension(110, 130));
        imgLabel.setMaximumSize(new Dimension(110, 130));
        imgLabel.setBorder(BorderFactory.createLineBorder(COLOR_OLIVE, 1));

        ImageIcon icon = loadMemberImage(member.photoName, 110, 130);
        if (icon != null) {
            imgLabel.setIcon(icon);
        } else {
            imgLabel.setIcon(createPlaceholderIcon(member.name.split(" ")[0], 110, 130));
        }

        photoPanel.add(imgLabel, BorderLayout.CENTER);
        card.add(photoPanel, BorderLayout.WEST);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(COLOR_IVORY);

        JLabel profileHeader = new JLabel("DEVELOPERS PROFILE: " + member.name.toUpperCase());
        profileHeader.setFont(new Font("SansSerif", Font.BOLD, 13));
        profileHeader.setForeground(COLOR_OLIVE);
        infoPanel.add(profileHeader);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        addInfoRow(infoPanel, "NAME:", member.name);
        addInfoRow(infoPanel, "AGE:", String.valueOf(member.age));
        addInfoRow(infoPanel, "EMAIL ADDRESS:", member.email);

        JLabel tasksTitle = new JLabel("CONTRIBUTED:");
        tasksTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        tasksTitle.setForeground(COLOR_OLIVE);
        infoPanel.add(tasksTitle);

        for (String task : member.tasks) {
            JLabel taskLabel = new JLabel("  • " + task);
            taskLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
            taskLabel.setForeground(Color.DARK_GRAY);
            infoPanel.add(taskLabel);
        }

        card.add(infoPanel, BorderLayout.CENTER);
        return card;
    }

    private void addInfoRow(JPanel panel, String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row.setBackground(COLOR_IVORY);
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(COLOR_OLIVE);
        lbl.setPreferredSize(new Dimension(110, 18));
        
        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.PLAIN, 11));
        val.setForeground(Color.DARK_GRAY);

        row.add(lbl);
        row.add(val);
        panel.add(row);
    }

    private ImageIcon loadMemberImage(String photoName, int width, int height) {
        String[] possiblePaths = {
            "photos/" + photoName + ".jpg",
            "photos/" + photoName + ".JPG",
            "photos/" + photoName + ".png"
        };

        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    Image img = ImageIO.read(file);
                    if (img != null) {
                        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        return new ImageIcon(scaledImg);
                    }
                } catch (Exception e) {
                    // Skip on error
                }
            }
        }
        return null;
    }

    private ImageIcon createPlaceholderIcon(String firstName, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(COLOR_CORNSILK);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(COLOR_OLIVE);
        g2d.drawRect(0, 0, width - 1, height - 1);
        
        g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontMetrics fm = g2d.getFontMetrics();
        
        String line1 = "TEAM MEMBER";
        String line2 = firstName.toUpperCase();
        
        int x1 = (width - fm.stringWidth(line1)) / 2;
        int x2 = (width - fm.stringWidth(line2)) / 2;
        
        g2d.setColor(COLOR_OLIVE);
        g2d.drawString(line1, x1, height / 2 - 5);
        g2d.drawString(line2, x2, height / 2 + 15);
        g2d.dispose();
        
        return new ImageIcon(image);
    }

    private List<DeveloperData> getTeamMembersData() {
        List<DeveloperData> list = new ArrayList<>();

        list.add(new DeveloperData("JAMES G. MUÑOZ", 19, "jamesmunoz0933@gmail.com", 
            List.of("Creating an application type for journal entry (Ledger)", "Recent Saved (History for files opened/saved)"), "munoz"));
            
        list.add(new DeveloperData("Christian Chael M. Lesoy", 20, "christianchael@gmail.com", 
            List.of("Creating an application type for journal entry (12 columns)", "Designing database"), "lesoy"));
            
        list.add(new DeveloperData("Dennise Keen T. Abalde", 21, "keentorbeso@mail.com", 
            List.of("User log in"), "abalde"));
            
        list.add(new DeveloperData("Franz A. Roluna", 20, "rolunafranz@gmail.com", 
            List.of("Designing database", "Creator's for the info for the developers button"), "roluna"));
            
        list.add(new DeveloperData("Brent Evan O. Mondejar", 19, "mondejarbrent17@gmail.com", 
            List.of("State navigation", "Designing database"), "mondejar"));
            
        list.add(new DeveloperData("Joshua Lorenz Bantecil Bacalan", 20, "joshuabacalan0@gmail.com", 
            List.of("State navigation", "Designing database"), "bacalan"));
            
        list.add(new DeveloperData("IGOT, NASH ALEXANDER I.", 19, "nashigot123@gmail.com", 
            List.of("User log in", "Creator's for the info for the developers button"), "igot"));
            
        list.add(new DeveloperData("Salazar, Carl R.", 20, "salazarcarlr@gmail.com", 
            List.of("Journal entry", "Recent Saved (History for files opened/saved)"), "salazar"));
            
        list.add(new DeveloperData("Rubi Jeneisha S. Verano", 20, "veranorubijane@gmail.com", 
            List.of("Lay out design", "Color palette", "Name for our application", "Information gatherer"), "verano"));
            
        list.add(new DeveloperData("RHEAN KEITH V. ZABATE", 19, "maryjanebenjie02@gmail.com", 
            List.of("Creating a homepage", "Creating a dashboard", "Designing the logo", "Lay out design", "Log out button"), "zabate"));
            
        list.add(new DeveloperData("CRISPE, CRIS ALVEY", 19, "alveycrispe34@gmail.com", 
            List.of("Creator's for the info for the developers button"), "crispe"));

        return list;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppLaunchDocumentationGUI().setVisible(true);
        });
    }
}
