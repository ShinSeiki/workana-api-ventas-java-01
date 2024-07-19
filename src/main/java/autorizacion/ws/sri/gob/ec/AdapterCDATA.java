//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package autorizacion.ws.sri.gob.ec;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdapterCDATA extends XmlAdapter<String, String> {
    public AdapterCDATA() {
    }

    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }

    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }
}
