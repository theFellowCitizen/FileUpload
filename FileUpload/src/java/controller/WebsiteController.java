package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;

/**
 *
 * @author kbwschuler
 */
@Named(value = "websiteController")
@SessionScoped
public class WebsiteController implements Serializable {

    private Part file = null;

    private String data = "";
    private String osName = System.getProperty("os.name").toLowerCase();
    private String linuxHome = System.getProperty("user.home");

    private String dName = "";
    private final Path pathWindows = Paths.get("C:\\Work\\images");
    private final Path pathLinux = Paths.get(linuxHome + "/images");

    private String prename = "";
    private String lastname = "";
    private Date birthDate = null;
    private String email = "";
    private String username = "";
    private String password = "";
 
    /**
     * Creates a new instance of WebsiteController
     */
    public WebsiteController() {

    }

    //the method to the upload accessed from xhtml
    //file is the selected in the xhtml    
    public void upload() {
        if (file != null) {
            saveFile();
        }
    }

    //first the folders are being created if they dont already exist
    //obviously the upload won't happen if no folder is selected to begin with
    public void saveFile() {
        createDir();
        String saveFolder = null;
        //first we will determine if the server runs linux or windows
        //not to encounter any path
        if (osName.contains("windows")) {
            saveFolder = pathWindows + "\\" + this.data;
        } else if (osName.contains("linux")) {
            saveFolder = pathLinux + "/" + this.data;
        }
        InputStream input = null;
        try {
            input = file.getInputStream();
            String fileName = file.getSubmittedFileName();
            Files.copy(input, new File(saveFolder, fileName).toPath());
            if (osName.contains("windows")) {
                addTextWatermark(fileName, file, new File(saveFolder + "\\" + "Watermarked" + fileName));
            } else if (osName.contains("linux")) {
                addTextWatermark(fileName, file, new File(saveFolder + "/" + "Watermarked" + fileName));
            }
        } catch (IOException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void addTextWatermark(String text, Part sourceImageFile, File destImageFile) {
        text = "uploadedFile";
        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile.getInputStream());
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 128));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            // paints the textual watermark
            g2d.drawString(text, centerX, centerY);

            ImageIO.write(sourceImage, "png", destImageFile);
            g2d.dispose();

            System.out.println("The tex watermark is added to the image.");

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void createDir() {
        if (osName.contains("windows")) {
            if (!Files.exists(Paths.get(pathWindows.toString() + "/animals"))
                    && !Files.exists(Paths.get(pathWindows.toString() + "/books"))
                    && !Files.exists(Paths.get(pathWindows.toString() + "/games"))) {

                new File(pathWindows + "/animals").mkdirs();
                new File(pathWindows + "/games").mkdirs();
                new File(pathWindows + "/books").mkdirs();
            }
        } else if (osName.contains("linux")) {
            if (!Files.exists(Paths.get(pathLinux.toString() + "/animals"))
                    && !Files.exists(Paths.get(pathLinux.toString() + "/books"))
                    && !Files.exists(Paths.get(pathLinux.toString() + "/games"))) {

                new File(pathLinux + "/animals").mkdirs();
                new File(pathLinux + "/games").mkdirs();
                new File(pathLinux + "/books").mkdirs();
            }
        }

        if (osName.contains("windows")) {
            if (!dName.trim().equals("")) {
                Path ownPath = Paths.get(pathWindows.getFileName() + "/" + dName);
                if (!Files.exists(ownPath)) {
//                ownPath.toFile().mkdir();
                    new File(pathWindows + "/" + dName).mkdir();
                }
            }
        } else if (osName.contains("linux")) {
            if (!dName.trim().equals("")) {
                Path ownPath = Paths.get(pathLinux.getFileName() + "/" + dName);
                if (!Files.exists(ownPath)) {
//                ownPath.toFile().mkdir();
                    new File(pathLinux + "/" + dName).mkdir();
                }
            }
        }

    }

    public String[] dirArray() {
        String[] i = null;
        if (osName.contains("windows")) {
            i = new File(pathWindows.toString()).list();
        } else if (osName.contains("linux")) {
            i = new File(pathLinux.toString()).list();
        }

        return i;
    }
    
    public void login(){
        try {
            FileWriter fl = new FileWriter(pathLinux.toString()+"login.txt");
            BufferedWriter bw = new BufferedWriter(fl);
            bw.write(prename);
            bw.append(lastname);
            bw.append((CharSequence) birthDate);
            bw.append(username);
            bw.append(password);
        } catch (IOException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    /**
     * @return the dName
     */
    public String getDName() {
        return dName;
    }

    /**
     * @param dName the dName to set
     */
    public void setDName(String dName) {
        this.dName = dName;
    }

    /**
     * @return the osName
     */
    public String getOsName() {
        return osName;
    }

    /**
     * @param osName the osName to set
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    /**
     * @return the prename
     */
    public String getPrename() {
        return prename;
    }

    /**
     * @param prename the prename to set
     */
    public void setPrename(String prename) {
        this.prename = prename;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
