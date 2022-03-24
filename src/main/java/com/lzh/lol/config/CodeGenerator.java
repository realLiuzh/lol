package com.lzh.lol.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * @author 志昊的刘
 * @version 1.0
 * @description: TODO
 * @date 2021/9/3 15:40
 */
public class CodeGenerator {

    private static final String AUTHOR="lzh";
    private static final String OUT_PUT_DIR="D:\\JavaProjects\\lol\\src\\main\\java";
    private static final String URL="jdbc:mysql://47.96.86.132:3306/lol?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
    // 此处要注意：parent + moduleName 为包的名字，在这个包下，创建对应的controller...
    private static final String PARENT="com.lzh";
    private static final String MODULE="lol";

    // 数据库中表的名字，表示要对哪些表进行自动生成controller service、mapper...
    private static final String[] INCLUDE=new String[]{"competition_bet"};

    // 生成实体时去掉表前缀，比如edu_course，如果不加下面这句，生成的实体类名字就是：EduCourse
    private static final String[] TABLEPREFIX=new String[]{"competition_"};

    public static void main(String[] args) {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();

        // 此处建议写项目/src/main/java源代码的绝对路径
        gc.setOutputDir(OUT_PUT_DIR);
        // 生成注释时的作者
        gc.setAuthor(AUTHOR);
        //生成后是否打开资源管理器
        gc.setOpen(false);
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService");    //去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
        gc.setDateType(DateType.ONLY_DATE); //定义生成的实体类中日期类型

        // 如果开启Swagger,要引入相应的包
        //gc.setSwagger2(true); //开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        // 此处要注意：parent + moduleName 为包的名字，在这个包下，创建对应的controller...
        pc.setParent(PARENT);
        pc.setModuleName(MODULE); //模块名
        pc.setController("controller");
        pc.setEntity("pojo");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 数据库中表的名字，表示要对哪些表进行自动生成controller service、mapper...
        strategy.setInclude(INCLUDE);
        // 数据库表映射到实体的命名策略,驼峰命名法
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 生成实体时去掉表前缀，比如edu_course，如果不加下面这句，生成的实体类名字就是：EduCourse
        strategy.setTablePrefix(TABLEPREFIX);
        //生成实体时去掉表前缀
        // strategy.setTablePrefix(pc.getModuleName() + "_");

        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);

/*        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("");
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);*/

        // 6、执行
        mpg.execute();
    }


}
