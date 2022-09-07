package pl.estrix.app.frontend.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.lang.management.ManagementFactory;

//@Component("systemBean")
//@Scope("systemBean")
//@Setter
//@Getter
@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "systemBean")
public class SystemBean extends MainController {

    private Long freeMemorySize;

    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
        com.sun.management.OperatingSystemMXBean mxbean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        freeMemorySize = bytesToMegabytes(mxbean.getFreePhysicalMemorySize());
    }
}
