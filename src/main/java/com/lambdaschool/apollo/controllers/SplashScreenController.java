package com.lambdaschool.apollo.controllers;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class SplashScreenController {
    @GetMapping(value = "/", produces = "image/gif")
    public void splashScreen (HttpServletResponse response) throws IOException {
        BufferedImage image = ImageIO.read(new File("screenshots/splash.gif"));
        response.setContentType("image/gif");

        ImageIO.write(image, "gif", response.getOutputStream());
        response.getOutputStream().close();
    }
}
