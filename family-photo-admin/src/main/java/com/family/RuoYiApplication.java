package com.family;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("   .    _  .     _____________  \n" +
                "   |\\_|/__/|    /             \\ \n" +
                "  / / \\/ \\  \\  / 启动成功！ \\\n" +
                " /__|O||O|__ \\ \\   Start Success   /\n" +
                "|/_ \\_/\\_/ _\\ | \\  ___________/ \n" +
                "| | (____) | ||  |/             \n" +
                "\\/\\___/\\__/  // _/              \n" +
                "(_/         ||                  \n" +
                " |          ||\\                 \n" +
                "  \\        //_/                 \n" +
                "   \\______//                    \n" +
                "  __|| __||                     \n" +
                " (____(____)                    ");
    }
}
