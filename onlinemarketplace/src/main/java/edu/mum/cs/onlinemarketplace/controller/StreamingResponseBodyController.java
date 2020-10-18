package edu.mum.cs.onlinemarketplace.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class StreamingResponseBodyController {

    @RequestMapping(value = "/files/{file}", method = RequestMethod.GET)
    public InputStreamResource FileSystemResource(HttpServletResponse response, @PathVariable("file") String file) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file + "\".pdf");
        String path = "./src/main/resources/static/files/" + file + ".pdf";
        InputStreamResource resource = new InputStreamResource(new FileInputStream(path));
        return resource;
    }
}
