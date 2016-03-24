package com.cts.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileDownload {

    @Autowired
    ServletContext context;

    @RequestMapping("/download/{name}")
    public void download(@PathVariable("name") String filename, HttpServletResponse response) throws IOException {
        InputStream is = new FileInputStream(new File(context.getRealPath("") + "/resources/" + filename + ".pdf"));
        // BufferedInputStream bis = new BufferedInputStream(is);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".pdf\"");
        IOUtils.copy(is, response.getOutputStream());

        response.flushBuffer();
        // OutputStream os = response.getOutputStream();
        // byte[] b = new byte[1024];
        // while (bis.read(b) > 0) {
        // os.write(b);
        // os.flush();
        // }
        //
        // os.close();
        // is.close();
    }
}
