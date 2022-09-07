package pl.estrix.app.sheduler.stock;

import org.firebirdsql.logging.Logger;
import org.firebirdsql.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SettingDto;
import pl.estrix.app.common.dto.model.StockDto;
import pl.estrix.app.lib.download.DownloadUtil;
//import pl.estrix.app.lib.download.DownloadUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class UpdateProductStockWorker {

    private static Logger LOG = LoggerFactory.getLogger(UpdateProductStockWorker.class);

    @Autowired
    private StockService stockService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private DownloadUtil downloadUtil;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    @Scheduled(cron="0 * * * * *")
    public void runTask() {
        String _date = simpleDateFormat.format(new Date());
        Optional<SettingDto> optSetting = settingService.getByCode("SCHEDULER_UPDATE_PRODUCT_STOCK");
        if (optSetting.isPresent() && optSetting.get().getValue().equals("1")) {
                ListResponseDto<StockDto> listResponseDto = stockService.getItems(StockSearchCriteriaDto.builder().build(),null);
                if (!listResponseDto.isEmpty()) {
                    for (StockDto dto : listResponseDto.getData()) {
                        String[] hours = dto.getUpdateHour().split(";");
                        for (String hour: hours) {
                            if (hour.trim().equals(_date)) {
                                StockDto stock = stockService.get(dto.getId());
                                downloadUtil.downloadProduct(stock);
                            }
                        }
                    }
                }
        }
    }

}
