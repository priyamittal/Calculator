package com.futurice.controller;

import com.futurice.service.CalulatorService;
import net.minidev.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class CalculatorController {


    @RequestMapping(value = "/" , produces = "text/html")
    public String getIndexPage() {
        return "<html><body><div id=\"app\"></div>\n" +
                "<script src=\"./bundle.js\"></script></body></html>";
    }

    @RequestMapping(value = "/*.js")
    public ResponseEntity<byte[]> index() throws IOException {

        InputStream input = new ClassPathResource("/assets/bundle.js").getInputStream();

        byte[] media =  StreamUtils.copyToByteArray(input);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        return new ResponseEntity<>(media,headers, HttpStatus.OK);
    }



    @RequestMapping(name= "/calculate" , method = GET, produces = "application/json")
    public ResponseEntity<Object> calculate(@RequestParam(value = "query") String base64Query) throws UnsupportedEncodingException {
        final String query = decodeBase64Query(base64Query);

        List<String> rpnQuery = CalulatorService.convertToRPN(query);
        JSONObject result = resultToJson(CalulatorService.calculateFromRpn(rpnQuery));
        return ResponseEntity.ok(result);
    }


    private String decodeBase64Query(final String base64Query) throws UnsupportedEncodingException {
        return  new String(Base64.getDecoder().decode(base64Query), "utf-8");
    }

    private JSONObject resultToJson(double result) {
        JSONObject obj = new JSONObject();

        obj.put("error", "false");
        obj.put("result", result);
        return obj;
    }

}
