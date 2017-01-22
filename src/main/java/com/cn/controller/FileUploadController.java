package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;

/**
 * Created by Administrator on 1/22/2017.
 */
@Controller
public class FileUploadController {
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/handleRegister")
    @ResponseBody
    public String handleRegister(@RequestParam String username, @RequestParam String password, @RequestParam LinkedList<MultipartFile> materyal) {
        System.out.println(username);
        System.out.println(password);

        for (int i = 0; i < materyal.size(); i++) {
            MultipartFile file = materyal.get(i);
            OutputStream stream = null;
            if (!file.isEmpty()) {
                String uploadUrl = "D:\\springprojects\\uploadedfiles\\" + file.getOriginalFilename();
                try {
                    byte[] bytes = file.getBytes();
                    stream =new BufferedOutputStream(new FileOutputStream(new File(uploadUrl)));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload  => " + e.getMessage();
                }
            } else {
                return "You failed to upload  because the file was empty.";
            }
        }

        return "success";
    }
}
