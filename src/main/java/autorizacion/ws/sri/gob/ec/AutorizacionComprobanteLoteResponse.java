//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package autorizacion.ws.sri.gob.ec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "autorizacionComprobanteLoteResponse",
        propOrder = {"respuestaAutorizacionLote"}
)
public class AutorizacionComprobanteLoteResponse {
    @XmlElement(
            name = "RespuestaAutorizacionLote"
    )
    protected RespuestaLote respuestaAutorizacionLote;

    public AutorizacionComprobanteLoteResponse() {
    }

    public RespuestaLote getRespuestaAutorizacionLote() {
        return this.respuestaAutorizacionLote;
    }

    public void setRespuestaAutorizacionLote(RespuestaLote value) {
        this.respuestaAutorizacionLote = value;
    }
}
