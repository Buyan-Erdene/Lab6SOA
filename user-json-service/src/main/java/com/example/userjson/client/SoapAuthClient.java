package com.example.userjson.client;

import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Service
public class SoapAuthClient extends WebServiceGatewaySupport {

    private static final String SOAP_URI = "http://localhost:8081/ws";
    private static final String NAMESPACE = "http://example.com/users";

    public boolean validateToken(String token) {
        // SOAP XML envelope-г гараар бүтээх
        String soapBody = String.format("""
            <soapenv:Envelope
                xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                xmlns:usr="%s">
               <soapenv:Body>
                  <usr:ValidateTokenRequest>
                     <usr:token>%s</usr:token>
                  </usr:ValidateTokenRequest>
               </soapenv:Body>
            </soapenv:Envelope>
            """, NAMESPACE, token);

        try {
            org.springframework.ws.WebServiceMessage response =
                getWebServiceTemplate()
                    .sendAndReceive(SOAP_URI,
                        msg -> {
                            try {
                                msg.getPayloadResult()
                                   .getClass(); // just trigger
                                javax.xml.transform.stream.StreamSource source =
                                    new javax.xml.transform.stream.StreamSource(
                                        new java.io.StringReader(soapBody));
                                javax.xml.transform.TransformerFactory
                                    .newInstance()
                                    .newTransformer()
                                    .transform(source, msg.getPayloadResult());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        },
                        msg -> msg
                    );

            // Response-г string болгоно
            java.io.StringWriter sw = new java.io.StringWriter();
            javax.xml.transform.TransformerFactory
                .newInstance()
                .newTransformer()
                .transform(response.getPayloadSource(),
                           new javax.xml.transform.stream.StreamResult(sw));
            return sw.toString().contains("<valid>true</valid>");
        } catch (Exception e) {
            return false;
        }
    }
}