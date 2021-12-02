package com.example.vcard;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.Address;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SiteGetter {

    public String getPanoramaFirm(String text) throws IOException {

        String html = "";
        String htmlSite = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" + "<style>\n" +
                ".myDiv {\n" +
                "  border: 5px outset black;\n" +
                "  background-color: lightgray;    \n" +
                "  text-align: center;\n" +
                "width:500px;\n" +
                "height:500px;\n" +
                "  float:left" +
                "}\n" +
                "</style>" +
                "<meta charset=\"UTF-8\">" +
                "  <title>Contact Cards</title>\n" +
                "</head>\n" +
                "<body>\n";

        html += htmlSite;


        Document document = Jsoup.connect("https://panoramafirm.pl/" + text).get();

        Elements elements = document.select("script[type=\"application/ld+json\"]");
        elements.remove(elements.last());


        String name;
        String telephone;
        String email;
        String site;
        String image;
        JSONObject adress;
        String ulica = "";
        String miasto = "";
        String code = "";

        for (Element element : elements) {
            JSONObject jsonObject = new JSONObject(element.html());

            try {
                name = jsonObject.getString("name");
            } catch (JSONException e) {
                name = "brak nazwy firmy";
            }
            try {
                telephone = jsonObject.getString("telephone");
            } catch (JSONException e) {
                telephone = "brak telefonu";
            }
            try {
                email = jsonObject.getString("email");
            } catch (JSONException e) {
                email = "brak email";
            }
            try {
                site = jsonObject.getString("sameAs");
            } catch (JSONException e) {
                site = "brak strony";
            }
            try {
                image = jsonObject.getString("image");
            } catch (JSONException e) {
                image = "brak zdjecia";
            }


            try {
                adress = jsonObject.getJSONObject("address");

                try {
                    ulica = adress.getString("streetAddress");
                } catch (JSONException e) {
                    ulica = "brak ulicy";
                }
                try {
                    miasto = adress.getString("addressLocality");
                } catch (JSONException e) {
                    miasto = "brak miasta";
                }
                try {
                    code = adress.getString("postalCode");
                } catch (JSONException e) {
                    code = "brak miasta";
                }

            } catch (JSONException e) {
                adress = null;
            }
            String params = String.format("name=%s&telephone=%s&email=%s&website=%s&street=%s&postalCode=%s&city=%s",
                    name, telephone, email, site, ulica, code, miasto).replaceAll(" ", "%20");

            String card = String.format("<div class=\"myDiv\">\n" +
                    "  <h1>%s</h1>\n" +
                    "  <img src=\"%s\" alt=\"Brak Logo\" width=\"100\" >\n" +
                    "  <h3>email:%s    tel:%s</h3>\n" +
                    "  \n" +
                    "<h3>%s</h3>\n" +
                    "  <h3>%s</h3>\n" +
                    "  <h3>%s</h3>\n" +
                    "<a href=/vcard/?%s>" +
                    "<button>Pobierz vCard</button></a></p>\n" +
                    "</div> ", name, image, email, telephone, site, (ulica + " " + miasto), code, params);


            html += card;


            System.out.println(jsonObject.get("name"));
        }


        html += "</body>\n" +
                "</html>";


        return html;
    }


}
