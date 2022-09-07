//package pl.estrix.app.frontend.converter;
//
//import org.springframework.stereotype.Component;
//import pl.estrix.app.common.dto.model.AccountDto;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.convert.Converter;
//import javax.faces.convert.ConverterException;
//import java.util.Map;
//import java.util.WeakHashMap;
//
//@Component("accountConverter")
//public class AccountConverter implements Converter {
//
//    private static Map<String, AccountDto> entities = new WeakHashMap<>();
//
//    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        synchronized (entities) {
//            if (value == null || value.isEmpty()) {
//                return null;
//            }
//            try {
//                return entities.get(value);
//            } catch (Exception exception) {
//                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid AccountDto ID"));
//            }
//        }
//
//    }
//
//    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
//        if (object == null) {
//            return "";
//        }
//        if (object instanceof AccountDto) {
//            entities.put(object.toString(), (AccountDto) object);
//            return String.valueOf((object));
//        } else {
////            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Account instance"));
//            return "";
//        }
//    }
//}
