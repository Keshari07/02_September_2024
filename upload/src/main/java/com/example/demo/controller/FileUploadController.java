package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    private static String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String index(Model model) {
        return "upload"; // Renders the upload.html page
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
            return "upload";
        }

        try {
            // Ensure the upload directory exists
            Path uploadDirPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }

            // Save the file to the server
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            System.out.println("Uploading file to: " + path.toString()); // Debug print
            Files.write(path, bytes);

            // Add a flash attribute to be used after the redirect
            redirectAttributes.addFlashAttribute("success", true);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to upload file. Error: " + e.getMessage());
            return "upload";
        } catch(Exception exception) {
        	System.out.println("message"+exception.getMessage());
        }

        return "redirect:/";
    }

}
