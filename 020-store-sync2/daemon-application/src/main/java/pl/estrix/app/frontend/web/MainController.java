package pl.estrix.app.frontend.web;


import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;
import org.springframework.context.annotation.Scope;
import pl.estrix.app.FacesViewScope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Getter
@Setter
@Scope(FacesViewScope.NAME)
public abstract class MainController implements Serializable{

    protected HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    protected FacesContext context = FacesContext.getCurrentInstance();

    protected Integer tablePageIndex;

    @PostConstruct
    public void init() {
        System.out.println("-" + getRequest().getParameter("table_page"));
        if (getRequest().getParameter("table_page")== null){
            tablePageIndex = 0;
        }else{
            tablePageIndex = Integer.parseInt(getRequest().getParameter("table_page"));
        }
    }

    public MainController(){
    }

    public void onDateSelectRefresh(SelectEvent event) {
    }

    public void onPage(PageEvent e) {
        tablePageIndex = e.getPage() + 1;
    }

    protected void updateOwnUrl(){
        String requestURI = request.getRequestURI();
        context.getExternalContext().getSessionMap().put("requestURI", requestURI);
    }


}
