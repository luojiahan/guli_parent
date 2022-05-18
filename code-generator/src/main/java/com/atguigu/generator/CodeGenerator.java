package com.atguigu.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CodeGenerator {
    public static void main(String[] args) throws Exception {

        String configFile = "application.properties";
        //2、设置数据库对应的table
        String[] tables = new String[] {"t_order", "t_pay_log"};

        CodeGenerator.generate(configFile, tables, false);
        CodeGenerator.generate(configFile, tables, true);

    }
    public static void generate(String configFile, String[] tables, boolean entity) throws IOException {
        //用来获取配置信息
        Properties rb = new Properties();
        rb.load(new ClassPathResource(configFile).getInputStream());

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(rb.getProperty("mp.OutputDir")  + "/src/main/java");
        gc.setFileOverride(false); // 重新生成时文件是否覆盖
        gc.setOpen(false); // 生成后是否打开资源管理器
        gc.setEnableCache(false);
        gc.setAuthor("atguigu");
        gc.setSwagger2(true); // 开启Swagger2模式
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setIdType(IdType.ID_WORKER_STR);  // 主键策略
        gc.setDateType(DateType.ONLY_DATE); // 定义生成的实体类中日期类型
        if (entity) {
            gc.setEntityName("%s");
        } else {
            gc.setEntityName("%sVo");
        }
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService"); //去掉Service接口的首字母I
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(rb.getProperty("mp.url"));
        dsc.setDriverName(rb.getProperty("mp.driver"));
        dsc.setUsername(rb.getProperty("mp.username"));
        dsc.setPassword(rb.getProperty("mp.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(rb.getProperty("mp.parent"));
        pc.setModuleName(rb.getProperty("mp.module"));
        if (entity) {
            pc.setEntity("entity");
        } else {
            pc.setEntity("entity.Vo");
        }
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return rb.getProperty("mp.OutputDir") +
//                        "/mapper/" +
//                        rb.getProperty("mp.module") + "/"
//                        + tableInfo.getMapperName() + StringPool.DOT_XML;
//            }
//        });
//        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
//        TemplateConfig tc = new TemplateConfig().setXml(null);
//        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel); //数据库表映射到实体的命名策略
        sc.setColumnNaming(NamingStrategy.underline_to_camel); //数据库表字段映射到实体的命名策略
        sc.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
        sc.setRestControllerStyle(true); //restful api风格控制器
        sc.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        sc.setEntityTableFieldAnnotationEnable(entity);
        sc.setVersionFieldName(rb.getProperty("mp.versionField"));
        sc.setLogicDeleteFieldName(rb.getProperty("mp.logicDeleteField"));

        sc.setInclude(tables);
        sc.setTablePrefix(StringUtils.defaultIfEmpty(rb.getProperty("mp.tablePrefix"), rb.getProperty("mp.module"))); //生成实体时去掉表前缀
        mpg.setStrategy(sc);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}