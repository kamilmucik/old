package pl.estrix.app.frontend.converter;

import com.sun.faces.util.MessageFactory;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("ourCustomConverter")
public class DateTimeCustomConverter extends DateTimeConverter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null){
            return null;
        }
        return LocalDate.parse(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        try {
            LocalDate dateValue = (LocalDate) value;
            return dateValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        } catch (Exception e) {
            throw new ConverterException(MessageFactory.getMessage(context, STRING_ID, value, MessageFactory.getLabel(context, component)), e);
        }
    }

}