insert into app_user(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('1','user@estrix.pl', 'user', 'github', '36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_USER');
insert into app_user(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('2','admin@estrix.pl', 'admin', 'github','36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_ADMIN');
insert into app_user(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('3','superuser@estrix.pl', 'superuser', 'github','36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_SUPERUSER');
insert into app_user(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('4','operator@estrix.pl', 'operator', 'github','36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_SCANER_OPERATOR');


INSERT INTO `stock` (`id`, `api_url`, `name`, `update_hour`, `url`, `site_scratch`) VALUES (NULL, 'http://www.hurtowniak.pl/4308-94fefe7e006da30854ee7200788cf7d8-active.xml', 'Hurtowniak', '201910011634', 'hurtowniak.pl', b'0' );
INSERT INTO `stock` (`id`, `api_url`, `name`, `update_hour`, `url`, `site_scratch`) VALUES (NULL, 'https://gatito.pl/modules/exportxml/export.php?email=k.mucik@e-strix.pl&token=9ffeb7cf4781a22453cad02731710efc&get=products&lang=pl&currency=pln&pcb=1&on_order=5', 'Gatito', '201910011604', 'gatio.pl', b'0' );
INSERT INTO `stock` (`id`, `api_url`, `name`, `update_hour`, `url`, `site_scratch`) VALUES (NULL, 'https://api.ikonka.com.pl/api2/index.php/request/?format=xml&hash=a357585c63b3c99262ea269a0db4cb3862917562&variant=1', 'Ikonka', '201910011604', 'ikonka.pl', b'0' );

INSERT INTO `shop` (`id`, `last_update`, `api_url`, `consumer_key`, `consumer_secret`, `name`, `status_progress`, `stock_ids`, `url`) VALUES (NULL, '201911150915', 'http://www.4ext.pl', 'ck_be25e035ef94291412446caca0509ca5a8a9dc32', 'cs_3b356c67fe6de209f0c6be64f9d8298896f1313f', '4ext_pl', '0', '', 'www.4ext.pl');
INSERT INTO `shop` (`id`, `last_update`, `api_url`, `consumer_key`, `consumer_secret`, `name`, `status_progress`, `stock_ids`, `url`) VALUES (NULL, '201911150915', 'http://kidz.e-strix.com', 'ck_78a5853b9ce7140031a60d8bab450e85356268bf', 'cs_458605514a72acc4d43d394d1a5337af56c5fefd', 'kidz_e-strix_com', '0', '', 'kidz.e-strix.com');


INSERT INTO `app_setting` (`id`, `last_update`, `setting_value`, `setting_name`, `setting_type`, `setting_code`) VALUES (NULL, NULL, '/home/sklepy/tmp/', 'Katalog tymczasowy', 'string', 'BASE_TEMP_DIR');
INSERT INTO `app_setting` (`id`, `last_update`, `setting_value`, `setting_name`, `setting_type`, `setting_code`) VALUES (NULL, NULL, '/home/sklepy/zasoby/', 'Katalog zasobów', 'string', 'BASE_RESOURCE_DIR');
INSERT INTO `app_setting` (`id`, `last_update`, `setting_value`, `setting_name`, `setting_type`, `setting_code`) VALUES (NULL, NULL, '1', 'Scheduler: Aktualizacja produktów z magazynu', 'boolean', 'SCHEDULER_UPDATE_PRODUCT_STOCK');
INSERT INTO `app_setting` (`id`, `last_update`, `setting_value`, `setting_name`, `setting_type`, `setting_code`) VALUES (NULL, NULL, '0', 'Scheduler: Wysyłanie produktów do sklepu', 'boolean', 'SCHEDULER_SEND_PRODUCT_SHOP');
INSERT INTO `app_setting` (`id`, `last_update`, `setting_value`, `setting_name`, `setting_type`, `setting_code`) VALUES (NULL, NULL, '0', 'Scheduler: Pobieranie informacji o aukcjach allegro', 'boolean', 'SCHEDULER_UPDATE_ALLEGRO_OFFER');
INSERT INTO `app_setting` (`id`, `last_update`, `setting_value`, `setting_name`, `setting_type`, `setting_code`) VALUES (NULL, NULL, '1', 'Scheduler: Aktualizacja obrazków', 'boolean', 'SCHEDULER_UPDATE_STOCK_IMAGES');
