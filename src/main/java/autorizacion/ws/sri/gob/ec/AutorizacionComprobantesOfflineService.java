package autorizacion.ws.sri.gob.ec;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(
        name = "AutorizacionComprobantesOfflineService",
        targetNamespace = "http://ec.gob.sri.ws.autorizacion"
)

public class AutorizacionComprobantesOfflineService extends Service {
    private static final QName AUTORIZACIONCOMPROBANTESOFFLINESERVICE_QNAME = new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService");

    // CONSTRUCTOR
    public AutorizacionComprobantesOfflineService(String ambiente) {
        super(__getWsdlLocation(ambiente), AUTORIZACIONCOMPROBANTESOFFLINESERVICE_QNAME);
    }

    @WebEndpoint(
            name = "AutorizacionComprobantesOfflinePort"
    )
    public AutorizacionComprobantesOffline getAutorizacionComprobantesOfflinePort() {
        return super.getPort(new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflinePort"), AutorizacionComprobantesOffline.class);
    }

    // AutorizacionComprobantesOffline CONSULTA DE DOCUMENTOS AUTORIZADOS
    // CELCER PRUEBAS
    // CEL    PRODUCCION

    private static URL __getWsdlLocation(String ambiente) {
        try {
            if(ambiente.equals("1")){
                return new URL("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl");
            }else{
                return new URL("https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
