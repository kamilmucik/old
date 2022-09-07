package pl.estrix.app.frontend.converter;


import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Component("themeConverter")
public class ThemeConverter implements Converter  {

    public Object getAsObject(final FacesContext context, final UIComponent component, final String newValue) {
        if (newValue == null) {
            return newValue;
        }
        return newValue;
    }

    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
//        return ((Theme) value).getName();
        if (value == null) {
            return "";
        }
//        if (object instanceof AccountDto) {
//            entities.put(object.toString(), (AccountDto) object);
            return String.valueOf((value));
//        } else {
////            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Account instance"));
//            return "";
//        }
    }
}
