package com.zakmouf.bluetree.web;

import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/chart")
public class ChartController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public void getChart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        int width = Integer.parseInt(request.getParameter("width"));
        int height = Integer.parseInt(request.getParameter("height"));
        JFreeChart chart = (JFreeChart) request.getSession().getAttribute(name);
        BufferedImage image = generateImage(chart, width, height);
        byte[] bytes = encodeImage(image);
        response.setContentType("image/jpeg");
        response.getOutputStream().write(bytes);
    }

    protected BufferedImage generateImage(JFreeChart chart, int width, int height) {
        Dimension dimension = new Dimension(width, height);
        BufferedImage image = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        Rectangle2D rectangle = new Rectangle(dimension);
        chart.draw(graphics, rectangle);
        graphics.dispose();
        return image;
    }

    protected byte[] encodeImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

}
