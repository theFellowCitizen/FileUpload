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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
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
    private String fileContent;
    private String folder = "C:\\Work";

    private String data = "";

    private String dName = "";
    private Path path = Paths.get("C:\\Work\\images")/*Paths.get("/home/anon/NetBeansProjects/MKDir/images")*/;

    /**
     * Creates a new instance of WebsiteController
     */
    public WebsiteController() {

    }

    public void upload() {
        if (file != null) {
            try {
                setFileContent(new Scanner(file.getInputStream()).useDelimiter("\\A").next());
                saveFile();
            } catch (IOException e) {
                // Error handling
            }
        }
    }

    public void saveFile() {

        createDir();
        String saveFolder = path + "\\" + this.data;
        InputStream input = null;
        try {
            input = file.getInputStream();
            String fileName = file.getSubmittedFileName();
            Files.copy(input, new File(saveFolder, fileName).toPath());
            addTextWatermark(fileName, file, new File(saveFolder + "\\" + "Watermarked" + fileName));
            System.out.println(saveFolder + "/" + fileName);
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

        if (!Files.exists(Paths.get(path.toString() + "/animals"))
                && !Files.exists(Paths.get(path.toString() + "/books"))
                && !Files.exists(Paths.get(path.toString() + "/games"))) {

            new File(path + "/animals").mkdirs();
            new File(path + "/games").mkdirs();
            new File(path + "/books").mkdirs();
        }

        if (!dName.trim().equals("")) {
            Path ownPath = Paths.get(path.getFileName() + "/" + dName);
            if (!Files.exists(ownPath)) {
//                ownPath.toFile().mkdir();
                new File(path + "/" + dName).mkdir();
            }
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

    public int listenDirectory() {
        int i = new File(path.toString()).list().length;
        return i;
    }

    public String[] dirArray() {
        String[] i = new File(path.toString()).list();
        return i;
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
     * @return the fileContent
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * @param fileContent the fileContent to set
     */
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
