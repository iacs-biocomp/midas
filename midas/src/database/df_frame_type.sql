INSERT INTO db_frame_type (id,description,snippet,js,data_snippet,data_format,renderer) VALUES 
('SHINY','R Shiny module','shiny.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('INFO-PANEL','info panel box','info-panel.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('IMAGE','Imagen','image.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('INFO-PANEL-O','info panel box open','info-panel-o.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('MAP_ARAGON','Mapa Aragon','map-aragon.jsp','map-aragon.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('HTML_BIGAN','Html Bigan con JS','basic-html.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('CIAS_SELECTOR','Cias selector','ciasSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('SANKEY','Sankey graph','sankey.jsp','sankey.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('BENCHMARK','Benchmark table','benchmark.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('HTML','Plain HTML insert','plain-html.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
;
INSERT INTO db_frame_type (id,description,snippet,js,data_snippet,data_format,renderer) VALUES 
('MOR-LINE','morris line chart','morris-line.jsp','','','','es.aragon.midas.dashboard.renderer.MorrisLineRenderer')
,('BASICTABLE','basic table','basic-table.jsp','datatables.js',NULL,NULL,'es.aragon.iacs.biganPortal.renderer.BasicTableRenderer')
,('HIGHCHART','Highchart Graph','highchart.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('DATE_SLIDER','Date Slider','dateSlider.jsp','','',NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('TREEMAP_STATIC','Treemap static','treemap-static.jsp','','treemap-static','treemap1','es.aragon.midas.dashboard.renderer.BasicRenderer')
,('WEEPEOPLE','WeePeople Graph','weepeople.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('WEEPEOPLEN','WeePeople Graph n colors','weepeople.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('HIGHCHARTPYR','Highchart Pyramid','highchartPyr.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('CIAS_SELECT_LEV','Cias selector with level','ciasSelectorLevel.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('DETAIL_SELECTOR','Detail selector','detailSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
;
INSERT INTO db_frame_type (id,description,snippet,js,data_snippet,data_format,renderer) VALUES 
('HIGHCHART_DUAL','Highchart Graph','highchartDual.jsp','highchartDual.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('MAP_ARAGON_S','Mapa Aragon Sectores','mapLine.jsp','/cdn/js/d3/topojson.v1.min.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('MAP_ARAGON_H','Mapa Aragon Sectores','mapLineH.jsp','/cdn/js/d3/topojson.v1.min.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('AGG_SELECTOR','Aggregation selector','aggSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('LEVEL_SELECTOR','Aggregation level selector','levelSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('DETAIL_H_SELECT','Detail Hospital Selector','detailHospSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('SECTOR_H_SELECT','Sector / Hospital Selector','sectorHospSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
;