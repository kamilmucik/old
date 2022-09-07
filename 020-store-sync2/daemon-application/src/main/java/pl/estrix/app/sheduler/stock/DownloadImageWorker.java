package pl.estrix.app.sheduler.stock;

import org.firebirdsql.logging.Logger;
import org.firebirdsql.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.dto.model.SettingDto;

import java.util.Date;
import java.util.Optional;

@Component
public class DownloadImageWorker {

    private static Logger LOG = LoggerFactory.getLogger(DownloadImageWorker.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private SettingService settingService;

    @Scheduled(cron="0 * * * * *")
    public void runTask() {
//        LOG.debug("_date: " + new Date());

        Optional<SettingDto> optSetting = settingService.getByCode("SCHEDULER_UPDATE_STOCK_IMAGES");
        if (optSetting.isPresent() && optSetting.get().getValue().equals("1")) {
            stockService.downloadStockProductImage();
        }
    }
}
